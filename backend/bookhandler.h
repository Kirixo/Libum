#ifndef BOOKHANDLER_H
#define BOOKHANDLER_H
#include "dbcontroller.h"
#include <QtHttpServer/QHttpServer>

class BookHandler
{
public:
    BookHandler();

    static QHttpServerResponse getBookList(const QHttpServerRequest &request);

};

#endif // BOOKHANDLER_H
