#ifndef CARTREPOSITORY_H
#define CARTREPOSITORY_H

#include "book.h"
#include <optional>
#include <qtypes.h>
class CartRepository
{
public:
    CartRepository();

    static bool addBook(quint64 userId, quint64 bookid);
    static QList<Book> fetchUsersBooks(quint64 userId);

};

#endif // CARTREPOSITORY_H
