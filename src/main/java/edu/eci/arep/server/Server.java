package edu.eci.arep.server;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

import edu.eci.arep.util.FestividadApi;
import edu.eci.arep.util.MimeTypes;
import edu.eci.arep.util.Request;
import edu.eci.arep.util.Response;
import edu.eci.arep.util.Service;
import edu.eci.arep.util.MimeTypes;

import java.io.*;

public class Server {

    private static Map<String, Service> getRoutes = new HashMap<>();
    private static final int port = 35000;
    private static String rootFiles = "www";

    // PONER LAS RUTAS
    public static void setRoutes() {
        get("/hello", (req, res) -> {
            String name = req.getValues("name");
            if (name == null)
                name = "Ser mortal";
            return "Hola " + name + "!";
        });

        get("/pi", (req, resp) -> {
            return String.valueOf(Math.PI); 
        });

        get("/bye", (req, res) -> "Bye bye!");

        get("/api/festividad", (req, res) -> {
            String fest = req.getValues("name"); // ejemplo: /api/festividad?name=navidad
            if (fest == null) {
                return "{\"error\": \"Debes pasar un parámetro name (ejemplo: /api/festividad?name=navidad)\"}";
            }

            return FestividadApi.getFestividad(fest);
        });
    }

    // OBTENER EL LAMBDA
    public static void get(String path, Service service) {
        getRoutes.put(path, service);
    }

    // ABRIR PUERTOS PARA ESCUCHAR
    public static void listen() throws IOException, URISyntaxException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor escuchando en puerto " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    public static void staticfiles(String path) {
        rootFiles = path;
    }


    // MANEJO DE PETICIONES
private static void handleRequest(Socket clientSocket) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

    try {
        String inputLine = in.readLine();
        if (inputLine == null || inputLine.isEmpty()) {
            clientSocket.close();
            return;
        }

        String[] requestParts = inputLine.split(" ");
        String method = requestParts[0];
        String rawPath = requestParts[1];

        // Consumir headers
        while ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
            // ignorados
        }

        URI uri = URI.create(rawPath);
        Request req = new Request(uri);
        Response res = new Response(out);

        String route = req.getPath();

        if (getRoutes.containsKey(route)) {
            String body = getRoutes.get(route).executeService(req, res);
            if (body != null) {
                res.send(body);
            }
        } else {
            // 👇 Si la ruta es "/", devolver index.html
            if (route.equals("/")) {
                route = "/index.html";
            }
            serveStaticFile(route, out, clientSocket);
        }

    } catch (Exception e) {
        e.printStackTrace();
        String errorMessage = "<h1>500 Internal Server Error</h1>";
        out.println("HTTP/1.1 500 Internal Server Error");
        out.println("Content-Type: text/html; charset=UTF-8");
        out.println("Content-Length: " + errorMessage.length());
        out.println();
        out.println(errorMessage);
    } finally {
        out.close();
        in.close();
        clientSocket.close();
    }
}

// MANEJO DE ARCHIVOS ESTÁTICOS
private static void serveStaticFile(String path, PrintWriter out, Socket clientSocket) throws IOException {
    // Buscar archivo en resources/www dentro del classpath
    InputStream resourceStream = Server.class.getClassLoader().getResourceAsStream(rootFiles + path);

    if (resourceStream != null) {
        // Determinar MIME
        String mimeType = MimeTypes.guessContentType(new File(path));
        byte[] fileBytes = resourceStream.readAllBytes();

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: " + mimeType);
        out.println("Content-Length: " + fileBytes.length);
        out.println();

        OutputStream rawOut = clientSocket.getOutputStream();
        rawOut.write(fileBytes);
        rawOut.flush();
        resourceStream.close();
    } else {
        String errorMessage = "<h1>404 Not Found</h1>";
        out.println("HTTP/1.1 404 Not Found");
        out.println("Content-Type: text/html; charset=UTF-8");
        out.println("Content-Length: " + errorMessage.length());
        out.println();
        out.println(errorMessage);
    }
}



    public static void main(String[] args) throws IOException, URISyntaxException {
        setRoutes();
        listen();
    }
}
