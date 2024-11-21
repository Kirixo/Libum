#ifndef SERVERCONTROLLER_H
#define SERVERCONTROLLER_H

#include <QObject>


class ServerController : public QObject
{
    Q_OBJECT
public:
    explicit ServerController(QObject *parent = nullptr);

    static void setServerSettings(const QString& scheme, const QString& host, int port);

    static QString scheme();
    static QString host();
    static int port();

private:
    static QString scheme_;
    static QString host_;
    static int port_;
};

#endif // SERVERCONTROLLER_H
