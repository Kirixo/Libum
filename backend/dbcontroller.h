#ifndef DBCONTROLLER_H
#define DBCONTROLLER_H
#include <QtSql/QSqlDatabase>
#include <QtSql/QSqlQuery>

class DBController
{
public:
    DBController() = default;

    static bool connect(const QString& host, const QString& username, const QString& password, const QString& database, int port = 5432);
    static bool close();
    static QSqlDatabase& getDatabase();

private:
    static QSqlDatabase db;
};

#endif // DBCONTROLLER_H
