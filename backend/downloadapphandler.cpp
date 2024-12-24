#include "downloadapphandler.h"

QHttpServerResponse DownloadAppHandler::handleRequest(const QHttpServerRequest &request) {
    AppRepository repository_;
    QString filePath = repository_.getAppFilePath();
    if (!repository_.appFileExists()) {
        return ResponseFactory::createResponse("File not found.",
                                               QHttpServerResponse::StatusCode::NotFound);
    }

    QFile file(filePath);
    if (!file.open(QIODevice::ReadOnly)) {
        return ResponseFactory::createResponse("Unable to open file.",
                                               QHttpServerResponse::StatusCode::InternalServerError);
    }


    QByteArray responseData = file.readAll();
    file.close();
    return ResponseFactory::createFileResponse(responseData, QHttpServerResponse::StatusCode::Ok,
                                               ResponseFactory::FileExtentions::Apk);
}
