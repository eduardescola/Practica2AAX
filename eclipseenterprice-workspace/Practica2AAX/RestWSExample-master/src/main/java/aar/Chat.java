package aar;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Chat implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int employee1;

	@Column(nullable = false)
	private int employee2;

	public Chat() {
	}

	public Chat(String name, int employee1, int employee2) {
		this.name = name;
		this.employee1 = employee1;
		this.employee2 = employee2;
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

}
