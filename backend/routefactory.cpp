#include "routefactory.h"

RouteFactory::RouteFactory(std::shared_ptr<QHttpServer> server, std::shared_ptr<DBController> dbcontroller)
    : server_(server), dbcontroller_(dbcontroller) {}

void RouteFactory::registerAllRoutes()
{
    handleOptionsRequest();
    setupUserRoutes();
    setupBookRoutes();
    setupCartRoutes();
}

void RouteFactory::setupUserRoutes() {
    server_->route("/api/users", QHttpServerRequest::Method::Get, UserHandler::getUser);
    server_->route("/api/users", QHttpServerRequest::Method::Patch, UserHandler::updateUser);
    server_->route("/api/users/register", QHttpServerRequest::Method::Post, UserHandler::registerUser);
    server_->route("/api/users/login", QHttpServerRequest::Method::Post, UserHandler::loginUser);
    server_->route("/api/users/list", QHttpServerRequest::Method::Get, UserHandler::getUserList);
}

void RouteFactory::setupBookRoutes() {
    server_->route("/api/books", QHttpServerRequest::Method::Get, BookHandler::getBook);
    server_->route("/api/books/list", QHttpServerRequest::Method::Get, BookHandler::getBookList);
    server_->route("/api/books/genres", QHttpServerRequest::Method::Get, BookHandler::getGenresList);
    server_->route("/api/books/file/reader", QHttpServerRequest::Method::Get, BookHandler::getFileForReader);
}

void RouteFactory::setupCartRoutes() {
    server_->route("/api/cart/add", QHttpServerRequest::Method::Post, CartHandler::addBook);
    server_->route("/api/cart", QHttpServerRequest::Method::Get, CartHandler::getUsersCart);
    server_->route("/api/cart", QHttpServerRequest::Method::Delete, CartHandler::removeBook);
    server_->route("/api/cart/clear", QHttpServerRequest::Method::Delete, CartHandler::clearCart);
}

void RouteFactory::handleOptionsRequest()
{
    server_->route("/*", QHttpServerRequest::Method::Options, [this](const QHttpServerRequest &request) {
        QHttpServerResponse response(
            QByteArray(),
            QHttpServerResponse::StatusCode::NoContent
            );

        response.setHeader("Content-Type", "text/plain");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        return response;
    });
}
