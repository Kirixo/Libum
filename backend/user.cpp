#include "user.h"

User::User(const QString &email, const QString &login, const QString &password)
    : email_(email), login_(login), password_(password)
{
}

User::User(const User &user)
{

}

User::User(User &&user)
    : email_(std::move(user.email_)), login_(std::move(user.login_)), password_(std::move(user.password_))
{
}

bool User::saveInDB()
{
    if(email_.isNull() || login_.isNull() || password_.isNull()) {
        return false;
    }
    QSqlDatabase db = DBController::getDatabase();

    QString queryString = "INSERT INTO users (email, login, password)"
                          "VALUES (:email, :login, :password)";
    QSqlQuery query;
    query.prepare(queryString);
    query.bindValue(":email", email_);
    query.bindValue(":login", login_);
    query.bindValue(":password", password_);
    if(query.exec()) {
        return true;
    }

    return false;
}

