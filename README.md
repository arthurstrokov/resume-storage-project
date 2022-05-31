# resume-storage-project-test

# Resume project

Spring Boot REST service for storing resumes.

# About

Service designed to work with employees resumes.

# How to:

## Create

    POST http://localhost:8080/employees
    RESPONSE: HTTP 201 (CREATED)

    Example:
    {
        "firstName": "Arthur",
        "lastName": "Strokov",
        "phone": "375-291555376",
        "age": null,
        "birthDate": "1985-11-01",
        "gender": "MALE",
        "email": "arthurstrokov@gmail.com"
    }

## Read

    GET http://localhost:8080/employees
    GET http://localhost:8080/employees/{id}
    GET http://localhost:8080/employees/all
    GET http://localhost:8080/employees/pageable?page=0&size=0&sort=fieldName
    GET http://localhost:8080/employees/filtered?search=fieldName:value
    GET http://localhost:8080/employees/?page=0&size=0&sort=fieldValue&search=fieldName:value
    GET http://localhost:8080/employees/?search=fieldName:value&page=0&size=0&sort=fieldName
    RESPONSE: HTTP 200 (OK)

## Update

    PUT http://localhost:8080/employees/{id}
    RESPONSE: HTTP 200 (OK)

    Example:
    {
    "id": 1
    "firstName": "Arthur",
    "lastName": "Strokov",
    "phone": "375-291555376",
    "age": null,
    "birthDate": "1985-11-01",
    "gender": "MALE",
    "email": "arthurstrokov@gmail.com"
    }

## Delete

    DELETE http://localhost:8080/employees/{id}
    RESPONSE: HTTP 204 (NO_CONTENT)


## Docker compose

    ./gradlew clean build or ./gradlew clean build -x test for work in docker
    
    docker-compose build

    docker-compose up -d
    docker-compose down --volumes

    docker-compose up --build

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4c635b307ce54f6e950731a6a4bc7ca7)](https://www.codacy.com/gh/arthurstrokov/resume-storage-project/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=arthurstrokov/resume-storage-project&amp;utm_campaign=Badge_Grade)
[![DeepSource](https://deepsource.io/gh/arthurstrokov/resume-storage-project.svg/?label=active+issues&show_trend=true&token=RdrPe0NiKOd9ub3Tg2RPCof3)](https://deepsource.io/gh/arthurstrokov/resume-storage-project/?ref=repository-badge)
