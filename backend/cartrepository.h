#ifndef CARTREPOSITORY_H
#define CARTREPOSITORY_H

#include <optional>
#include <qtypes.h>
class CartRepository
{
public:
    CartRepository();

    static bool addBook(quint64 userId, quint64 bookid);

};

#endif // CARTREPOSITORY_H
