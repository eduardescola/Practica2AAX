package aar.websockets.websocket;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import jakarta.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import aar.websockets.model.Employee;

@ApplicationScoped
@ServerEndpoint("/actions")
public class WebSocketServer {
    
    private static SessionHandler sessionHandler = new SessionHandler();
    
    public WebSocketServer() {
        System.out.println("class loaded " + this.getClass());
    }
    
    @OnOpen
    public void onOpen(Session session) throws URISyntaxException, IOException, InterruptedException {
        sessionHandler.addSession(session);
        System.out.println("cliente suscrito, sesion activa");
    }

    @OnClose
    public void onClose(Session session) {   
        sessionHandler.removeSession(session);
        System.out.println("cliente cierra conexión, sesion eliminada");
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketServer.class.getName()).
                log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("add".equals(jsonMessage.getString("action"))) {
                Employee employee = new Employee();
                employee.setName(jsonMessage.getString("name"));
                employee.setPassword("password");
                sessionHandler.addEmployee(employee);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.removeEmployee(id);
            }
            /*
            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                sessionHandler.toggleEmployee(id);
            }*/
        } 
    }
    
    
    
}