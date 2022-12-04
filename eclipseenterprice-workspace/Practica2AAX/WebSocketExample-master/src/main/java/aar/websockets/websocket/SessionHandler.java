package aar.websockets.websocket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import aar.websockets.model.Chat;
import aar.websockets.model.Employee;

@ApplicationScoped
public class SessionHandler {
    private int employeeId = 0;
    private int chatId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Employee> employees = new HashSet<>();
    private final Set<Chat> chats = new HashSet<>();
    
    public void addSession(Session session) {
        sessions.add(session);
        JsonProvider provider = JsonProvider.provider();
        for (Employee employee : employees) {
            JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", employee.getId())
                .add("name", employee.getName())
                .add("password", employee.getPassword())
                .build();
            sendToSession(session, addMessage);
        }
        for (Chat chat : chats) {
            JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", chat.getId())
                .add("name", chat.getName())
                .add("employee1", chat.getEmployee1())
                .add("employee2", chat.getEmployee2())
                .build();
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public void addEmployee(Employee employee) {
        employee.setId(employeeId);
        employees.add(employee);
        employeeId++;
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", employee.getId())
                .add("name", employee.getName())
                .add("password", employee.getPassword())
                .build();
        sendToAllConnectedSessions(addMessage);   
    }
    
    public void addChat(Chat chat) {
    	chat.setId(chatId);
        chats.add(chat);
        chatId++;
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
        		.add("action", "add")
                .add("id", chat.getId())
                .add("name", chat.getName())
                .add("employee1", chat.getEmployee1())
                .add("employee2", chat.getEmployee2())
                .build();
        sendToAllConnectedSessions(addMessage);   
    }

    public void removeEmployee(int id) {
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            employees.remove(employee);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }   
    }
    
    public void removeChat(int id) {
    	Chat chat = getChatById(id);
        if (chat != null) {
        	chats.remove(chat);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }   
    }
    /*
    public void toggleEmployee(int id) {
        JsonProvider provider = JsonProvider.provider();
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            if ("On".equals(employee.getPassword())) {
                employee.setPassword("Off");
            } else {
                employee.setPassword("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", employee.getId())
                    .add("password", employee.getPassword())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }      
    }
    
    public void toggleChat(int id) {
        JsonProvider provider = JsonProvider.provider();
        Chat chat = getChatById(id);
        if (chat != null) {
            if ("On".equals(chat.getPassword())) {
            	chat.setPassword("Off");
            } else {
            	chat.setPassword("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", chat.getId())
                    .add("password", chat.getPassword())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }      
    }
    */
    private Employee getEmployeeById(int id) {
        for (Employee device : employees) {
            if (device.getId() == id) {
                return device;
            }
        }
        return null;
    }
    
    private Chat getChatById(int id) {
        for (Chat chat : chats) {
            if (chat.getId() == id) {
                return chat;
            }
        }
        return null;
    }

    private void sendToAllConnectedSessions(JsonObject message) {  
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SessionHandler.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
    public String httpGetEmployees() throws URISyntaxException, IOException, InterruptedException{ 
    	ClienteHttp get = new ClienteHttp();
    	
    	HttpResponse<String> message = get.httpGetEmployees();
    	
    	String employees=message.body();

		return employees;	
    }
    
    public String httpGetChats() throws URISyntaxException, IOException, InterruptedException{ 
    	ClienteHttp get = new ClienteHttp();
    	
    	HttpResponse<String> message = get.httpGetChats();
    	
    	String chats=message.body();

		return chats;	
    }
    
    
    
}