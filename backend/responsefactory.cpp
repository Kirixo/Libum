#include "responsefactory.h"


QHttpServerResponse ResponseFactory::createResponse(const QString &content, QHttpServerResponse::StatusCode statusCode)
{
    QHttpServerResponse response = QHttpServerResponse(
        content.toUtf8(), statusCode
        );
    addCorsHeaders(response);
    response.setHeader("Content-Type", "text/plain");
    return response;
}

QHttpServerResponse ResponseFactory::createJsonResponse(const QByteArray &content, QHttpServerResponse::StatusCode statusCode)
{
    QHttpServerResponse response = QHttpServerResponse(
        content, statusCode
        );
    addCorsHeaders(response);
    response.setHeader("Content-Type", "application/json");
    return response;
}

QHttpServerResponse ResponseFactory::createFileResponse(const QByteArray &content,
                                                        QHttpServerResponse::StatusCode statusCode,
                                                        FileExtentions fileExtension)
{
    QHttpServerResponse response = QHttpServerResponse(
        content, statusCode
        );

    QString contentType = "application/octet-stream";

    if (fileExtension == FileExtentions::Apk) {
        contentType = "application/vnd.android.package-archive";
        response.setHeader("Content-Disposition", "attachment; filename=\"Libum.apk\"");
    }
    response.setHeader("Content-Type", contentType.toUtf8());

    return response;
}

void ResponseFactory::addCorsHeaders(QHttpServerResponse &response)
{
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
}
