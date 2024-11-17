#ifndef USER_H
#define USER_H
#include "dbcontroller.h"
#include <QtSql/QSqlQuery>
#include "jsonable.h"

class User : Jsonable
{
public:
    User();
    User(quint64 id);
    User(const QString& email, const QString& login, const QString& password);
    User(const User& user);
    User(User&& user);
    ~User();

    bool saveInDB();
    void authorize(const QString& email, const QString& password);
    void initializeByID(quint64 id);
    static bool checkExistanceInDB(const QString& email);
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

    // Jsonable interface
public:
    QJsonObject toJson() const override;
};

#endif // USER_H
