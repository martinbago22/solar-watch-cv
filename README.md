<p align="center">
  <img src="https://cdn-icons-png.flaticon.com/512/6295/6295417.png" width="100" />
</p>
<p align="center">
    <h1 align="center">SOLAR-WATCH-CV</h1>
</p>
<p align="center">
	<img src="https://img.shields.io/github/license/martinbago22/solar-watch-cv?style=flat&color=0080ff" alt="license">
	<img src="https://img.shields.io/github/last-commit/martinbago22/solar-watch-cv?style=flat&logo=git&logoColor=white&color=0080ff" alt="last-commit">
	<img src="https://img.shields.io/github/languages/top/martinbago22/solar-watch-cv?style=flat&color=0080ff" alt="repo-top-language">
	<img src="https://img.shields.io/github/languages/count/martinbago22/solar-watch-cv?style=flat&color=0080ff" alt="repo-language-count">
<p>
<p align="center">
		<em>Developed with the software and tools below.</em>
</p>
<p align="center">
	<a href="https://www.java.com/en/"> <img src="https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=flat&logo=JavaScript&logoColor=black" alt="JavaScript" /> </a>
	<a href="https://www.postgresql.org"> <img src="https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=flat&logo=PostgreSQL&logoColor=white" alt="PostgreSQL"> </a>
	<a href="https://www.docker.com"> <img src="https://img.shields.io/badge/Docker-2496ED.svg?style=flat&logo=Docker&logoColor=white" alt="Docker"> </a>
	<a href="https://www.java.com/en/"> <img src="https://img.shields.io/badge/Java-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white" alt="Java"> </a>
	<a href="https://maven.apache.org"> <img src="https://img.shields.io/badge/Apache Maven-C71A36.svg?style=flat&logo=apachemaven&logoColor=white" alt="Maven"> </a>
	<a href="https://spring.io/projects/spring-boot"> <img src="https://img.shields.io/badge/Spring Boot-6DB33F.svg?style=flat&logo=springboot&logoColor=green" alt="Spring Boot"> </a>
</p>
<hr>

##  Quick Links

> - [ Overview](#-overview)
> - [ Features](#-features)
> - [ Repository Structure](#-repository-structure)
> - [ Modules](#-modules)
> - [ Getting Started](#-getting-started)
>   - [ Installation](#-installation)
>   - [ Running solar-watch-cv](#-running-solar-watch-cv)
>   - [ Tests](#-tests)
> - [ Project Roadmap](#-project-roadmap)

---

##  Overview

This is an educational school project with the goal of practicing the Spring Boot framework for Java.  `overview`

---

##  Features

Getting sunrise and sunset times and current weather information for cities you search for with user authentication included. `features`

---

##  Repository Structure

```sh
└── solar-watch-cv/
    ├── README.md
    ├── backend
    │   ├── .gitignore
    │   ├── .mvn
    │   │   └── wrapper
    │   │       └── maven-wrapper.properties
    │   ├── Dockerfile
    │   ├── mvnw
    │   ├── mvnw.cmd
    │   ├── pom.xml
    │   └── src
    │       ├── main
    │       │   ├── java
    │       │   │   └── com
    │       │   └── resources
    │       │       ├── application.properties
    │       │       └── import.sql
    │       └── test
    │           └── java
    │               └── com
    └── frontend
        └── src
            └── codewars.js
```

---

##  Getting Started

***Requirements***

Ensure you have the following dependencies installed on your system:

* **Java**: `version 21`

###  Installation

1. Clone the solar-watch-cv repository:

```sh
git clone https://github.com/martinbago22/solar-watch-cv
```

2. Change to the project directory:

```sh
cd solar-watch-cv
```

3. Install the dependencies:

```sh
mvn clean install
```

###  Running solar-watch-cv

Use the following command to run solar-watch-cv:

```sh
java -jar target/myapp.jar
```

###  Tests

To execute tests, run:

```sh
mvn test
```

---
