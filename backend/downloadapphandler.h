#ifndef DOWNLOADAPPHANDLER_H
#define DOWNLOADAPPHANDLER_H
#include "apprepository.h"
#include "responsefactory.h"
#include <QHttpServerResponse>


class DownloadAppHandler {
public:
    static QHttpServerResponse handleRequest(const QHttpServerRequest& request);
};


#endif // DOWNLOADAPPHANDLER_H
