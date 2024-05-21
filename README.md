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
	<a href="https://spring.io/projects/spring-boot"> <img src="https://img.shields.io/badge/Spring boot-6DB33F.svg?style=flat&logo=springboot&logoColor=green" alt="Spring Boot"> </a>
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

##  Modules

<details closed><summary>backend</summary>

| File                                                                                        | Summary                                        |
| ---                                                                                         | ---                                            |
| [Dockerfile](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/Dockerfile) | HTTP error 401 for prompt `backend/Dockerfile` |
| [mvnw.cmd](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/mvnw.cmd)     | HTTP error 401 for prompt `backend/mvnw.cmd`   |
| [pom.xml](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/pom.xml)       | HTTP error 401 for prompt `backend/pom.xml`    |
| [mvnw](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/mvnw)             | HTTP error 401 for prompt `backend/mvnw`       |

</details>

<details closed><summary>backend.src.main.resources</summary>

| File                                                                                                           | Summary                                                           |
| ---                                                                                                            | ---                                                               |
| [import.sql](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/resources/import.sql) | HTTP error 401 for prompt `backend/src/main/resources/import.sql` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch</summary>

| File                                                                                                                                                              | Summary                                                                                              |
| ---                                                                                                                                                               | ---                                                                                                  |
| [SolarWatchApplication.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/SolarWatchApplication.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/SolarWatchApplication.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.configuration</summary>

| File                                                                                                                                                                              | Summary                                                                                                             |
| ---                                                                                                                                                                               | ---                                                                                                                 |
| [WebClientConfiguration.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/configuration/WebClientConfiguration.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/configuration/WebClientConfiguration.java` |
| [DataBaseInitializer.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/configuration/DataBaseInitializer.java)       | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/configuration/DataBaseInitializer.java`    |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.util</summary>

| File                                                                                                                                       | Summary                                                                                     |
| ---                                                                                                                                        | ---                                                                                         |
| [Utility.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/util/Utility.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/util/Utility.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.repository</summary>

| File                                                                                                                                                                             | Summary                                                                                                           |
| ---                                                                                                                                                                              | ---                                                                                                               |
| [RoleRepository.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/repository/RoleRepository.java)                   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/repository/RoleRepository.java`          |
| [SunriseSunsetRepository.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/repository/SunriseSunsetRepository.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/repository/SunriseSunsetRepository.java` |
| [UserRepository.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/repository/UserRepository.java)                   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/repository/UserRepository.java`          |
| [CityRepository.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/repository/CityRepository.java)                   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/repository/CityRepository.java`          |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.controller</summary>

| File                                                                                                                                                             | Summary                                                                                                   |
| ---                                                                                                                                                              | ---                                                                                                       |
| [AdminController.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/controller/AdminController.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/controller/AdminController.java` |
| [SunController.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/controller/SunController.java)     | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/controller/SunController.java`   |
| [UserController.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/controller/UserController.java)   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/controller/UserController.java`  |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.dto</summary>

| File                                                                                                                                                        | Summary                                                                                             |
| ---                                                                                                                                                         | ---                                                                                                 |
| [SunriseSunsetDTO.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/dto/SunriseSunsetDTO.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/dto/SunriseSunsetDTO.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.model</summary>

| File                                                                                                                                                              | Summary                                                                                                 |
| ---                                                                                                                                                               | ---                                                                                                     |
| [Coordinates.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/Coordinates.java)               | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/Coordinates.java`        |
| [WeatherReport.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/WeatherReport.java)           | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/WeatherReport.java`      |
| [SolarResultDetails.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/SolarResultDetails.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/SolarResultDetails.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.model.dto</summary>

| File                                                                                                                                                                        | Summary                                                                                                        |
| ---                                                                                                                                                                         | ---                                                                                                            |
| [CurrentWeatherInfoDTO.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/dto/CurrentWeatherInfoDTO.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/dto/CurrentWeatherInfoDTO.java` |
| [SunriseSunsetDTO.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/dto/SunriseSunsetDTO.java)           | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/dto/SunriseSunsetDTO.java`      |
| [UsernamePasswordDTO.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/dto/UsernamePasswordDTO.java)     | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/dto/UsernamePasswordDTO.java`   |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.model.dto.integration_model</summary>

| File                                                                                                                                                                                    | Summary                                                                                                                       |
| ---                                                                                                                                                                                     | ---                                                                                                                           |
| [SunInfoResponseDTO.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/dto/integration_model/SunInfoResponseDTO.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/dto/integration_model/SunInfoResponseDTO.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.model.api_response</summary>

| File                                                                                                                                                                           | Summary                                                                                                              |
| ---                                                                                                                                                                            | ---                                                                                                                  |
| [Coordinates.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/api_response/Coordinates.java)               | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/api_response/Coordinates.java`        |
| [WeatherReport.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/api_response/WeatherReport.java)           | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/api_response/WeatherReport.java`      |
| [SolarResultDetails.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/api_response/SolarResultDetails.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/api_response/SolarResultDetails.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.model.api_response.current_weather_response</summary>

| File                                                                                                                                                                                                            | Summary                                                                                                                                           |
| ---                                                                                                                                                                                                             | ---                                                                                                                                               |
| [WindInfo.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/WindInfo.java)                             | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/WindInfo.java`               |
| [MainWeatherInfo.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/MainWeatherInfo.java)               | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/MainWeatherInfo.java`        |
| [CurrentWeatherInfo.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/CurrentWeatherInfo.java)         | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/CurrentWeatherInfo.java`     |
| [CurrentWeatherResponse.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/CurrentWeatherResponse.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/api_response/current_weather_response/CurrentWeatherResponse.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.model.entity</summary>

| File                                                                                                                                                           | Summary                                                                                                   |
| ---                                                                                                                                                            | ---                                                                                                       |
| [SunriseSunset.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/entity/SunriseSunset.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/entity/SunriseSunset.java` |
| [UserEntity.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/entity/UserEntity.java)       | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/entity/UserEntity.java`    |
| [Role.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/entity/Role.java)                   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/entity/Role.java`          |
| [City.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/entity/City.java)                   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/entity/City.java`          |
| [RoleEntity.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/model/entity/RoleEntity.java)       | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/model/entity/RoleEntity.java`    |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.service</summary>

| File                                                                                                                                                                      | Summary                                                                                                      |
| ---                                                                                                                                                                       | ---                                                                                                          |
| [OpenWeatherService.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/service/OpenWeatherService.java)       | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/service/OpenWeatherService.java`    |
| [CoordinateFetcher.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/service/CoordinateFetcher.java)         | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/service/CoordinateFetcher.java`     |
| [UserService.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/service/UserService.java)                     | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/service/UserService.java`           |
| [GeoCodeService.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/service/GeoCodeService.java)               | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/service/GeoCodeService.java`        |
| [CurrentWeatherFetcher.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/service/CurrentWeatherFetcher.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/service/CurrentWeatherFetcher.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.security</summary>

| File                                                                                                                                                               | Summary                                                                                                   |
| ---                                                                                                                                                                | ---                                                                                                       |
| [WebSecurityConfig.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/security/WebSecurityConfig.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/security/WebSecurityConfig.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.security.service</summary>

| File                                                                                                                                                                                 | Summary                                                                                                                |
| ---                                                                                                                                                                                  | ---                                                                                                                    |
| [UserDetailsServiceImpl.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/security/service/UserDetailsServiceImpl.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/security/service/UserDetailsServiceImpl.java` |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.security.jwt</summary>

| File                                                                                                                                                                   | Summary                                                                                                       |
| ---                                                                                                                                                                    | ---                                                                                                           |
| [AuthTokenFilter.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/security/jwt/AuthTokenFilter.java)     | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/security/jwt/AuthTokenFilter.java`   |
| [AuthEntryPointJwt.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/security/jwt/AuthEntryPointJwt.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/security/jwt/AuthEntryPointJwt.java` |
| [JwtUtils.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/security/jwt/JwtUtils.java)                   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/security/jwt/JwtUtils.java`          |

</details>

<details closed><summary>backend.src.main.java.com.codecool.solarwatch.exception</summary>

| File                                                                                                                                                                                            | Summary                                                                                                                  |
| ---                                                                                                                                                                                             | ---                                                                                                                      |
| [InvalidDateException.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/exception/InvalidDateException.java)                       | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/exception/InvalidDateException.java`            |
| [NotSupportedCityException.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/exception/NotSupportedCityException.java)             | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/exception/NotSupportedCityException.java`       |
| [SunriseSunsetNotFoundException.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/exception/SunriseSunsetNotFoundException.java)   | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/exception/SunriseSunsetNotFoundException.java`  |
| [WeatherForecastControllerAdvice.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/exception/WeatherForecastControllerAdvice.java) | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/exception/WeatherForecastControllerAdvice.java` |
| [InvalidCityException.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/main/java/com/codecool/solarwatch/exception/InvalidCityException.java)                       | HTTP error 401 for prompt `backend/src/main/java/com/codecool/solarwatch/exception/InvalidCityException.java`            |

</details>

<details closed><summary>backend.src.test.java.com.codecool.solarwatch</summary>

| File                                                                                                                                                                  | Summary                                                                                                |
| ---                                                                                                                                                                   | ---                                                                                                    |
| [MockMvcIT.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/test/java/com/codecool/solarwatch/MockMvcIT.java)                             | HTTP error 401 for prompt `backend/src/test/java/com/codecool/solarwatch/MockMvcIT.java`               |
| [SolarWatchApplicationIT.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/test/java/com/codecool/solarwatch/SolarWatchApplicationIT.java) | HTTP error 401 for prompt `backend/src/test/java/com/codecool/solarwatch/SolarWatchApplicationIT.java` |

</details>

<details closed><summary>backend.src.test.java.com.codecool.solarwatch.service</summary>

| File                                                                                                                                                                | Summary                                                                                                   |
| ---                                                                                                                                                                 | ---                                                                                                       |
| [GeoCodeServiceTest.java](https://github.com/martinbago22/solar-watch-cv/blob/master/backend/src/test/java/com/codecool/solarwatch/service/GeoCodeServiceTest.java) | HTTP error 401 for prompt `backend/src/test/java/com/codecool/solarwatch/service/GeoCodeServiceTest.java` |

</details>

<details closed><summary>frontend.src</summary>

| File                                                                                               | Summary                                              |
| ---                                                                                                | ---                                                  |
| [codewars.js](https://github.com/martinbago22/solar-watch-cv/blob/master/frontend/src/codewars.js) | HTTP error 401 for prompt `frontend/src/codewars.js` |

</details>

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
