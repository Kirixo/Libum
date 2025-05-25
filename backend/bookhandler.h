#ifndef BOOKHANDLER_H
#define BOOKHANDLER_H
#include "dbcontroller.h"
#include <QtHttpServer/QHttpServer>
#include "bookrepository.h"

class BookHandler
{
public:
    BookHandler();

    static QHttpServerResponse getBook(const QHttpServerRequest &request);
    static QHttpServerResponse getBookList(const QHttpServerRequest &request);
    static QHttpServerResponse getGenresList(const QHttpServerRequest &request);

    static QHttpServerResponse getFileForReader(const QHttpServerRequest &request);
    static QHttpServerResponse getFilteredBookList(const QHttpServerRequest &request);

};

#endif // BOOKHANDLER_H
