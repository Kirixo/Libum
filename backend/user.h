#ifndef USER_H
#define USER_H
#include "dbcontroller.h"
#include <QtSql/QSqlQuery>

class User
{
public:
    // User(int id, const std::string& email, const std::string& login, const std::string& password);
    User(const QString& email, const QString& login, const QString& password);
    User(const User& user);
    User(User&& user);

    bool saveInDB();


private:
    qint64 id_;
    QString email_;
    QString login_;
    QString password_;


};

#endif // USER_H
