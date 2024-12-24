#include "apprepository.h"
#include <QSettings>

QString AppRepository::getAppFilePath() const {
    QSettings settings("config.ini", QSettings::IniFormat);
    return settings.value("Application/path", "./").toString() +
           settings.value("Application/filename", "/Libum.apk").toString();
}

bool AppRepository::appFileExists() const {
    QFile file(getAppFilePath());
    return file.exists();
}
