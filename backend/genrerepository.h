#ifndef GENREREPOSITORY_H
#define GENREREPOSITORY_H

#include "genre.h"
class GenreRepository
{
public:
    GenreRepository();

    static QList<Genre> getAllGenres();
};

#endif // GENREREPOSITORY_H
