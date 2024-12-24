#include "bookstatus.h"


QJsonObject BookStatus::toJson() const
{
    QJsonObject json;
    json["id"] = QJsonValue::fromVariant(id);
    json["name"] = name;
    return json;
}
