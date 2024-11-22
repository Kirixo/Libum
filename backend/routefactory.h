#ifndef ROUTEFACTORY_H
#define ROUTEFACTORY_H
#include <QtHttpServer/QHttpServer>
#include "carthandler.h"
#include "dbcontroller.h"
#include "userhandler.h"
#include "bookhandler.h"


class RouteFactory
{
public:
    explicit RouteFactory(std::shared_ptr<QHttpServer> server, std::shared_ptr<DBController> dbcontroller);

    void registerAllRoutes();

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

    void setupBookRoutes() {
        server_->route("/api/books", QHttpServerRequest::Method::Get, BookHandler::getBook);
        server_->route("/api/books/list", QHttpServerRequest::Method::Get, BookHandler::getBookList);
        server_->route("/api/books/genres", QHttpServerRequest::Method::Get, BookHandler::getGenresList);
    }

    void setupCartRoutes() {
        server_->route("/api/cart/add", QHttpServerRequest::Method::Post, CartHandler::addBook);
        server_->route("/api/cart", QHttpServerRequest::Method::Get, CartHandler::getUsersCart);
        server_->route("/api/cart", QHttpServerRequest::Method::Delete, CartHandler::removeBook);
        server_->route("/api/cart/clear", QHttpServerRequest::Method::Delete, CartHandler::clearCart);
    }

    void handleOptionsRequest();
};



#endif // ROUTEFACTORY_H
