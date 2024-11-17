#ifndef RESPONSEFACTORYH_H
#define RESPONSEFACTORYH_H
#include <QtHttpServer/QHttpServerResponse>
#include <QJsonDocument>
#include <QJsonObject>

class ResponseFactory
{
public:
    static QHttpServerResponse createResponse(const QString &content, QHttpServerResponse::StatusCode statusCode);

    static QHttpServerResponse createJsonResponse(const QByteArray &content, QHttpServerResponse::StatusCode statusCode);

private:
    static void addCorsHeaders(QHttpServerResponse &response);
};

#endif // RESPONSEFACTORYH_H
