FROM ubuntu:22.04

# Install necessary packages
RUN apt-get update && apt-get install -y \
    build-essential \
    cmake \
    ninja-build \
    git \
    python3-pip \
    libglib2.0-dev\
    libssl-dev \
    libpq-dev\
    vim \
    && rm -rf /var/lib/apt/lists/*

# Install aqtinstall via pip
RUN pip3 install aqtinstall

# Install Qt using aqtinstall
RUN aqt install-qt linux desktop 6.7.0 linux_gcc_64 --modules \
    qthttpserver \
    qtwebsockets

# Set the CMAKE_PREFIX_PATH environment variable to point to the installed Qt
ENV CMAKE_PREFIX_PATH="/6.7.0/gcc_64/lib/cmake/Qt6:$CMAKE_PREFIX_PATH"
