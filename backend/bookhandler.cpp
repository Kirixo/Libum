#include "bookhandler.h"
#include <qjsondocument.h>
#include "genrerepository.h"
#include "logger.h"
#include "responsefactory.h"


BookHandler::BookHandler() {}

QHttpServerResponse BookHandler::getBook(const QHttpServerRequest &request)
{
    bool ok;
    int bookId = request.query().queryItemValue("id").toInt(&ok);

    Logger::instance().log(QString("[getBook request]: id = %1").arg(bookId), Logger::LogLevel::Info);

    if (!ok) {
        return ResponseFactory::createResponse("Invalid book ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    std::optional<Book> book = BookRepository::fetchBookById(bookId);

    if (book) {
        QByteArray responseData = QJsonDocument(book->toJson()).toJson(QJsonDocument::Compact);
        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Book not found.", QHttpServerResponse::StatusCode::NotFound);
}

QHttpServerResponse BookHandler::getBookList(const QHttpServerRequest &request)
{
    const int defautLimit = 24;
    const int defaultPage = 1;

    bool ok;
    int limit = request.query().queryItemValue("limit").toInt(&ok);
    if (!ok) {
        limit = defautLimit;
    }

    int page = request.query().queryItemValue("page").toInt(&ok);
    if (!ok) {
        page = defaultPage;
    }

    Logger::instance().log(QString("[getBookList request]: limit =  %1, page = %2").arg(limit)
                               .arg(page), Logger::LogLevel::Info);

    QList<Book> books = BookRepository::fetchBooks(limit, page);

    if (!books.isEmpty()) {
        QJsonArray bookArray;
        for (const Book &book : books) {
            bookArray.append(book.toJson());
        }

        QJsonObject responseJson;
        responseJson["total_count"] = BookRepository::getBooksCount();
        responseJson["books"] = bookArray;

        QByteArray responseData = QJsonDocument(responseJson).toJson(QJsonDocument::Compact);

        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    Logger::instance().log(QString("[getBookList request] Not Found: limit =  %1, page = %2").arg(limit)
                               .arg(page), Logger::LogLevel::Warning);

    return ResponseFactory::createResponse("Books not found.", QHttpServerResponse::StatusCode::NotFound);
}

QHttpServerResponse BookHandler::getGenresList(const QHttpServerRequest &request)
{
    QList<Genre> genres = GenreRepository::getAllGenres();

    Logger::instance().log(QString("[getGenreList request]"), Logger::LogLevel::Info);

    if (!genres.isEmpty()) {
        QJsonArray genreArray;
        for (const Genre &genre : genres) {
            genreArray.append(genre.toJson());
        }

        QJsonObject responseJson;
        responseJson["genres"] = genreArray;

        QByteArray responseData = QJsonDocument(responseJson).toJson(QJsonDocument::Compact);

        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    Logger::instance().log(QString("[getGenreList request] Not Found"), Logger::LogLevel::Warning);

    return ResponseFactory::createResponse("Genres not found.", QHttpServerResponse::StatusCode::NotFound);
}
