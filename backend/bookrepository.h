#ifndef BOOKREPOSITORY_H
#define BOOKREPOSITORY_H

#include "book.h"
#include <optional>

class BookRepository
{
public:
    BookRepository();
    std::optional<Book> fetchBookById(int id);
    QList<Book> fetchBooks(int limit, int page);
    int getBooksCount();

private:
    QList<QString> fetchGenresForBook(int bookId);
};

#endif // BOOKREPOSITORY_H
