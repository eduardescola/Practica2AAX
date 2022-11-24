package aar.websockets.websocket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.HashSet;
import java.util.Set;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;

import aar.websockets.model.Employee;

@ApplicationScoped
public class DeviceSessionHandler {
    private int deviceId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Employee> devices = new HashSet<>();
    
    public void addSession(Session session) {
        sessions.add(session);
        JsonProvider provider = JsonProvider.provider();
        for (Employee device : devices) {
            JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getPassword())
                .add("description", device.getDescription())
                .build();
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public void addDevice(Employee device) {
        device.setId(deviceId);
        devices.add(device);
        deviceId++;
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getPassword())
                .add("description", device.getDescription())
                .build();
        sendToAllConnectedSessions(addMessage);   
    }

    public void removeDevice(int id) {
        Employee device = getDeviceById(id);
        if (device != null) {
            devices.remove(device);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }   
    }

    public void toggleDevice(int id) {
        JsonProvider provider = JsonProvider.provider();
        Employee device = getDeviceById(id);
        if (device != null) {
            if ("On".equals(device.getPassword())) {
                device.setPassword("Off");
            } else {
                device.setPassword("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", device.getId())
                    .add("status", device.getPassword())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }      
    }

    private Employee getDeviceById(int id) {
        for (Employee device : devices) {
            if (device.getId() == id) {
                return device;
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
            Logger.getLogger(DeviceSessionHandler.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    
}