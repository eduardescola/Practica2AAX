package aar.websockets.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;

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
	
}
