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
        return ResponseFactory::createResponse("Invalid book ID.",
                                               QHttpServerResponse::StatusCode::BadRequest);
    }

    std::optional<Book> book = BookRepository::fetchBookById(bookId);

    if (book) {
        QByteArray responseData = QJsonDocument(book->toJson()).toJson(QJsonDocument::Compact);
        return ResponseFactory::createJsonResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    return ResponseFactory::createResponse("Book not found.",
                                           QHttpServerResponse::StatusCode::NotFound);
}

QHttpServerResponse BookHandler::getBookList(const QHttpServerRequest &request)
{
    const int defautLimit = 24;
    const int defaultPage = 1;

    bool isLimitOk;
    int limit = request.query().queryItemValue("limit").toInt(&isLimitOk);
    if (!isLimitOk) {
        limit = defautLimit;
    }

    bool isPageOk;
    int page = request.query().queryItemValue("page").toInt(&isPageOk);
    if (!isPageOk) {
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

    Logger::instance().log(QString("[getBookList request] Not Found: limit =  %1, page = %2")
                               .arg(limit).arg(page), Logger::LogLevel::Warning);

    return ResponseFactory::createResponse("Books not found.",
                                           QHttpServerResponse::StatusCode::NotFound);
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

    return ResponseFactory::createResponse("Genres not found.",
                                           QHttpServerResponse::StatusCode::NotFound);
}

QHttpServerResponse BookHandler::getFileForReader(const QHttpServerRequest &request)
{
    bool ok;
    int bookId = request.query().queryItemValue("id").toInt(&ok);

    Logger::instance().log(QString("[%1 request]: id = %2").arg(__func__).arg(bookId),
                           Logger::LogLevel::Info);

    if (!ok) {
        return ResponseFactory::createResponse("Invalid book ID.",
                                               QHttpServerResponse::StatusCode::BadRequest);
    }

    std::optional<QString> filePathOpt = BookRepository::fetchFilePathForReader(bookId);

    if (!filePathOpt) {
        Logger::instance().log(QString("[%1 request]: Book not found. id = %2")
                                   .arg(__func__).arg(bookId), Logger::LogLevel::Warning);
        return ResponseFactory::createResponse("Book not found.",
                                               QHttpServerResponse::StatusCode::NotFound);
    }

    QFile file(filePathOpt.value());
    if(!file.open(QIODevice::ReadOnly)) {
        Logger::instance().log(QString("[%1 request]: File not found. id = %2")
                                   .arg(__func__).arg(bookId), Logger::LogLevel::Error);
        return ResponseFactory::createResponse("File not found.",
                                               QHttpServerResponse::StatusCode::InternalServerError);
    }

    QByteArray responseData = file.readAll();
    return ResponseFactory::createFileResponse(responseData, QHttpServerResponse::StatusCode::Ok);
}
