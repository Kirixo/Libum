#ifndef BOOKREPOSITORY_H
#define BOOKREPOSITORY_H

#include "book.h"
#include "genre.h"
#include <optional>

class BookRepository
{
public:
    BookRepository();
    static std::optional<Book> fetchBookById(int id);
    static QList<Book> fetchBooks(int limit, int page);
    static int getBooksCount();

private:
    static QList<Genre> fetchGenresForBook(int bookId);
};

#endif // BOOKREPOSITORY_H
