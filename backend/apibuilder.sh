#!/bin/bash

if [ ! -f /build/Libum ]; then
  echo "Building the project..."
  cmake -B /build -S /app && cmake --build /build
fi

echo "Running the application..."
/build/Libum
