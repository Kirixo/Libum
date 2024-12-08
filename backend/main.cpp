#include <QCoreApplication>
#include <QSettings>
#include <QTranslator>
#include "servercontroller.h"
#include "dbcontroller.h"
#include "routefactory.h"
#include <QtSql/QSqlError>
#include "logger.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    // Load configuration
    QSettings settings("config.ini", QSettings::IniFormat);

    // Logger configuration
    QString logFile = settings.value("Logger/logFile", "Libum.log").toString();
    bool enableConsoleOutput = settings.value("Logger/enableConsoleOutput", true).toBool();
    QString logLevel = settings.value("Logger/logLevel", "Debug").toString();

    Logger::instance().setLogFile(logFile);
    Logger::instance().enableConsoleOutput(enableConsoleOutput);

    if (logLevel == "Debug") {
        Logger::instance().setLogLevel(Logger::LogLevel::Debug);
    } else if (logLevel == "Info") {
        Logger::instance().setLogLevel(Logger::LogLevel::Info);
    } else if (logLevel == "Error") {
        Logger::instance().setLogLevel(Logger::LogLevel::Error);
    }

    // Server settings
    QString protocol = settings.value("Server/protocol", "http").toString();
    QString host = settings.value("Server/host", "127.0.0.1").toString();
    int port = settings.value("Server/port", 4925).toInt();

    ServerController::setServerSettings(protocol, host, port);

    std::shared_ptr<QHttpServer> server = std::make_shared<QHttpServer>();
    std::shared_ptr<DBController> dbController = std::make_shared<DBController>();

    // Database configuration
    QString dbHost = settings.value("Database/host", "localhost").toString();
    QString dbUser = settings.value("Database/user", "user").toString();
    QString dbPassword = settings.value("Database/password", "password").toString();
    QString dbName = settings.value("Database/name", "database").toString();
    int dbPort = settings.value("Database/port", "5432").toInt();

    if (dbController->connect(dbHost, dbUser, dbPassword, dbName, dbPort)) {
        Logger::instance().log(dbName + " database opened from main.cpp", Logger::LogLevel::Info);
    } else {
        Logger::instance().log(dbName + " database opening error in main.cpp: " +
                                   dbController->getDatabase().lastError().text(), Logger::LogLevel::Error);
    }

    // Set up routes
    RouteFactory routefactory(server, dbController);
    routefactory.registerAllRoutes();

    // Start server
    if (!server->listen(QHostAddress::Any, ServerController::port())) {
        Logger::instance().log("[CRITICAL] Could not start server", Logger::LogLevel::Error);
        return 1;
    }

    return a.exec();
}
