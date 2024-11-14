#include "book.h"

Book::Book() : id_(-1)
{
}

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

void Book::fetch(quint64 id)
{
    QSqlDatabase db = DBController::getDatabase();

    QString queryString = R"(
        SELECT b.id, b.title, b.description, b.language_id, b.authors, b.year, b.price, l.lang_name
        FROM books AS b
        LEFT JOIN languages AS l ON b.language_id = l.id
        WHERE b.id = :id
    )";

    QSqlQuery query(db);
    query.prepare(queryString);
    query.bindValue(":id", id);

    if (query.exec() && query.next()) {
        id_ = query.value("id").toInt();
        title_ = query.value("title").toString();
        description_ = query.value("description").toString();
        language_ = query.value("lang_name").toString();
        author_ = query.value("authors").toString();
        year_ = query.value("year").toInt();
        price_ = query.value("price").toFloat();
    }

    QString getGenresQueryString = R"(
        SELECT g.genre_name
        FROM book_genre bg
        LEFT JOIN genres AS g ON g.id = bg.genre_id
        WHERE bg.book_id = :id
    )";

    QSqlQuery getGenresQuery(db);
    getGenresQuery.prepare(getGenresQueryString);
    getGenresQuery.bindValue(":id", id);

    if (getGenresQuery.exec()) {
        while(getGenresQuery.next()) {
            genres_.append(getGenresQuery.value("genre_name").toString());
        }
    }
}

bool Book::exists()
{
    return id_ != -1;
}
