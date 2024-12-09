#include "logger.h"
#include <iostream>

Logger::Logger()
    : consoleOutputEnabled_(true), currentLogLevel_(LogLevel::Info) {}

Logger::~Logger()
{
    if(logFile_.isOpen()) {
        logFile_.close();
    }
}

Logger &Logger::instance()
{
    static Logger loggerInstance;
    return loggerInstance;
}

void Logger::log(const QString &message, LogLevel level)
{
    QString formattedMessage = formatMessage(message, level);

    QMutexLocker locker(&mutex_);

    if(logFile_.isOpen()) {
        logStream_ << formattedMessage << Qt::endl;
    }

    if (consoleOutputEnabled_) {
        std::cout << formattedMessage.toStdString() << std::endl;
    }
}

void Logger::setLogFile(const QString &filePath)
{
    QMutexLocker locker(&mutex_);

    if(logFile_.isOpen()) {
        logFile_.close();
    }

    logFile_.setFileName(filePath);

    if (logFile_.open(QIODevice::WriteOnly | QIODevice::Append)) {
        logStream_.setDevice(&logFile_);
    } else {
        qWarning() << "Failed to open log file:" << filePath;
    }
}

void Logger::enableConsoleOutput(bool enabled)
{
    consoleOutputEnabled_ = enabled;
}

void Logger::setLogLevel(LogLevel level)
{
    currentLogLevel_ = level;
}

QString Logger::formatMessage(const QString &message, LogLevel level)
{
    QString levelStr;
    switch (level) {
        case LogLevel::Info: levelStr = "INFO"; break;
        case LogLevel::Warning: levelStr = "WARNING"; break;
        case LogLevel::Error: levelStr = "ERROR"; break;
        case LogLevel::Debug: levelStr = "DEBUG"; break;
    }

    return QString("[%1] [%2] %3")
        .arg(QDateTime::currentDateTime()
        .toString("dd-MM-yyyy HH:mm:ss"))
        .arg(levelStr).arg(message);
}
