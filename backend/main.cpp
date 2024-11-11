#include <QCoreApplication>
#include <QLocale>
#include <QTranslator>
#include "servercontroller.h"
#include "dbcontroller.h"
#include "routefactory.h"
#include <QtSql/QSqlError>

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

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
        qDebug() << "Libum db opened from main.cpp";
    } else {
        qDebug() << "Libum db opening error in main.cpp" << dbController->getDatabase().lastError();
    }


    RouteFactory routefactory(server, dbController);
    routefactory.registerAllRoutes();

    if (!server->listen(QHostAddress::Any, ServerController::port())) {
        qCritical() << "Could not start server";
        return 1;
    }




    // Set up code that uses the Qt event loop here.
    // Call a.quit() or a.exit() to quit the application.
    // A not very useful example would be including
    // #include <QTimer>
    // near the top of the file and calling
    // QTimer::singleShot(5000, &a, &QCoreApplication::quit);
    // which quits the application after 5 seconds.

    // If you do not need a running Qt event loop, remove the call
    // to a.exec() or use the Non-Qt Plain C++ Application template.

    return a.exec();
}
