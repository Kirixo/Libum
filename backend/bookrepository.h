#ifndef BOOKREPOSITORY_H
#define BOOKREPOSITORY_H

#include "book.h"
#include "genre.h"
#include <optional>

class BookRepository
{
public:
    BookRepository();
    static std::optional<Book> fetchBookById(int bookId);
    static QList<Book> fetchBooks(int limit, int page);
    static int getBooksCount();
    static QList<Genre> fetchGenresForBook(int bookId);
    static QList<Book> fetchFilteredBooks(int limit, int page, std::optional<qint64> minPrice, std::optional<qint64> maxPrice, std::optional<QString> searchQuery,
                                          std::optional<qint64> userIdForFavorites);

    static std::optional<QString> fetchFilePathForReader(int bookId);
    // static std::optional<QString> fetchFilePathForDownload(int bookId);


    static void uploadBookFile();
};

#endif // BOOKREPOSITORY_H
