package aar.websockets.model;

public class Chat {

    private int id;
    private String name;
    private int employee1;
    private int employee2;

    public Chat() {
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public int getEmployee1() {
        return employee1;
    }
    
    public int getEmployee2() {
        return employee2;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setEmployee1(int employee1) {
        this.employee1 = employee1;
    }
    public void setEmployee2(int employee2) {
        this.employee2 = employee2;
    }
}
