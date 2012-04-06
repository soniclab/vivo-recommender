package edu.northwestern.sonic.model;

import java.net.URI;
/*
 * author: Anup
 */

public class User {
	private String email;
	private String name;
	private URI uri;
	private String department;
	private String departmentURI;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public String getDepartment(){
		return department;
	}
	public void setDepartment(String department){
		this.department = department;
	}
	public String getDepartmentURI(){
		return departmentURI;
	}
	public void setDepartmentURI(String departmentURI){
		this.departmentURI = departmentURI;
	}
}
