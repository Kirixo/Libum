#ifndef BOOKSTATUSREPOSITORY_H
#define BOOKSTATUSREPOSITORY_H

#include "bookstatus.h"
#include "book.h"
#include <QList>
#include <optional>

class BookStatusRepository
{
public:
    static std::optional<BookStatus> getStatusForBook(int userId, int bookId);
    static QList<BookStatus> getStatusesList();
    static QList<Book> getBooksWithStatus(int userId, int statusId, int limit, int offset);
    static bool setStatusForBook(int userId, int bookId, int statusId);
    static bool removeStatusForBook(int userId, int bookId);

private:
    BookStatusRepository() = delete; // Prevent instantiation
};

#endif // BOOKSTATUSREPOSITORY_H
