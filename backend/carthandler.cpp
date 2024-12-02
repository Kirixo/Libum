#include "carthandler.h"
#include "cartrepository.h"
#include "responsefactory.h"
#include "logger.h"

CartHandler::CartHandler() {}

QHttpServerResponse CartHandler::addBook(const QHttpServerRequest &request)
{
    bool ok;
    int userId = request.query().queryItemValue("user_id").toInt(&ok);
    if (!ok) {
        return ResponseFactory::createResponse("Invalid user ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    int bookId = request.query().queryItemValue("book_id").toInt(&ok);
    if (!ok) {
        return ResponseFactory::createResponse("Invalid book ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    Logger::instance().log(QString("[addBook request]: user_id =  %1, book_id = %2").arg(userId)
                               .arg(bookId), Logger::LogLevel::Info);

    if(CartRepository::addBook(userId, bookId)) {
        const QByteArray responseMessage = "Book has been successfully added in the cart.";
        Logger::instance().log(QString("[addBook request]: %1 user_id =  %2, book_id = %3 ")
                                   .arg(responseMessage).arg(userId).arg(bookId), Logger::LogLevel::Info);
        return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::Ok);
    }

    const QByteArray responseMessage = "Operation failed.";
    Logger::instance().log(QString("[addBook request]: %1 user_id =  %2, book_id = %3 ")
                               .arg(responseMessage, userId).arg(bookId), Logger::LogLevel::Error);
    return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::InternalServerError);
}

QHttpServerResponse CartHandler::removeBook(const QHttpServerRequest &request)
{
    bool ok;
    int userId = request.query().queryItemValue("user_id").toInt(&ok);
    if (!ok) {
        return ResponseFactory::createResponse("Invalid user ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    int bookId = request.query().queryItemValue("book_id").toInt(&ok);
    if (!ok) {
        return ResponseFactory::createResponse("Invalid book ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    Logger::instance().log(QString("[removeBook request]: user_id =  %1, book_id = %2").arg(userId)
                               .arg(bookId), Logger::LogLevel::Info);

    if(CartRepository::remove(userId, bookId)) {
        const QByteArray responseMessage = "Book has been successfully removed from the cart.";
        Logger::instance().log(QString("[removeBook request]: %1 user_id =  %2, book_id = %3 ")
                                   .arg(responseMessage, userId).arg(bookId), Logger::LogLevel::Info);
        return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::Ok);
    }
    // TODO: specify errors
    const QByteArray responseMessage = "Operation failed.";
    Logger::instance().log(QString("[removeBook request]: %1 user_id =  %2, book_id = %3 ")
                               .arg(responseMessage, userId).arg(bookId), Logger::LogLevel::Error);
    return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::InternalServerError);
}

QHttpServerResponse CartHandler::clearCart(const QHttpServerRequest &request)
{
    bool ok;
    int userId = request.query().queryItemValue("user_id").toInt(&ok);
    if (!ok) {
        return ResponseFactory::createResponse("Invalid user ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    Logger::instance().log(QString("[clearCart request]: user_id =  %1")
                               .arg(userId), Logger::LogLevel::Info);

    if(CartRepository::clear(userId)) {
        const QByteArray responseMessage = "User's cart has been successfully cleared.";
        Logger::instance().log(QString("[clearCart request]: %1 user_id =  %2")
                                   .arg(responseMessage, userId), Logger::LogLevel::Info);
        return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::Ok);
    }
    // TODO: specify errors
    const QByteArray responseMessage = "Operation failed.";
    Logger::instance().log(QString("[clearCart request]: %1 user_id =  %2")
                               .arg(responseMessage, userId), Logger::LogLevel::Error);
    return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::InternalServerError);
}

QHttpServerResponse CartHandler::getUsersCart(const QHttpServerRequest &request)
{
    bool ok;
    int userId = request.query().queryItemValue("user_id").toInt(&ok);
    if (!ok) {
        return ResponseFactory::createResponse("Invalid user ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    Logger::instance().log(QString("[getUsersCart request]: user_id =  %1").arg(userId), Logger::LogLevel::Info);

    QList<Book> books = CartRepository::fetchUsersBooks(userId);

    if (!books.isEmpty()) {
        QJsonArray bookArray;
        for (const Book &book : books) {
            bookArray.append(book.toJson());
        }

        QJsonObject responseJson;
        // responseJson["total_count"] = CartRepository::getBooksCount();
        responseJson["books"] = bookArray;

        QByteArray responseData = QJsonDocument(responseJson).toJson(QJsonDocument::Compact);

        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    Logger::instance().log(QString("[getUsersCart request] Not Found: user_id =  %1")
                               .arg(userId), Logger::LogLevel::Warning);

    return ResponseFactory::createResponse("Books not found in the cart.", QHttpServerResponse::StatusCode::NotFound);
}
