#ifndef BOOKSTATUSHANDLER_H
#define BOOKSTATUSHANDLER_H
#include "dbcontroller.h"
#include <QtHttpServer/QHttpServer>
#include "bookrepository.h"

class BookStatusHandler
{
public:
    static QHttpServerResponse getStatusOfBook(const QHttpServerRequest &request);
    static QHttpServerResponse getStatusList(const QHttpServerRequest &request);
    static QHttpServerResponse getBookListWithStatus(const QHttpServerRequest &request);

    static QHttpServerResponse setStatusForBook(const QHttpServerRequest &request);
    static QHttpServerResponse removeStatusFromBook(const QHttpServerRequest &request);

    static QHttpServerResponse addBookToFavorites(const QHttpServerRequest &request);
    static QHttpServerResponse removeBookFromFavorites(const QHttpServerRequest &request);

    BookStatusHandler();
};

#endif // BOOKSTATUSHANDLER_H
