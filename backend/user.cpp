#include "user.h"
#include "logger.h"
#include <qsqlerror.h>

User::User()
    : id_(-1), email_(QString()), login_(QString()), password_(QString())
{
}

User::User(quint64 id)
{
    fetchByID(id);
}

User::User(const QString &email, const QString &login, const QString &password)
    : id_(-1), email_(email), login_(login), password_(password)
{
}

User::User(const User &user)
{

}

User::User(User &&user)
    : email_(std::move(user.email_)), login_(std::move(user.login_)), password_(std::move(user.password_))
{
}

User::~User()
{

}

QJsonObject User::toJson() const
{
    QJsonObject json;
    json["id"] = id_;
    json["email"] = email_;
    json["login"] = login_;
    return json;
}

bool User::saveInDB()
{
    if(email_.isNull() || login_.isNull() || password_.isNull()) {
        Logger::instance().log(QString("[saveInDB] email, login or password is NULL!"), Logger::LogLevel::Warning);
        return false;
    }

    QSqlDatabase db = DBController::getDatabase();

    QString queryString = R"(
        INSERT INTO users (email, login, password)
        VALUES (:email, :login, :password)
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":email", email_);
    query.bindValue(":login", login_);
    query.bindValue(":password", password_);
    if(query.exec()) {
        return true;
    }
    Logger::instance().log(QString("[saveInDB] Database query error!")
                               .append(query.lastError().text()), Logger::LogLevel::Warning);
    return false;
}

void User::authorize(const QString &email, const QString &password)
{
    if(email.isNull() || password.isNull()) {
        Logger::instance().log(QString("[authorize] email or password is NULL!"), Logger::LogLevel::Warning);
        return;
    }

    QSqlDatabase db = DBController::getDatabase();

    QString queryString = R"(
        SELECT id, email, login FROM users
        WHERE users.email = :email AND users.password = :password;
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":email", email);
    query.bindValue(":password", password);

    if (query.exec() && query.next()) {
        id_ = query.value("id").toInt();
        email_ = query.value("email").toString();
        login_ = query.value("login").toString();
    } else {
        Logger::instance().log(QString("[authorize] Possible query error!")
                                   .append(query.lastError().text()), Logger::LogLevel::Warning);
    }
}

void User::fetchByID(quint64 id)
{
    QSqlDatabase db = DBController::getDatabase();

    QString queryString = "SELECT id, email, login FROM users "
                          "WHERE users.id = :id;";
    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":id", id);

    if (query.exec() && query.next()) {
        id_ = query.value("id").toInt();
        email_ = query.value("email").toString();
        login_ = query.value("login").toString();
        return;
    }

    id_ = -1;
}

bool User::checkExistanceInDB(const QString &email)
{
    QString queryString = R"(
        SELECT 1 FROM users
        WHERE users.email = :email;
    )";

    QSqlQuery query(DBController::getDatabase());
    query.prepare(queryString);
    query.bindValue(":email", email);

    if (query.exec() && query.next()) {
        return 1;
    } else {
        return 0;
    }
}

bool User::exists()
{
    return id_ != -1;
}

qint64 User::id() const
{
    return id_;
}

QString User::email() const
{
    return email_;
}

QString User::login() const
{
    return login_;
}

