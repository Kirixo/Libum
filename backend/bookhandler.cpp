#include "bookhandler.h"
#include <qjsondocument.h>
#include "logger.h"

BookHandler::BookHandler() {}

QHttpServerResponse BookHandler::getBook(const QHttpServerRequest &request)
{
    bool ok;
    int bookId = request.query().queryItemValue("id").toInt(&ok);

    Logger::instance().log(QString("[getBook request]: id = %1").arg(bookId), Logger::LogLevel::Info);

    if (!ok) {
        return QHttpServerResponse("Invalid book ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    BookRepository bookRepository;
    std::optional<Book> book = bookRepository.fetchBookById(bookId);

    if (book) {
        QByteArray responseData = QJsonDocument(book->toJson()).toJson(QJsonDocument::Compact);
        return QHttpServerResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    return QHttpServerResponse("Book not found.", QHttpServerResponse::StatusCode::NotFound);
}

QHttpServerResponse BookHandler::getBookList(const QHttpServerRequest &request)
{
    bool ok;
    int limit = request.query().queryItemValue("limit").toInt(&ok);
    if (!ok) {
        return QHttpServerResponse("Invalid limit.", QHttpServerResponse::StatusCode::BadRequest);
    }

    int page = request.query().queryItemValue("page").toInt(&ok);
    if (!ok) {
        return QHttpServerResponse("Invalid page.", QHttpServerResponse::StatusCode::BadRequest);
    }

    qDebug() << "[getBookList request]: limit = " << limit << ", page = " << page;

    Logger::instance().log(QString("[getBookList request]: limit =  %1, page = %2").arg(limit)
                               .arg(page), Logger::LogLevel::Info);

    BookRepository bookRepository;
    QList<Book> books = bookRepository.fetchBooks(limit, page);

    if (!books.isEmpty()) {
        QJsonArray bookArray;
        for (const Book &book : books) {
            bookArray.append(book.toJson());
        }

        QJsonObject responseJson;
        responseJson["total_count"] = bookRepository.getBooksCount();
        responseJson["books"] = bookArray;

        QByteArray responseData = QJsonDocument(responseJson).toJson(QJsonDocument::Compact);

        return QHttpServerResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    Logger::instance().log(QString("[getBookList request] Not Found: limit =  %1, page = %2").arg(limit)
                               .arg(page), Logger::LogLevel::Warning);

    return QHttpServerResponse("Books not found.", QHttpServerResponse::StatusCode::NotFound);
}
