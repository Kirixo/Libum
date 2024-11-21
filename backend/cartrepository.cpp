#include "cartrepository.h"
#include "logger.h"
#include <qsqldatabase.h>
#include <qsqlerror.h>
#include <qsqlquery.h>
#include "dbcontroller.h"

CartRepository::CartRepository() {}

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
        Logger::instance().log(QString("[fetchBookById] Database query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
        return false;
    }

    return true;
}
