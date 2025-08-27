
# Web Framework for REST Services and Static Files

This project is a **lightweight web framework in Java** that allows developers to easily:
- Define **REST services** using lambda functions.
- Extract **query parameters** from requests.
- Serve **static files** from a configurable folder.
- Organize applications with a clean Maven structure.

---

## Getting Started

These instructions will help you get a copy of the project running on your local machine for development and testing purposes.

### Prerequisites
Make sure you have the following tools installed:
* Java 17 or higher
* Maven 3.6 or higher
* Git

### Installing
**1. Clone the repository:**
```bash
git clone https://github.com/AlejandroPrieto82/AREP-Lab2-MicroframeworksWEB.git
cd AREP-Lab2-MicroframeworksWEB
````

**2. Build the project using Maven:**
```bash
mvn clean package
```

**3. Run the server:**
```bash
java -cp target/AREP-Lab2-MicroframeworksWEB-1.0-SNAPSHOT.jar edu.eci.arep.server.Server
```

**4. Access the REST endpoints:**
* [http://localhost:35000/hello?name=Alejandro](http://localhost:35000/hello?name=Alejandro)
* [http://localhost:35000/pi](http://localhost:35000/pi)
* [http://localhost:35000/bye](http://localhost:35000/bye)
* [http://localhost:35000/api/festividad?name=christmas](http://localhost:35000/api/festividad?name=christmas)

**5. Access static files (from the `www` folder):**

* [http://localhost:35000/index.html](http://localhost:35000/index.html)
* [http://localhost:35000/img/elefante.jpeg](http://localhost:35000/img/elefante.jpeg)

---

## Design and Architecture

The framework follows a simple **request–response server architecture**:

* `Server` manages routes, sockets, and static file serving.
* `Service` is a functional interface used for lambda-based routes.
* `Request` and `Response` provide easy access to query parameters and response handling.
* `MimeTypes` ensures correct content-type headers.
* `FestividadApi` provides example REST functionality.

### Architecture Diagram

```plaintext
               ┌──────────────────────┐
               │      Client          │
               │ (Browser, curl, etc.)│
               └──────────┬───────────┘
                          │ HTTP Request
                          ▼
               ┌─────────────────────┐
               │      Server.java     │
               │ - Socket listener    │
               │ - Route registry     │
               │ - Static files       │
               └──────────┬───────────┘
                  ┌───────┴────────┐
                  ▼                ▼
     ┌─────────────────────┐   ┌─────────────────────┐
     │      Service        │   │  Static Files (www) │
     │  Lambda REST APIs   │   │ index.html, img/... │
     └─────────────────────┘   └─────────────────────┘
                  │
                  ▼
     ┌─────────────────────┐
     │      Utilities      │
     │ - Request           │
     │ - Response          │
     │ - MimeTypes         │
     │ - FestividadApi     │
     └─────────────────────┘
```

---

## Usage Example

### Defining REST routes

```java
Server.get("/hello", (req, res) -> "Hello " + req.getValues("name") + "!");
Server.get("/pi", (req, res) -> String.valueOf(Math.PI));
```

### Serving static files

```java
Server.staticfiles("www");
```

---

## Running the Tests

Currently, there are no automated tests (JUnit).
**Manual tests** can be performed by accessing endpoints and verifying responses.

### End-to-end tests

* `/hello?name=Alejandro` → returns `Hola Alejandro!`
* `/pi` → returns π value
* `/bye` → returns `Bye bye!`
* `/api/festividad?name=christmas` → returns holiday info in JSON
* `/index.html` → returns static file content
* Non-existing file → returns `404 Not Found`

---

## Coding Style & Project Quality

* Follows **Java naming conventions**.
* Organized into Maven packages (`server`, `util`).
* **.gitignore** properly configured for Java, Maven, IDE, Node, OS files.
* Project respects **Maven standard structure**.
* No unnecessary files or folders included.

---

## Deployment

To deploy in production:

1. Compile with Maven.
2. Run `edu.eci.arep.server.Server` on a machine with Java installed.
3. (Optional) Configure a reverse proxy such as **Nginx** if exposed to the internet.

---

## Built With

* **Java** - Main programming language
* **Maven** - Dependency management and build tool

---

## Author

* **Alejandro Prieto** - [GitHub Profile](https://github.com/AlejandroPrieto82)
