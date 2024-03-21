# Define variables
JAVA_VERSION := 17
MAVEN := mvn

# Define targets
.PHONY: all clean build test commit

all: build commit

clean:
	@echo "Cleaning the project..."
	$(MAVEN) clean

#build: clean
#	@echo "Building the project..."
#	$(MAVEN) -B package

test: clean
	@echo "Running tests..."
	$(MAVEN) test

commit:
	@chmod +x .github/hooks/pre-commit

# Set the default target
.DEFAULT_GOAL := all

build:
	chmod +x ./mvnw
	./mvnw clean install -DskipTests

run: build
	docker-compose up

stop:
	docker-compose down

test: build
	./mvnw test
