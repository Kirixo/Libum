#include "userhandler.h"
#include "user.h"
#include "qjsondocument.h"
#include "qjsonobject.h"
#include <QFile>

UserHandler::UserHandler() {}

QHttpServerResponse UserHandler::registerUser(const QHttpServerRequest &request)
{
    QJsonParseError err;
    const auto json = QJsonDocument::fromJson(request.body(), &err).object();

    if (!json.contains("email") || !json.contains("password") || !json.contains("login"))
        return QHttpServerResponse(QHttpServerResponder::StatusCode::BadRequest);

    User user = User(json.value("email").toString(), json.value("login").toString(), json.value("password").toString());

    if(user.saveInDB()){
        return QHttpServerResponse(QHttpServerResponder::StatusCode::Ok);
    }

    return QHttpServerResponse(QHttpServerResponder::StatusCode::InternalServerError);
    // QString filePath = "C:/Users/Kirixo/Pictures/ARTS/Arts/BlueArchive/GSJX8OwaUAIB7FU.jpg";
    // QFile file(filePath);

    // if (file.open(QIODevice::ReadOnly)) {
    //     QByteArray imageData = file.readAll();
    //     file.close();
    //     return QHttpServerResponse("image/jpeg", imageData);
    // } else {
    //     return QHttpServerResponse("Avatar not found", QHttpServerResponse::StatusCode::NotFound);
    // }
}

QHttpServerResponse UserHandler::loginUser(const QHttpServerRequest &request)
{

}

QHttpServerResponse UserHandler::getUser(const QHttpServerRequest &request)
{
    auto userId = request.query().queryItemValue("id");
    qDebug() << "Method: Get; getUser; userId = " << userId;
    qDebug() << request.body();


    return QHttpServerResponse(QHttpServerResponder::StatusCode::Ok);
}

QHttpServerResponse UserHandler::getUserList(const QHttpServerRequest &request)
{

}

QHttpServerResponse UserHandler::updateUser(const QHttpServerRequest &request)
{

}
