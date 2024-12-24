#ifndef RESPONSEFACTORYH_H
#define RESPONSEFACTORYH_H
#include <QtHttpServer/QHttpServerResponse>
#include <QJsonDocument>
#include <QJsonObject>

class ResponseFactory
{
public:
    enum class FileExtentions {
        Apk,
        Unknown
    };

    static QHttpServerResponse createResponse(const QString &content, QHttpServerResponse::StatusCode statusCode);

    static QHttpServerResponse createJsonResponse(const QByteArray &content, QHttpServerResponse::StatusCode statusCode);

    static QHttpServerResponse createFileResponse(const QByteArray &content, QHttpServerResponse::StatusCode statusCode,
                                                  FileExtentions fileExtension = FileExtentions::Unknown);
private:
    static void addCorsHeaders(QHttpServerResponse &response);
};

#endif // RESPONSEFACTORYH_H
