#include "servercontroller.h"

QString ServerController::scheme_;
QString ServerController::host_;
int ServerController::port_;

ServerController::ServerController(QObject *parent)
    : QObject{parent}
{}

void ServerController::setServerSettings(const QString& scheme, const QString& host, int port)
{
    scheme_ = scheme;
    host_ = host;
    port_ = port;
}

QString ServerController::scheme()
{
    return scheme_;
}

QString ServerController::host()
{
    return host_;
}

int ServerController::port()
{
    return port_;
}
