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
                                   .arg(responseMessage, userId).arg(bookId), Logger::LogLevel::Info);
        return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::Ok);
    }

    const QByteArray responseMessage = "Operation failed.";
    Logger::instance().log(QString("[addBook request]: %1 user_id =  %2, book_id = %3 ")
                               .arg(responseMessage, userId).arg(bookId), Logger::LogLevel::Error);
    return ResponseFactory::createJsonResponse(responseMessage, QHttpServerResponse::StatusCode::InternalServerError);
}
