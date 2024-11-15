#include <QCoreApplication>
#include <QLocale>
#include <QTranslator>
#include "servercontroller.h"
#include "dbcontroller.h"
#include "routefactory.h"
#include <QtSql/QSqlError>
#include "logger.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    Logger::instance().setLogFile("Libum.log");
    Logger::instance().enableConsoleOutput(true);
    Logger::instance().setLogLevel(Logger::LogLevel::Debug);

    QTranslator translator;
    const QStringList uiLanguages = QLocale::system().uiLanguages();
    for (const QString &locale : uiLanguages) {
        const QString baseName = "Libum_" + QLocale(locale).name();
        if (translator.load(":/i18n/" + baseName)) {
            a.installTranslator(&translator);
            break;
        }
    }

    ServerController::setServerSettings("http", "127.0.0.1", 4925);

    std::shared_ptr<QHttpServer> server = std::make_shared<QHttpServer>();
    std::shared_ptr<DBController> dbController = std::make_shared<DBController>();

    if(dbController->connect("localhost", "kirixo", "1111", "test", 5433)) {
        Logger::instance().log("Libum db opened from main.cpp", Logger::LogLevel::Info);
    } else {
        Logger::instance().log("Libum db opening error in main.cpp" +
                                   dbController->getDatabase().lastError().text(), Logger::LogLevel::Error);
    }


    RouteFactory routefactory(server, dbController);
    routefactory.registerAllRoutes();

    if (!server->listen(QHostAddress::Any, ServerController::port())) {
        Logger::instance().log("[CRITICAL] Could not start server", Logger::LogLevel::Error);
        return 1;
    }

    return a.exec();
}
