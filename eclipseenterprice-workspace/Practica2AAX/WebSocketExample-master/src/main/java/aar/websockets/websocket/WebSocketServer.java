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
        System.out.println("cliente cierra conexion, sesion eliminada");
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(WebSocketServer.class.getName()).
                log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws URISyntaxException, IOException, InterruptedException {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            if ("select".equals(jsonMessage.getString("action"))) {
                int id = (int) jsonMessage.getInt("id");
                String password = jsonMessage.getString("psw");
                if(sessionHandler.getPasswordById(id).equals(password))
                	sessionHandler.getChatsByEmployee(session, id);
            }
            
            if("send".equals(jsonMessage.getString("action"))) {
            	int id = (int) jsonMessage.getInt("id");
            	int sender = (int) jsonMessage.getInt("sender");
            	String msg = (String) jsonMessage.getString("msg");
            	String name = (String) jsonMessage.getString("name");
            	sessionHandler.sendMessage(id, sender, msg, name);
            	
            }
        } 
    } 
}