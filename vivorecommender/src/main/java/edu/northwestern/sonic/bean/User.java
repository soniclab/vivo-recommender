package edu.northwestern.sonic.bean;
/*
 * author: Anup
 */

public class User {
	private String email;
	private String name;
	private String uri;
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
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
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
