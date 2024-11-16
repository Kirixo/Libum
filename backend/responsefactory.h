#ifndef RESPONSEFACTORYH_H
#define RESPONSEFACTORYH_H
#include <QtHttpServer/QHttpServerResponse>
#include <QJsonDocument>
#include <QJsonObject>

class ResponseFactory
{
public:
    static QHttpServerResponse createResponse(const QString &content, QHttpServerResponse::StatusCode statusCode) {
        QHttpServerResponse response = QHttpServerResponse(
            content.toUtf8(), statusCode
        );
        addCorsHeaders(response);
        response.setHeader("Content-Type", "text/plain");
        return response;
    }

    static QHttpServerResponse createJsonResponse(const QByteArray &content, QHttpServerResponse::StatusCode statusCode) {
        QHttpServerResponse response = QHttpServerResponse(
            content, statusCode
        );
        addCorsHeaders(response);
        response.setHeader("Content-Type", "application/json");
        return response;
    }

private:
    static void addCorsHeaders(QHttpServerResponse &response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
};

#endif // RESPONSEFACTORYH_H
