# Define variables
JAVA_VERSION := 17
MAVEN := mvn

# Define targets
.PHONY: all clean build test commit run stop teardown

# Set the default target
.DEFAULT_GOAL := all

all: build commit

clean:
	@echo "Cleaning the project..."
	$(MAVEN) clean

build:
	@echo "Building the project..."
	chmod +x ./mvnw
	./mvnw clean install -DskipTests

test: build
	@echo "Running tests..."
	./mvnw test

commit:
	@echo "Setting up pre-commit hook..."
	chmod +x .github/hooks/pre-commit

run: build
	@echo "Starting the application..."
	docker-compose up

stop:
	@echo "Stopping the application..."
	docker-compose down

teardown:
	@echo "Tearing down the Docker environment..."
	docker-compose down --rmi all --volumes --remove-orphans
