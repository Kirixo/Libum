#ifndef ROUTEFACTORY_H
#define ROUTEFACTORY_H
#include <QtHttpServer/QHttpServer>
#include "dbcontroller.h"
#include "userhandler.h"


class RouteFactory
{
public:
    explicit RouteFactory(std::shared_ptr<QHttpServer> server, std::shared_ptr<DBController> dbcontroller);

    void registerAllRoutes() {
        setupUserRoutes();
    }

private:
    std::shared_ptr<DBController> dbcontroller_;
    std::shared_ptr<QHttpServer> server_;

    void setupUserRoutes() {
        server_->route("/api/users", QHttpServerRequest::Method::Get, UserHandler::getUser);
        server_->route("/api/users", QHttpServerRequest::Method::Patch, UserHandler::updateUser);
        server_->route("/api/users/register", QHttpServerRequest::Method::Post, UserHandler::registerUser);
        server_->route("/api/users/login", QHttpServerRequest::Method::Post, UserHandler::loginUser);
        server_->route("/api/users/list", QHttpServerRequest::Method::Get, UserHandler::getUserList);
    }
};



#endif // ROUTEFACTORY_H
