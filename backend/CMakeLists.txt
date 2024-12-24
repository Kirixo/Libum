cmake_minimum_required(VERSION 3.16)

project(Libum LANGUAGES CXX)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(QT NAMES Qt6 Qt5 REQUIRED COMPONENTS Core Sql LinguistTools HttpServer)
find_package(Qt${QT_VERSION_MAJOR} REQUIRED COMPONENTS Core Sql LinguistTools HttpServer)

set(TS_FILES Libum_en_US.ts)

add_executable(Libum
  main.cpp
  ${TS_FILES}
  servercontroller.h servercontroller.cpp
  dbcontroller.h dbcontroller.cpp
  user.h user.cpp
  routefactory.h routefactory.cpp
  userhandler.h userhandler.cpp
  responsefactory.h responsefactory.cpp
  book.h book.cpp
  bookhandler.h bookhandler.cpp
  jsonable.h
  bookrepository.h bookrepository.cpp
  logger.h logger.cpp
  genre.h genre.cpp
  genrerepository.h genrerepository.cpp
  cartrepository.h cartrepository.cpp
  carthandler.h carthandler.cpp
  bookstatus.h bookstatus.cpp
  bookstatushandler.h bookstatushandler.cpp
  bookstatusrepository.h bookstatusrepository.cpp
  apprepository.h apprepository.cpp
  downloadapphandler.h downloadapphandler.cpp
  has_method.h
)
target_link_libraries(Libum Qt${QT_VERSION_MAJOR}::Core Qt${QT_VERSION_MAJOR}::Sql Qt${QT_VERSION_MAJOR}::HttpServer)

if(COMMAND qt_create_translation)
    qt_create_translation(QM_FILES ${CMAKE_SOURCE_DIR} ${TS_FILES})
else()
    qt5_create_translation(QM_FILES ${CMAKE_SOURCE_DIR} ${TS_FILES})
endif()

include(GNUInstallDirs)
install(TARGETS Libum
    LIBRARY DESTINATION ${CMAKE_INSTALL_LIBDIR}
    RUNTIME DESTINATION ${CMAKE_INSTALL_BINDIR}
)


# set(FIREBASE_CPP_SDK_DIR libs/firebase/)
# # Add Firebase libraries to the target using the function from the SDK.
# add_subdirectory(${FIREBASE_CPP_SDK_DIR} bin/ EXCLUDE_FROM_ALL)

# # The Firebase C++ library `firebase_app` is required,
# # and it must always be listed last.

# # Add the Firebase SDKs for the products you want to use in your app
# # For example, to use Firebase Authentication and Firebase Realtime Database
# set(firebase_libs firebase_auth firebase_database firebase_app)
# target_link_libraries(${target_name} "${firebase_libs}")
