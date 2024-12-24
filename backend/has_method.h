#ifndef HAS_METHOD_H
#define HAS_METHOD_H

#include <type_traits>
#include <concepts>

template <typename T>
concept HasIsNull = requires(T obj) {
    { obj.isNull() } -> std::convertible_to<bool>;
};


#endif // HAS_METHOD_H
