#include "genrerepository.h"
#include <qsqldatabase.h>
#include "dbcontroller.h"

GenreRepository::GenreRepository() {}

QList<Genre> GenreRepository::getAllGenres()
{
    QSqlDatabase db = DBController::getDatabase();
    QList<Genre> genres;

    QString queryString = R"(
        SELECT id, genre_name
        FROM genres;
    )";

    QSqlQuery query(db);
    query.prepare(queryString);

    if (query.exec()) {
        while (query.next()) {
            Genre tmpGenre;
            tmpGenre.setId(query.value("id").toInt());
            tmpGenre.setName(query.value("genre_name").toString());
            genres.append(std::move(tmpGenre));
        }
    }

    return genres;
}
