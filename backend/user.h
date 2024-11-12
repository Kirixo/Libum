#ifndef USER_H
#define USER_H
#include "dbcontroller.h"
#include <QtSql/QSqlQuery>

class User
{
public:
    // User(int id, const std::string& email, const std::string& login, const std::string& password);
    User();
    User(quint64 id);
    User(const QString& email, const QString& login, const QString& password);
    User(const User& user);
    User(User&& user);

    bool saveInDB();
    void authorize(const QString& email, const QString& password);
    void initializeByID(quint64 id);
    bool exists();


    void setId(qint64 newId);
    void setEmail(const QString &newEmail);
    void setLogin(const QString &newLogin);
    void setPassword(const QString &newPassword);

    qint64 id() const;
    QString email() const;
    QString login() const;

private:
    qint64 id_;
    QString email_;
    QString login_;
    QString password_;


};

#endif // USER_H
