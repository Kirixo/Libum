#include "bookstatushandler.h"
#include "bookstatusrepository.h"
#include "responsefactory.h"
#include "logger.h"
#include <QJsonDocument>
#include <QJsonArray>
#include <QJsonObject>

BookStatusHandler::BookStatusHandler() {}

QHttpServerResponse BookStatusHandler::getStatusOfBook(const QHttpServerRequest &request)
{
    bool okUser, okBook;
    int userId = request.query().queryItemValue("user_id").toInt(&okUser);
    int bookId = request.query().queryItemValue("book_id").toInt(&okBook);

    Logger::instance().log(QString("[%1 request]: userId = %2, bookId = %3").arg(__func__).arg(userId).arg(bookId), Logger::LogLevel::Info);

    if (!okUser || !okBook) {
        return ResponseFactory::createResponse("Invalid user ID or book ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    std::optional<BookStatus> status = BookStatusRepository::getStatusForBook(userId, bookId);

    if (status) {
        QByteArray responseData = QJsonDocument(status->toJson()).toJson(QJsonDocument::Compact);
        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Status not found.", QHttpServerResponse::StatusCode::NotFound);
}

QHttpServerResponse BookStatusHandler::getStatusList(const QHttpServerRequest &request)
{
    Logger::instance().log(QString("[%1 request]").arg(__func__), Logger::LogLevel::Info);

    QList<BookStatus> statuses = BookStatusRepository::getStatusesList();

    if (!statuses.isEmpty()) {
        QJsonArray statusArray;
        for (const BookStatus &status : statuses) {
            statusArray.append(status.toJson());
        }

        QJsonObject responseJson;
        responseJson["statuses"] = statusArray;

        QByteArray responseData = QJsonDocument(responseJson).toJson(QJsonDocument::Compact);

        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    Logger::instance().log(QString("[%1 request] Not Found").arg(__func__), Logger::LogLevel::Warning);

    return ResponseFactory::createResponse("Statuses not found.", QHttpServerResponse::StatusCode::NotFound);
}


QHttpServerResponse BookStatusHandler::getBookListWithStatus(const QHttpServerRequest &request)
{
    bool okUser, okStatus, okLimit, okOffset;
    int userId = request.query().queryItemValue("user_id").toInt(&okUser);
    int statusId = request.query().queryItemValue("status_id").toInt(&okStatus);
    int limit = request.query().queryItemValue("limit").toInt(&okLimit);
    int page = request.query().queryItemValue("page").toInt(&okOffset);

    if (!okLimit) limit = 25;
    if (!okOffset) page = 1;

    Logger::instance().log(QString("[%1 request]: userId = %2, statusId = %3, limit = %4, offset = %5").arg(__func__).arg(userId).arg(statusId).arg(limit).arg(page), Logger::LogLevel::Info);

    if (!okUser || !okStatus) {
        return ResponseFactory::createResponse("Invalid user ID or status ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    QList<Book> books = BookStatusRepository::getBooksWithStatus(userId, statusId, limit, page);

    if (!books.isEmpty()) {
        QJsonArray bookArray;
        for (const Book &book : books) {
            bookArray.append(book.toJson());
        }

        QJsonObject responseJson;
        responseJson["books"] = bookArray;

        QByteArray responseData = QJsonDocument(responseJson).toJson(QJsonDocument::Compact);
        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Books not found.", QHttpServerResponse::StatusCode::NotFound);
}

QHttpServerResponse BookStatusHandler::setStatusForBook(const QHttpServerRequest &request)
{
    bool okUser, okBook, okStatus;
    int userId = request.query().queryItemValue("user_id").toInt(&okUser);
    int bookId = request.query().queryItemValue("book_id").toInt(&okBook);
    int statusId = request.query().queryItemValue("status_id").toInt(&okStatus);

    Logger::instance().log(QString("[%1 request]: userId = %2, bookId = %3, statusId = %4").arg(__func__).arg(userId).arg(bookId).arg(statusId), Logger::LogLevel::Info);

    if (!okUser || !okBook || !okStatus) {
        return ResponseFactory::createResponse("Invalid input parameters.", QHttpServerResponse::StatusCode::BadRequest);
    }

    if (BookStatusRepository::setStatusForBook(userId, bookId, statusId)) {
        return ResponseFactory::createResponse("Status updated successfully.", QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Failed to update status.", QHttpServerResponse::StatusCode::InternalServerError);
}

QHttpServerResponse BookStatusHandler::removeStatusFromBook(const QHttpServerRequest &request)
{
    bool okUser, okBook;
    int userId = request.query().queryItemValue("user_id").toInt(&okUser);
    int bookId = request.query().queryItemValue("book_id").toInt(&okBook);

    Logger::instance().log(QString("[%1 request]: userId = %2, bookId = %3").arg(__func__).arg(userId).arg(bookId), Logger::LogLevel::Info);

    if (!okUser || !okBook) {
        return ResponseFactory::createResponse("Invalid user ID or book ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    if (BookStatusRepository::removeStatusForBook(userId, bookId)) {
        return ResponseFactory::createResponse("Status removed successfully.", QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Failed to remove status.", QHttpServerResponse::StatusCode::InternalServerError);
}

QHttpServerResponse BookStatusHandler::addBookToFavorites(const QHttpServerRequest &request)
{
    bool okUser, okBook;
    int userId = request.query().queryItemValue("user_id").toInt(&okUser);
    int bookId = request.query().queryItemValue("book_id").toInt(&okBook);

    Logger::instance().log(QString("[%1 request]: userId = %2, bookId = %3").arg(__func__).arg(userId).arg(bookId), Logger::LogLevel::Info);

    if (!okUser || !okBook) {
        return ResponseFactory::createResponse("Invalid input parameters.", QHttpServerResponse::StatusCode::BadRequest);
    }

    if (BookStatusRepository::setFavoritesForBook(userId, bookId)) {
        return ResponseFactory::createResponse("Status updated successfully.", QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Failed to update status.", QHttpServerResponse::StatusCode::InternalServerError);
}


QHttpServerResponse BookStatusHandler::removeBookFromFavorites(const QHttpServerRequest &request)
{
    bool okUser, okBook;
    int userId = request.query().queryItemValue("user_id").toInt(&okUser);
    int bookId = request.query().queryItemValue("book_id").toInt(&okBook);

    Logger::instance().log(QString("[%1 request]: userId = %2, bookId = %3").arg(__func__).arg(userId).arg(bookId), Logger::LogLevel::Info);

    if (!okUser || !okBook) {
        return ResponseFactory::createResponse("Invalid user ID or book ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    if (BookStatusRepository::removeFavoritesForBook(userId, bookId)) {
        return ResponseFactory::createResponse("Removed from favorites successfully.", QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Failed to remove from favorites.", QHttpServerResponse::StatusCode::InternalServerError);
}
