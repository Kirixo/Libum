#ifndef CARTREPOSITORY_H
#define CARTREPOSITORY_H

#include "book.h"
#include <optional>
#include <qtypes.h>
class CartRepository
{
public:
    static bool addBook(quint64 userId, quint64 bookid);
    static bool remove(quint64 userId, quint64 bookid);
    static bool clear(quint64 userId);
    static QList<Book> fetchUsersBooks(quint64 userId);
private:
    CartRepository() = delete;
};

#endif // CARTREPOSITORY_H
