#include "bookrepository.h"

BookRepository::BookRepository() {}

std::optional<Book> BookRepository::fetchBookById(int id)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        qWarning() << "Database not open!";
        return std::nullopt;
    }

    QString queryString = R"(
        SELECT b.id, b.title, b.description, b.authors, b.price, l.lang_name, b.cover
        FROM books AS b
        LEFT JOIN languages AS l ON b.language_id = l.id
        WHERE b.id = :id
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":id", id);

    if (!query.exec() || !query.next()) {
        return std::nullopt;
    }

    Book book;
    book.setId(query.value("id").toInt());
    book.setTitle(query.value("title").toString());
    book.setDescription(query.value("description").toString());
    book.setAuthor(query.value("authors").toString());
    book.setPrice(query.value("price").toFloat());
    book.setCover(query.value("cover").toString());
    book.setLanguage(query.value("lang_name").toString());

    book.setGenres(fetchGenresForBook(book.id()));

    return book;
}

QList<Book> BookRepository::fetchBooks(int limit, int page)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        qWarning() << "Database not open!";
        return {};
    }

    QString queryString = R"(
        SELECT b.id, b.title, b.description, b.authors, b.price, l.lang_name, b.cover
        FROM books AS b
        LEFT JOIN languages AS l ON b.language_id = l.id
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
            book.setPrice(query.value("price").toFloat());
            book.setLanguage(query.value("lang_name").toString());
            book.setCover(query.value("cover").toString());
            book.setGenres(fetchGenresForBook(book.id()));
            books.append(std::move(book));
        }
    }

    return books;
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

QList<QString> BookRepository::fetchGenresForBook(int bookId)
{
    QSqlDatabase db = DBController::getDatabase();
    QList<QString> genres;

    QString queryString = R"(
        SELECT g.genre_name
        FROM book_genre bg
        LEFT JOIN genres AS g ON g.id = bg.genre_id
        WHERE bg.book_id = :id
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":id", bookId);

    if (query.exec()) {
        while (query.next()) {
            genres.append(query.value("genre_name").toString());
        }
    }

    return genres;
}


