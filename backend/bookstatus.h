#ifndef BOOKSTATUS_H
#define BOOKSTATUS_H
#include "jsonable.h"
#include <qobject.h>
#include <qtypes.h>

class BookStatus : Jsonable
{
public:
    explicit BookStatus() {};
    explicit BookStatus(quint64 id, const QString& name)
        : id(id), name(name) {}
    explicit BookStatus(quint64 id, QString&& name)
        : id(id), name(std::move(name)) {}

    quint64 id;
    QString name;

    // Jsonable interface
public:
    QJsonObject toJson() const override;
};

#endif // BOOKSTATUS_H
