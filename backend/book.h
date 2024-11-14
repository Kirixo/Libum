#ifndef BOOK_H
#define BOOK_H
#include <QObject>
#include <qurl.h>
#include "dbcontroller.h"
#include "jsonable.h"

class Book : Jsonable
{
public:
    Book();
    ~Book();

    QJsonObject toJson() const override;
    void fetch(quint64 id);

    bool exists();

private:
    qint64 id_;
    QString title_;
    QString cover_;
    float meanScore_;
    QString description_;
    QList<QString> genres_;
    QString language_;
    qint16 year_;
    QString author_;
    float price_;

};

#endif // BOOK_H
