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
}

QHttpServerResponse UserHandler::loginUser(const QHttpServerRequest &request)
{
    QJsonParseError err;
    const auto json = QJsonDocument::fromJson(request.body(), &err).object();

    if (err.error != QJsonParseError::NoError) {
        return QHttpServerResponse("Invalid JSON format.", QHttpServerResponse::StatusCode::BadRequest);
    }

    if (!json.contains("email") || !json.contains("password")) {
        return QHttpServerResponse("Missing email or password.", QHttpServerResponse::StatusCode::BadRequest);
    }

    User user;
    user.authorize(json.value("email").toString(), json.value("password").toString());

    if (user.exists()) {
        QByteArray responseData = QJsonDocument(user.toJson()).toJson(QJsonDocument::Compact);

        return QHttpServerResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    return QHttpServerResponse("Invalid email or password.", QHttpServerResponse::StatusCode::Unauthorized);
}


QHttpServerResponse UserHandler::getUser(const QHttpServerRequest &request)
{
    bool ok;
    int userId = request.query().queryItemValue("id").toInt(&ok);

    qDebug() << "[getUser request]: id = " << userId;

    if (!ok) {
        return QHttpServerResponse("Invalid user ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    User user(userId);

    if (user.exists()) {
        QByteArray responseData = QJsonDocument(user.toJson()).toJson(QJsonDocument::Compact);

        return QHttpServerResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    return QHttpServerResponse("User not found.", QHttpServerResponse::StatusCode::NotFound);
}


QHttpServerResponse UserHandler::getUserList(const QHttpServerRequest &request)
{
    // TODO:
    return nullptr;
}

QHttpServerResponse UserHandler::updateUser(const QHttpServerRequest &request)
{
    // TODO:
    return nullptr;
}
