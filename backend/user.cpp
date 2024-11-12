#include "user.h"

User::User()
    : id_(-1), email_(QString()), login_(QString()), password_(QString())
{
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

void User::authorize(const QString &email, const QString &password)
{
    if(email.isNull() || password.isNull()) {
        return;
    }

    QSqlDatabase db = DBController::getDatabase();

    QString queryString = "SELECT id, email, login FROM users "
                          "WHERE users.email = :email AND users.password = :password;";
    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":email", email);
    query.bindValue(":password", password);

    if (query.exec() && query.next()) {
        id_ = query.value("id").toInt();
        email_ = query.value("email").toString();
        login_ = query.value("login").toString();
        return;
    }
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

