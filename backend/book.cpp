#include "book.h"

Book::Book() {}

Book::~Book() {}

QJsonObject Book::toJson() const
{
    QJsonObject json;
    json["id"] = id_;
    json["title"] = title_;
    json["cover"] = cover_;
    json["mean_score"] = meanScore_;
    json["description"] = description_;

    QJsonArray genreArray;
    for (const auto& genre : genres_) {
        genreArray.append(genre);
    }
    json["genres"] = genreArray;

    json["language"] = language_;
    json["author"] = author_;
    json["price"] = price_;

    return json;
}
