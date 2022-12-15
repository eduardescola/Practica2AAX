package aar.websockets.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ClienteHttp {
	
	private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
	
	public HttpResponse<String> httpGetEmployees() throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://localhost:8080/RestWSExample/rest/employees"))
				  .GET()
				  .build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
	
	public HttpResponse<String> httpGetEmployeesById(int id) throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://localhost:8080/RestWSExample/rest/employees/"+id))
				  .GET()
				  .build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
	
	public HttpResponse<String> httpGetChats() throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://localhost:8080/RestWSExample/rest/chats"))
				  .GET()
				  .build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
	
	public HttpResponse<String> httpGetChatsByEmployee(int id) throws URISyntaxException, IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("http://localhost:8080/RestWSExample/rest/chats/employees/"+id))
				  .GET()
				  .build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
	/*
	public HttpResponse<String> httpSamePassword(int id, String password) throws IOException, InterruptedException, URISyntaxException{
		String idString = id+"";
		Map<String, String> parameters = new HashMap<>();
		parameters.put("id", idString);
		parameters.put("password", password);

		String form = parameters.entrySet()
			    .stream()
			    .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
			    .collect(Collectors.joining("&"));

		HttpRequest request = HttpRequest.newBuilder().uri(new URI("http://localhost:8080/RestWSExample/rest/chats/employees/login"))
				.headers("Content-Type", "application/x-www-form-urlencoded")
				.POST(HttpRequest.BodyPublishers.ofString(form))
				.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response);
		return response;
	}
	*/
}
