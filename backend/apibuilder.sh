#!/bin/bash

if [ ! -l /build/Libum ]; then
  echo "Building the project..."
  rm -rf /build
  cmake -B /build -S /app && cmake --build /build
fi

echo "Running the application..."
/build/Libum
