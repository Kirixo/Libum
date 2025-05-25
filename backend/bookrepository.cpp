#include "bookrepository.h"
#include "logger.h"
#include "dbcontroller.h"
#include <qsqldatabase.h>
#include <qsqlerror.h>
#include <qsqlquery.h>

BookRepository::BookRepository() {}

std::optional<Book> BookRepository::fetchBookById(int id)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[fetchBookById] Database not open!"), Logger::LogLevel::Warning);
        return std::nullopt;
    }

    QString queryString = R"(
        SELECT b.id, b.title, b.description, b.authors, b.year, b.pages_count, b.price, l.lang_name, b.cover
        FROM books AS b
        LEFT JOIN languages AS l ON b.language_id = l.id
        WHERE b.id = :id
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":id", id);

    if (!query.exec() || !query.next()) {
        Logger::instance().log(QString("[fetchBookById] Database query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
        return std::nullopt;
    }

    Book book;
    book.setId(query.value("id").toInt());
    book.setTitle(query.value("title").toString());
    book.setDescription(query.value("description").toString());
    book.setAuthor(query.value("authors").toString());
    book.setYear(query.value("year").toInt());
    book.setPageCount(query.value("pages_count").toInt());
    book.setPrice(query.value("price").toFloat());
    book.setCover(query.value("cover").toString());
    book.setLanguage(query.value("lang_name").toString());

    book.setGenres(fetchGenresForBook(book.id()));

    return book;
}



int BookRepository::getBooksCount()
{
    QSqlDatabase db = DBController::getDatabase();

    QString queryString = R"(
        SELECT COUNT(1)
        FROM books;
    )";

    QSqlQuery query(db);
    query.prepare(queryString);

    if (query.exec() && query.next()) {
        return query.value(0).toInt();
    }

    return 0;
}

QList<Genre> BookRepository::fetchGenresForBook(int bookId)
{
    QSqlDatabase db = DBController::getDatabase();
    QList<Genre> genres;

    QString queryString = R"(
        SELECT DISTINCT g.id, g.genre_name
        FROM book_genre bg
        LEFT JOIN genres AS g ON g.id = bg.genre_id
        WHERE bg.book_id = :id
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":id", bookId);

    if (query.exec()) {
        while (query.next()) {
            Genre tmpGenre;
            tmpGenre.setId(query.value("id").toInt());
            tmpGenre.setName(query.value("genre_name").toString());
            genres.append(std::move(tmpGenre));
        }
    }

    return genres;
}

QList<Book> BookRepository::fetchFilteredBooks(int limit, int page, std::optional<qint64> minPrice, std::optional<qint64> maxPrice,
                                               std::optional<QString> searchQuery, std::optional<qint64> userIdForFavorites)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[fetchFilteredBooks] Database not open!"), Logger::LogLevel::Warning);
        return {};
    }

    QString queryString = R"(
        SELECT DISTINCT b.id, b.title, b.description, b.authors, b.year, b.pages_count, b.price, l.lang_name, b.cover
        FROM books AS b
        LEFT JOIN languages AS l ON b.language_id = l.id

    )";

    QStringList conditions;

    if (userIdForFavorites.has_value()) {
        queryString.append(R"(
            LEFT JOIN favorites AS fav ON fav.book_id = b.id
        )");
        conditions << "fav.user_id = :userIdForFavorites";
    }

    if (minPrice.has_value()) {
        conditions << "b.price >= :minPrice";
    }

    if (maxPrice.has_value()) {
        conditions << "b.price <= :maxPrice";
    }

    if (searchQuery.has_value()) {
        conditions << "b.title LIKE :searchQuery ";
    }

    if (!conditions.isEmpty()) {
        queryString.append(" WHERE " + conditions.join(" AND "));
    }

    queryString.append(R"(
        ORDER BY b.id DESC
        LIMIT :limit OFFSET :offset
    )");

    QSqlQuery query(db);
    query.prepare(queryString);

    query.bindValue(":limit", limit);
    query.bindValue(":offset", (page - 1) * limit);

    if (userIdForFavorites) {
        query.bindValue(":userIdForFavorites", userIdForFavorites.value());
    }
    if (minPrice) {
        query.bindValue(":minPrice", minPrice.value());
    }
    if (maxPrice) {
        query.bindValue(":maxPrice", maxPrice.value());
    }
    if (searchQuery) {
        query.bindValue(":searchQuery", "%" + searchQuery.value() + "%");
    }


    QList<Book> books;
    if (query.exec()) {
        Logger::instance().log(QString("[fetchFilteredBooks] Database query content!")
                                   .append(query.lastQuery()), Logger::LogLevel::Debug);
        while (query.next()) {
            Book book;
            book.setId(query.value("id").toInt());
            book.setTitle(query.value("title").toString());
            book.setDescription(query.value("description").toString());
            book.setAuthor(query.value("authors").toString());
            book.setYear(query.value("year").toInt());
            book.setPageCount(query.value("pages_count").toInt());
            book.setPrice(query.value("price").toFloat());
            book.setLanguage(query.value("lang_name").toString());
            book.setCover(query.value("cover").toString());
            book.setGenres(fetchGenresForBook(book.id()));
            books.append(std::move(book));
        }
    } else {
        Logger::instance().log(QString("[fetchFilteredBooks] Database query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
    }

    return books;
}

QList<Book> BookRepository::fetchBooks(int limit, int page)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[fetchBooks] Database not open!"), Logger::LogLevel::Warning);
        return {};
    }

    QString queryString = R"(
        SELECT DISTINCT b.id, b.title, b.description, b.authors, b.year, b.pages_count, b.price, l.lang_name, b.cover
        FROM books AS b
        LEFT JOIN languages AS l ON b.language_id = l.id
        ORDER BY b.id DESC
        LIMIT :limit OFFSET :offset
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":limit", limit);
    query.bindValue(":offset", (page - 1) * limit);

    QList<Book> books;
    if (query.exec()) {
        while (query.next()) {
            Book book;
            book.setId(query.value("id").toInt());
            book.setTitle(query.value("title").toString());
            book.setDescription(query.value("description").toString());
            book.setAuthor(query.value("authors").toString());
            book.setYear(query.value("year").toInt());
            book.setPageCount(query.value("pages_count").toInt());
            book.setPrice(query.value("price").toFloat());
            book.setLanguage(query.value("lang_name").toString());
            book.setCover(query.value("cover").toString());
            book.setGenres(fetchGenresForBook(book.id()));
            books.append(std::move(book));
        }
    } else {
        Logger::instance().log(QString("[fetchBooks] Database query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
    }

    return books;
}





std::optional<QString> BookRepository::fetchFilePathForReader(int bookId)
{
    QSqlDatabase db = DBController::getDatabase();
    const QString type = "c_fb2";

    QString queryString = R"(
        SELECT bf.path
        FROM book_files bf
        WHERE bf.book_id = :id AND bf.type = :type;
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":id", bookId);
    query.bindValue(":type", type);

    if (query.exec() && query.next()) {
        return query.value("path").toString();
    }

    return std::nullopt;
}
