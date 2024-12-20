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

    void setupUserRoutes();

    void setupBookRoutes();

    void setupCartRoutes();

    void handleOptionsRequest();
};



#endif // ROUTEFACTORY_H
