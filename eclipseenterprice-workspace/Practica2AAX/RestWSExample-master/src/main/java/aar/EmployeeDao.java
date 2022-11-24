package aar;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDao {
	
	Logger log = Logger.getLogger(EmployeeDao.class.getName());

    final DatabaseService d = new DatabaseService();
	
    public int addEmployee(String name, String password) {
        try {
            Employee employee = new Employee(name, password);
            d.insertEmployee(employee);
        } catch (Exception ex) {	
           log.log(Level.SEVERE, null, ex);
        }		
        return 1;
    }	
    
    public Employee getEmployee(Integer id) {
        return d.readEmployee(id);
    }
    
    public boolean deleteEmployee(Integer id) {
    	return d.deleteEmployee(id);
    }
	
    public List<Employee> searchEmployee(String key, String value) {
    	try {
    		return d.searchEmployee(key, value);
    	} catch (Exception ex) {
            log.log(Level.INFO, null, ex);
    	}
    	return null;
    }
    
    public List<Employee> getAllEmployees() {
    	try {
    		return d.findAllEmployees();
    	} catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
    	}
    	return null;
    }
}