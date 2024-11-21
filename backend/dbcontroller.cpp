#include "dbcontroller.h"

QSqlDatabase DBController::db;

bool DBController::connect(const QString &host, const QString &username, const QString &password, const QString &database, int port)
{
    db = QSqlDatabase::addDatabase("QPSQL");
    db.setHostName(host);
    db.setUserName(username);
    db.setPassword(password);
    db.setDatabaseName(database);
    db.setPort(port);
    if(db.open()){
        return 1;
    } else {
        return 0;
    }
}


bool DBController::close()
{
    if (db.isOpen()) {
        db.close();
        return true;
    }
    return false;
}

QSqlDatabase& DBController::getDatabase()
{
    return db;
}
