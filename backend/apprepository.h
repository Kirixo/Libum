#ifndef APPREPOSITORY_H
#define APPREPOSITORY_H
#include "logger.h"
#include <qobject.h>

class AppRepository {
public:
    AppRepository() {};

    QString getAppFilePath() const;

    bool appFileExists() const;
};

#endif // APPREPOSITORY_H
