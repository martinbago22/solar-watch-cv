Solar-Watch.
My educational driven school project at CodeCool.
My application runs on localhost:8080. It gets you the sunrise/sunset times for cities and dates you search for. There is also a feature to get the current weather for a given city. There is also User Authentication and Authorization implemented.
There is also a Database established so that once you look for a city my app first looks through the DB whether or not it's already included, if it isn't it fetches information from the free GeoCoding API.
For future goals, I'd like to implement a front end for my app, provide integration tests and cover more unit tests for my service classes and complete fully dockerizing my app.
Technologies used: Java, Maven, Spring-Boot, Hibernate, PostgreSQL, JPA, Spring Data. For Security I'm using Spring Security.
I've used these technologies to improve my understanding of them and improve my skills at them, as these are the technologies we've used at the latter part of my studies.
Challenges I've faced included: Getting a base understanding of how Spring Security works, learning how to use Docker (still work in progress), gaining a better understanding of ORM and Integration Tests.

First start with setting DB_URL, DB_USERNAME and POSTGRES_PW ENV variables with your own credentials. Also set your API_KEY for your own for https://openweathermap.org/api/geocoding-api .

