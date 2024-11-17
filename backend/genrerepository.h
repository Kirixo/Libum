#ifndef GENREREPOSITORY_H
#define GENREREPOSITORY_H

#include "genre.h"
class GenreRepository
{
public:
    GenreRepository();

    QList<Genre> getAllGenres();
};

#endif // GENREREPOSITORY_H
