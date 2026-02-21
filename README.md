\# 🔗 Scalable URL Shortener



A production-style URL Shortener built with Spring Boot that converts long URLs into short, unique links and efficiently redirects users using a cache-first strategy.



Inspired by services like Bitly, this project focuses on system design principles, performance optimization, and scalability considerations.



---



\##  Features



\-  Generate unique short URLs

\-  Redirect to original URL (HTTP 302)

\-  Expiration support

\-  Click count tracking

\-  Cache-first lookup using Caffeine

\-  Base62 encoding for short code generation



---



\##  Architecture



Client  

&nbsp;  ↓  

Application (Spring Boot)  

&nbsp;  ↓  

Caffeine Cache  

&nbsp;  ↓  

Database (MySQL)



\### Redirect Flow



1\. User requests `/abc123`

2\. System checks Caffeine cache

3\. If found → return redirect immediately

4\. If not found → fetch from DB → store in cache → redirect

5\. Increment click counter (optimized strategy)



---



\###  Base62 Encoding

Instead of using random strings, this project uses:



\- Auto-increment ID from database

\- Encoded into Base62 (a-z, A-Z, 0-9)



Benefits:

\- Guaranteed uniqueness

\- No collision checks required

\- Short and compact URLs

\- Better database performance



---



\##  Tech Stack



\- Java 25

\- Spring Boot

\- Spring Data JPA

\- Caffeine Cache

\- MySQL 

\- Maven



---



\##  API Endpoints



\###  Create Short URL



POST `/generate`



Request Body:

```json

{

&nbsp; "originalUrl": "https://example.com/very/long/url",

&nbsp; "expirationDate": "2026-12-31T23:59:59"

}

```



Response:

```json

{

&nbsp; "shortUrl": "http://localhost:8080/AbX92k"

}

```



---



\###  Redirect



GET `/Go/{code}`



Response:

\- HTTP 302 Redirect to original URL



---



\##  Future Improvements



\- Replace Caffeine with Redis (distributed cache)

\- Implement rate limiting



---



\##  How to Run



```bash

git clone https://github.com/your-username/url-shortener.git

cd url-shortener

mvn clean install

mvn spring-boot:run

```



App runs on:

```

http://localhost:8080

```



---





Eslam  

Backend Developer | Java \& Spring Boot  

Focused on scalable backend systems and performance-driven architecture.



