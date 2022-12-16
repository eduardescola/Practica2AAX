package aar.websockets.websocket;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;

import aar.websockets.model.Chat;
import aar.websockets.model.Employee;

@ApplicationScoped
public class SessionHandler {
	private int employeeId = 0;
	private int chatId = 0;
	private final Set<Session> sessions = new HashSet<>();


	public void addSession(Session session) throws URISyntaxException, IOException, InterruptedException {
		sessions.add(session);
		String employeesResponse = httpGetEmployees();

		JsonReader reader = Json.createReader(new StringReader(employeesResponse));
		JsonArray arrayEmployees = reader.readArray();

		JsonProvider provider = JsonProvider.provider();
		for (int i = 0; i < arrayEmployees.size(); i++) {
			JsonObject employee = arrayEmployees.getJsonObject(i);
			JsonObject addMessage = provider.createObjectBuilder()
					.add("action", "add")
					.add("id", employee.getInt("id"))
					.add("name", employee.getString("name"))
					.add("password", employee.getString("password"))
					.build();
			sendToSession(session, addMessage);
		}
	}

	public void removeSession(Session session) {
		sessions.remove(session);
	}

	public void addEmployee(Employee employee) {
		employee.setId(employeeId);
		employeeId++;
		JsonProvider provider = JsonProvider.provider();
		JsonObject addMessage = provider.createObjectBuilder().add("action", "add").add("id", employee.getId())
				.add("name", employee.getName()).add("password", employee.getPassword()).build();
		sendToAllConnectedSessions(addMessage);
	}

	public void addChat(Chat chat) {
		chat.setId(chatId);
		chatId++;
		JsonProvider provider = JsonProvider.provider();
		JsonObject addMessage = provider.createObjectBuilder().add("action", "add").add("id", chat.getId())
				.add("name", chat.getName()).add("employee1", chat.getEmployee1()).add("employee2", chat.getEmployee2())
				.build();
		sendToAllConnectedSessions(addMessage);
	}

	public String getPasswordById(int id) throws URISyntaxException, IOException, InterruptedException{
		String employeesResponse = httpGetEmployeesById(id);

		JsonReader reader = Json.createReader(new StringReader(employeesResponse));
		JsonObject employee = reader.readObject();
		
		return employee.getString("password");
	}
	
	public void getChatsByEmployee(Session session, int id) throws URISyntaxException, IOException, InterruptedException {
		String chatsResponse = httpGetChatsByEmployee(id);

		JsonReader reader = Json.createReader(new StringReader(chatsResponse));
		JsonArray arrayChats = reader.readArray();

		JsonProvider provider = JsonProvider.provider();
		for (int i = 0; i < arrayChats.size(); i++) {
			JsonObject chat = arrayChats.getJsonObject(i);
			JsonObject addMessage = provider.createObjectBuilder()
					.add("action", "addChats")
					.add("id", chat.getInt("id"))
					.add("name", chat.getString("name"))
					.add("employee1", chat.getInt("employee1"))
					.add("employee2", chat.getInt("employee2"))
					.build();
			sendToSession(session, addMessage);
		}
	}
	
	public void sendMessage(int id, int sender, String message, String name) throws URISyntaxException, IOException, InterruptedException {
		JsonProvider provider = JsonProvider.provider();
		JsonObject addMessage = provider.createObjectBuilder()
				.add("action", "showMessage")
				.add("id", id)
				.add("sender", sender)
				.add("message", message)
				.add("name", name)
				.build();
		sendToAllConnectedSessions(addMessage);
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
			Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String httpGetEmployees() throws URISyntaxException, IOException, InterruptedException {
		ClienteHttp get = new ClienteHttp();

		HttpResponse<String> message = get.httpGetEmployees();

		String employees = message.body();

		return employees;
	}
	
	public String httpGetEmployeesById(int id) throws URISyntaxException, IOException, InterruptedException {
		ClienteHttp get = new ClienteHttp();

		HttpResponse<String> message = get.httpGetEmployeesById(id);

		String employees = message.body();

		return employees;
	}

	public String httpGetChats() throws URISyntaxException, IOException, InterruptedException {
		ClienteHttp get = new ClienteHttp();

		HttpResponse<String> message = get.httpGetChats();

		String chats = message.body();

		return chats;
	}

	public String httpGetChatsByEmployee(int id) throws URISyntaxException, IOException, InterruptedException {
		ClienteHttp get = new ClienteHttp();

		HttpResponse<String> message = get.httpGetChatsByEmployee(id);

		String chats = message.body();

		return chats;
	}
}