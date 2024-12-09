#ifndef LOGGER_H
#define LOGGER_H

#include <QString>
#include <QFile>
#include <QTextStream>
#include <QDateTime>
#include <QMutex>

class Logger
{
public:
    enum class LogLevel { Info, Warning, Error, Debug };

    static Logger& instance();

    void log(const QString& message, LogLevel level = LogLevel::Info);

    void setLogFile(const QString& filePath);
    void enableConsoleOutput(bool enabled);
    void setLogLevel(LogLevel level);

private:
    Logger();
    ~Logger();

    Logger(const Logger&) = delete;
    Logger& operator=(const Logger&) = delete;

    QString formatMessage(const QString& message, LogLevel level);

    QFile logFile_;
    QTextStream logStream_;
    QMutex mutex_;
    bool consoleOutputEnabled_;
    LogLevel currentLogLevel_;
};

#endif // LOGGER_H
