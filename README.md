# Web Framework for REST Services and Static Files
This project is a lightweight web framework developed in Java that allows developers to create REST services and serve static files easily. It provides tools to define routes using lambda functions, extract query parameters, and organize application static files.

## Getting Started
These instructions will help you get a copy of the project running on your local machine for development and testing purposes.

### Prerequisites

Make sure you have the following tools installed:
* Java 17 or higher
* Maven 3.6 or higher
* Git

### Installing
**1. Clone the repository:**
git clone [https://github.com/AlejandroPrieto82/AREP-Lab2-MicroframeworksWEB)](https://github.com/AlejandroPrieto82/AREP-Lab2-MicroframeworksWEB)

```bash
cd your-repository
git clone https://github.com/AlejandroPrieto82/AREP-Lab2-MicroframeworksWEB.git
```
**2. Build the project using Maven:**
```bash
mvn clean package
```

**3. Run the server:**
```bash
java -cp target/your-repository-1.0-SNAPSHOT.jar edu.eci.arep.server.Server
```

**4. Access the REST endpoints:**
[http://localhost:35000/hello?name=Alejandro](http://localhost:35000/hello?name=Pedro)  
[http://localhost:35000/pi](http://localhost:35000/pi)  
[http://localhost:35000/bye](http://localhost:35000/bye)  
[http://localhost:35000/api/festividad?name=christmas](http://localhost:35000/api/festividad?name=christmas)  

**5. Access static files (if present in the `www` folder):**
[http://localhost:35000/index.html](http://localhost:35000/index.html)  
[http://localhost:35000/img/elefante.jpeg](http://localhost:35000/img/elefante.jpeg)


## Running the tests
Currently, there are no automated tests included. Manual tests can be performed by accessing the REST endpoints and verifying server responses and static file serving.

### End-to-end tests
* Accessing `/hello?name=Alejandro` should return `Hola Alejandro!`.
* Accessing `/pi` should return the value of π.
* Accessing `/bye` should return `Bye bye!`.
* Accessing `/api/festividad?name=christmas` should return information about the holiday in JSON format.
* Accessing an existing static file like `/index.html` should return the file content.
* Accessing a non-existing file should return a 404 error.

### Coding style tests
* The code follows Java naming conventions and is organized in packages according to functionality (`server`, `util`).

## Deployment
To deploy the server in a production environment, any machine with Java installed can run it. Just compile the project and run the `edu.eci.arep.server.Server` class.
It is recommended to configure a reverse proxy if exposing to the internet for security (e.g., using Nginx).

## Built With
* Java - Main programming language
* Maven - Dependency management and build tool

## Authors
* **Alejandro Prieto** - *Full Work* - [https://github.com/AlejandroPrieto82](https://github.com/AlejandroPrieto82)