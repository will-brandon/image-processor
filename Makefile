#!/bin/zsh
#
# GNU Makefile build script for the Image Processor
# Builds all source files for the application (ignores test source)
#
# By: Will Brandon
#
# Note: All paths are relative, be sure to cd into the project directory before starting this
# Makefile.

JDK_VERSION := 11
MAIN = src/cs3500/imageprocessor/ImageProcessor.java
SRC := src
OUTPUT := out/production/ImageProcessor

all: build

build:
	@javac $(MAIN) --release $(JDK_VERSION) -cp $(SRC) -d $(OUTPUT)
	@echo "\033[0m\n >> \033[1;33mCompiled the Image Processor Java source"
	@echo "\033[0m >> \033[36mStart the application using ./start\033[0m\n"

clean:
	@if [ -d $(OUTPUT) ]; then \
		rm -r $(OUTPUT); \
		echo "\033[0m\n >> \033[1;33mRemoved binary directory (\033[0;1m$(OUTPUT)\033[33m)\033[0m\n"; \
	else \
		echo "\033[0m\n >> \033[1;33mBinary directory (\033[0;1m$(OUTPUT)\033[33m) already nonexistent\033[0m\n"; \
	fi
