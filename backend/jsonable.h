#ifndef JSONABLE_H
#define JSONABLE_H

#include <QJsonObject>
#include <QJsonArray>

class Jsonable {
public:
    virtual ~Jsonable() {};
    virtual QJsonObject toJson() const = 0;
};

#endif // JSONABLE_H
