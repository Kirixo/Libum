#ifndef USERHANDLER_H
#define USERHANDLER_H
#include "dbcontroller.h"
#include <QtHttpServer/QHttpServer>

class UserHandler
{
public:
    UserHandler();

    static QHttpServerResponse registerUser(const QHttpServerRequest &request);
    static QHttpServerResponse loginUser(const QHttpServerRequest &request);
    static QHttpServerResponse getUser(const QHttpServerRequest &request);
    static QHttpServerResponse getUserList(const QHttpServerRequest &request);
    static QHttpServerResponse updateUser(const QHttpServerRequest &request);

private:
    std::unique_ptr<DBController> dbcontroller_;
};

#endif // USERHANDLER_H
