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

@Path("/employees")
public class EmployeeService {

	EmployeeDao employeeDao = new EmployeeDao();

	Logger log = Logger.getLogger(EmployeeService.class.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getEmployees() {
		return employeeDao.getAllEmployees();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getEmployee(@PathParam("id") Integer id) {
		return employeeDao.getEmployee(id);
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response addEmployee(@FormParam("name") String name, @FormParam("password") String password) {
		try {
			employeeDao.addEmployee(name, password);
			log.log(Level.INFO, "Inserted employee " + name);

			return Response.status(200).entity("addEmployee -> name: " + name + ", password: " + password).build();
		} catch (Exception e) {
			return Response.status(400).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public Response removeEmployee(@FormParam("id") Integer id) {
		try {
			boolean deletedOk = employeeDao.deleteEmployee(id);

			if (deletedOk == true)
				log.log(Level.INFO, "deleted employee " + id + " correctly ");

			return Response.status(200).entity("Deleted employee with id: " + id + " correctly ").build();
		} catch (Exception e) {
			return Response.status(400).build();
		}
	}
}