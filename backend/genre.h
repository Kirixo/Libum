#ifndef GENRE_H
#define GENRE_H

#include <QString>
#include <QJsonObject>
#include "jsonable.h"

class Genre : Jsonable {
public:
    Genre();
    Genre(const Genre &) = default;
    Genre(Genre &&) = default;
    Genre &operator=(const Genre &) = default;
    Genre &operator=(Genre &&) = default;
    Genre(int id, const QString &name);

    int id() const;
    void setId(int id);

    const QString& name() const;
    void setName(const QString &name);

private:
    int id_;
    QString name_;

    // Jsonable interface
public:
    QJsonObject toJson() const override;
};

#endif // GENRE_H
