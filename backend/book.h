#ifndef BOOK_H
#define BOOK_H
#include <QObject>
#include <QUrl>
#include "genre.h"
#include "jsonable.h"

class Book : Jsonable
{
public:
    Book();
    ~Book();

    bool exists();

    void setId(qint64 newId);
    void setTitle(const QString &newTitle);
    void setCover(const QString &newCover);
    void setMeanScore(float newMeanScore);
    void setDescription(const QString &newDescription);
    void setGenres(const QList<Genre> &newGenres);
    void setLanguage(const QString &newLanguage);
    void setYear(qint16 newYear);
    void setAuthor(const QString &newAuthor);
    void setPrice(float newPrice);

    qint64 id() const;
    const QString& title() const;
    const QString& cover() const;
    float meanScore() const;
    const QString& description() const;
    const QList<Genre>& genres() const;
    const QString& language() const;
    qint16 year() const;
    const QString& author() const;
    float price() const;

private:
    qint64 id_ {-1};
    QString title_;
    QString cover_;
    float meanScore_ {-1};
    qint16 pageCount_ {-1};
    QString description_;
    QList<Genre> genres_;
    QString language_;
    qint16 year_ {-0x7FFF};
    QString author_;
    float price_ {-1};

    // Jsonable interface
public:
    QJsonObject toJson() const override;

    qint16 pageCount() const;
    void setPageCount(qint16 newPageCount);
};

#endif // BOOK_H
