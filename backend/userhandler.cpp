#include "userhandler.h"
#include "logger.h"
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

    QString email = json.value("email").toString();
    QString login = json.value("login").toString();
    QString password = json.value("password").toString();

    Logger::instance().log(QString("[registerUser request] email = %1, login = %2, password = %3")
                               .arg(email).arg(login).arg(password), Logger::LogLevel::Info);

    User user = User(email, login, password);

    if(user.saveInDB()){
        return QHttpServerResponse(QHttpServerResponder::StatusCode::Ok);
    }

    Logger::instance().log(QString("[registerUser request] email = %1, login = %2, password = %3")
                               .arg(email).arg(login).arg(password), Logger::LogLevel::Error);

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

    QString email = json.value("email").toString();
    QString password = json.value("password").toString();

    Logger::instance().log(QString("[loginUser request] email = %1, password = %2")
                               .arg(email).arg(password), Logger::LogLevel::Info);

    User user;
    user.authorize(email, password);

    if (user.exists()) {
        QByteArray responseData = QJsonDocument(user.toJson()).toJson(QJsonDocument::Compact);

        return QHttpServerResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    Logger::instance().log(QString("[loginUser request] User hasn't been authorized email = %1, password = %2")
                               .arg(email).arg(password), Logger::LogLevel::Info);

    return QHttpServerResponse("Invalid email or password.", QHttpServerResponse::StatusCode::Unauthorized);
}


QHttpServerResponse UserHandler::getUser(const QHttpServerRequest &request)
{
    bool ok;
    int userId = request.query().queryItemValue("id").toInt(&ok);

    Logger::instance().log(QString("[getUser request] id = %1").arg(userId), Logger::LogLevel::Info);

    if (!ok) {
        return QHttpServerResponse("Invalid user ID.", QHttpServerResponse::StatusCode::BadRequest);
    }

    User user(userId);

    if (user.exists()) {
        QByteArray responseData = QJsonDocument(user.toJson()).toJson(QJsonDocument::Compact);

        return QHttpServerResponse(responseData, QHttpServerResponse::StatusCode::Ok);
    }

    Logger::instance().log(QString("[loginUser request] user not found id = %1")
                               .arg(userId), Logger::LogLevel::Warning);

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
