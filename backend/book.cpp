#include "book.h"

Book::Book() : id_(-1), meanScore_(0)
{
}

Book::~Book() {}

QJsonObject Book::toJson() const
{
    QJsonObject json;
    json["id"] = id_;
    json["title"] = title_;
    json["cover"] = cover_;
    json["description"] = description_;

    QJsonArray genreArray;
    for (const auto& genre : genres_) {
        QJsonObject tmpJsonGenre;
        tmpJsonGenre["id"] = genre.id();
        tmpJsonGenre["name"] = genre.name();
        genreArray.append(tmpJsonGenre);
    }
    json["genres"] = genreArray;

    json["language"] = language_;
    json["author"] = author_;
    json["mean_score"] = meanScore_ ? QJsonValue(std::round(meanScore_ * 100) / 100.0) : QJsonValue::Null;
    json["price"] = std::round(price_ * 100) / 100.0;

    return json;
}

void Book::setId(qint64 newId)
{
    id_ = newId;
}

void Book::setTitle(const QString &newTitle)
{
    title_ = newTitle;
}

void Book::setCover(const QString &newCover)
{
    cover_ = newCover;
}

void Book::setMeanScore(float newMeanScore)
{
    meanScore_ = newMeanScore;
}

void Book::setDescription(const QString &newDescription)
{
    description_ = newDescription;
}

void Book::setGenres(const QList<Genre> &newGenres)
{
    genres_ = newGenres;
}

void Book::setLanguage(const QString &newLanguage)
{
    language_ = newLanguage;
}

void Book::setYear(qint16 newYear)
{
    year_ = newYear;
}

void Book::setAuthor(const QString &newAuthor)
{
    author_ = newAuthor;
}

void Book::setPrice(float newPrice)
{
    price_ = newPrice;
}

qint64 Book::id() const
{
    return id_;
}

const QString& Book::title() const
{
    return title_;
}

const QString& Book::cover() const
{
    return cover_;
}

float Book::meanScore() const
{
    return meanScore_;
}

const QString& Book::description() const
{
    return description_;
}

const QList<Genre>& Book::genres() const
{
    return genres_;
}

const QString& Book::language() const
{
    return language_;
}

qint16 Book::year() const
{
    return year_;
}

const QString& Book::author() const
{
    return author_;
}

float Book::price() const
{
    return price_;
}
