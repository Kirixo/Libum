#ifndef CARTHANDLER_H
#define CARTHANDLER_H
#include <QtHttpServer/QHttpServer>

class CartHandler
{
public:
    CartHandler();

    static QHttpServerResponse addBook(const QHttpServerRequest &request);
    static QHttpServerResponse getUsersCart(const QHttpServerRequest &request);
};

#endif // CARTHANDLER_H
