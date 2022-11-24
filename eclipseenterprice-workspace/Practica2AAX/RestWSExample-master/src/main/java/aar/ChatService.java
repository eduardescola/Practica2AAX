package aar;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/chats")
public class ChatService {

	ChatDao chatDao = new ChatDao();

	Logger log = Logger.getLogger(ChatService.class.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chat> getChats() {
		return chatDao.getAllChats();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Chat getChat(@PathParam("id") Integer id) {
		return chatDao.getChat(id);
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addChat(@FormParam("name") String name, @FormParam("employee1") int employee1, @FormParam("employee2") int employee2) {
		try {
			chatDao.addChat(name, employee1, employee2);
			log.log(Level.INFO, "Inserted chat " + name);

			return Response.status(200)
					.entity("addUser -> name: " + name + ", employee1: " + employee1 + ", employee2: " + employee2).build();
		} catch (Exception e) {
			return Response.status(400).build();
		}
	}

	@GET
	@Path("/employees/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Chat[] getChatsByEmployee(@PathParam("id") Integer id) {
		Chat[] aux=new Chat[chatDao.getAllChats().size()];
		int i=0;
		for (Chat c: chatDao.getAllChats()) {
			if (id.equals(c.getEmployee1()) || id.equals(c.getEmployee2())) {
				aux[i]=c;
				i++;	
			}
		}
		Chat[] chats=new Chat[i];
		for (int j=0; j<i;j++)
			chats[j]=aux[j];
		return chats;
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response removeChat(@FormParam("id") Integer id) {
		try {
			boolean deletedOk = chatDao.deleteChat(id);

			if (deletedOk == true)
				log.log(Level.INFO, "deleted chat " + id + " correctly ");

			return Response.status(200).entity("Deleted chat with id: " + id + " correctly ").build();
		} catch (Exception e) {
			return Response.status(400).build();
		}
	}

}