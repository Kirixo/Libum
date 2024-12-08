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
