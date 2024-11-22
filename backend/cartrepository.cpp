#include "cartrepository.h"
#include "logger.h"
#include <qsqldatabase.h>
#include <qsqlerror.h>
#include <qsqlquery.h>
#include "dbcontroller.h"
#include "bookrepository.h"

bool CartRepository::addBook(quint64 userId, quint64 bookId)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[addBook] Database not open!"), Logger::LogLevel::Warning);
        return false;
    }

    QString queryString = R"(
        INSERT INTO carts_content (user_id, book_id)
        VALUES (:user_id, :book_id);
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":user_id", userId);
    query.bindValue(":book_id", bookId);

    if (!query.exec()) {
        Logger::instance().log(QString("[addBook] Database query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
        return false;
    }

    return true;
}

bool CartRepository::remove(quint64 userId, quint64 bookId)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[remove] Database not open!"), Logger::LogLevel::Warning);
        return {};
    }

    QString queryString = R"(
        DELETE FROM carts_content
        WHERE user_id = :user_id AND book_id = :book_id;
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":user_id", userId);
    query.bindValue(":book_id", bookId);
    if (query.exec()) {
        return true;
    } else {
        Logger::instance().log(QString("[remove] Database query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
        return false;
    }
}

bool CartRepository::clear(quint64 userId)
{
}

QList<Book> CartRepository::fetchUsersBooks(quint64 userId)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[fetchUsersBooks] Database not open!"), Logger::LogLevel::Warning);
        return {};
    }

    QString queryString = R"(
        SELECT b.id, b.title, b.description, b.authors, b.price, l.lang_name, b.cover
        FROM carts_content AS cc
        INNER JOIN books AS b ON cc.book_id = b.id
        LEFT JOIN languages AS l ON b.language_id = l.id
        WHERE cc.user_id = :user_id;
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":user_id", userId);

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
            book.setGenres(BookRepository::fetchGenresForBook(book.id()));
            books.append(std::move(book));
        }
    } else {
        Logger::instance().log(QString("[fetchBooks] Database query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
    }

    return books;
}
