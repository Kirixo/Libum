#include "bookstatusrepository.h"
#include "dbcontroller.h"
#include "logger.h"
#include "bookstatus.h"
#include <QSqlQuery>
#include <QSqlError>
#include <QVariant>

std::optional<BookStatus> BookStatusRepository::getStatusForBook(int userId, int bookId)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[%1] Database not open!").arg(__func__), Logger::LogLevel::Warning);
        return std::nullopt;
    }

    QString queryString = R"(
        SELECT s.id, s.status_name
        FROM lists AS l
        LEFT JOIN statuses AS s ON l.status_id = s.id
        WHERE l.user_id = :userId AND l.book_id = :bookId
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":userId", userId);
    query.bindValue(":bookId", bookId);

    if (query.exec() && query.next()) {

        return BookStatus(query.value("id").toInt(), query.value("status_name").toString());
    }

    Logger::instance().log(QString("[%1] Query failed: %2").arg(__func__, query.lastError().text()), Logger::LogLevel::Warning);
    return std::nullopt;
}

QList<BookStatus> BookStatusRepository::getStatusesList()
{
    QList<BookStatus> statuses;

    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[%1] Database not open!").arg(__func__), Logger::LogLevel::Warning);
        return statuses;
    }

    QString queryString = R"(
        SELECT id, status_name
        FROM statuses;
    )";

    QSqlQuery query(db);
    query.prepare(queryString);

    if (query.exec()) {
        while (query.next()) {
            BookStatus status;
            status.id = query.value("id").toInt();
            status.name = query.value("status_name").toString();
            statuses.append(status);
        }
    } else {
        Logger::instance().log(QString("[%1] Query failed: %2").arg(__func__, query.lastError().text()), Logger::LogLevel::Warning);
    }

    return statuses;
}

QList<Book> BookStatusRepository::getBooksWithStatus(int userId, int statusId, int limit, int offset)
{
    QList<Book> books;

    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[%1] Database not open!").arg(__func__), Logger::LogLevel::Warning);
        return {};
    }

    QString queryString = R"(
        SELECT b.id AS book_id, b.title AS book_title, b.cover AS book_cover, b.pages_count,
               lang.lang_name AS book_language, b.year AS book_year, b.authors AS book_author
        FROM lists AS l
        LEFT JOIN books AS b ON l.book_id = b.id
        LEFT JOIN languages AS lang ON b.language_id = lang.id
        WHERE l.user_id = :user_id AND l.status_id = :status_id
        ORDER BY l.id DESC
        LIMIT :limit OFFSET :offset
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":user_id", userId);
    query.bindValue(":status_id", statusId);
    query.bindValue(":limit", limit);
    query.bindValue(":offset", offset - 1);

    if (query.exec()) {
        while (query.next()) {
            Book book;
            book.setId(query.value("book_id").toInt());
            book.setTitle(query.value("book_title").toString());
            book.setCover(query.value("book_cover").toString());
            book.setLanguage(query.value("book_language").toString());
            book.setYear(query.value("book_year").toInt());
            book.setAuthor(query.value("book_author").toString());
            book.setPageCount(query.value("pages_count").toInt());
            books.append(book);
        }
    } else {
        Logger::instance().log(QString("[%1] Query failed: %2").arg(__func__, query.lastError().text()), Logger::LogLevel::Warning);
    }

    return books;
}

bool BookStatusRepository::setStatusForBook(int userId, int bookId, int statusId)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[%1] Database not open!").arg(__func__), Logger::LogLevel::Warning);
        return false;
    }

    QString upsertQueryString = R"(
        INSERT INTO lists (user_id, book_id, status_id)
        VALUES (:userId, :bookId, :statusId)
        ON CONFLICT (user_id, book_id) DO UPDATE
        SET status_id = :statusId
    )";

    QSqlQuery query(db);
    query.prepare(upsertQueryString);
    query.bindValue(":userId", userId);
    query.bindValue(":bookId", bookId);
    query.bindValue(":statusId", statusId);

    if (!query.exec()) {
        Logger::instance().log(QString("[%1] Query failed: %2").arg(__func__, query.lastError().text()), Logger::LogLevel::Warning);
        return false;
    }

    return true;
}

bool BookStatusRepository::removeStatusForBook(int userId, int bookId)
{
    QSqlDatabase db = DBController::getDatabase();
    if (!db.isOpen()) {
        Logger::instance().log(QString("[%1] Database not open!").arg(__func__), Logger::LogLevel::Warning);
        return false;
    }

    QString queryString = R"(
        DELETE FROM lists
        WHERE user_id = :userId AND book_id = :bookId
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":userId", userId);
    query.bindValue(":bookId", bookId);

    if (!query.exec()) {
        Logger::instance().log(QString("[%1] Query failed: %2").arg(__func__, query.lastError().text()), Logger::LogLevel::Warning);
        return false;
    }

    return true;
}
