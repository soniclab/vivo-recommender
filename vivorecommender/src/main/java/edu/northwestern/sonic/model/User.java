package edu.northwestern.sonic.model;

import java.net.URI;
import java.util.Set;
/*
 * author: Anup
 */

public class User {
	private String email;
	private String name;
	private URI uri;
	private String department;
	private URI departmentURI;
    private String moniker;
    private Set<String> majorField;
    private Set<URI> gradSchool;
	private boolean affiliation = true;
	private boolean friendOfFriend = true;
	private boolean citation = true;
	private boolean mostQualified = true;
	private boolean birdsOfFeather = true;
	private boolean followCrowd = true;
	private boolean exchange = true;
	private boolean mobilizing = true;
	private boolean feelingLucky = true;
	private double bofScore;
	
	public boolean isAffiliation() {
		return affiliation;
	}
	public void setAffiliation(boolean affiliation) {
		this.affiliation = affiliation;
	}
	public boolean isFriendOfFriend() {
		return friendOfFriend;
	}
	public void setFriendOfFriend(boolean friendOfFriend) {
		this.friendOfFriend = friendOfFriend;
	}
	public boolean isCitation() {
		return citation;
	}
	public void setCitation(boolean citation) {
		this.citation = citation;
	}
	public boolean isMostQualified() {
		return mostQualified;
	}
	public void setMostQualified(boolean mostQualified) {
		this.mostQualified = mostQualified;
	}
	public boolean isBirdsOfFeather() {
		return birdsOfFeather;
	}
	public void setBirdsOfFeather(boolean birdsOfFeather) {
		this.birdsOfFeather = birdsOfFeather;
	}
	public boolean isFollowCrowd() {
		return followCrowd;
	}
	public void setFollowCrowd(boolean followCrowd) {
		this.followCrowd = followCrowd;
	}
	public boolean isExchange() {
		return exchange;
	}
	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}
	public boolean isMobilizing() {
		return mobilizing;
	}
	public void setMobilizing(boolean mobilizing) {
		this.mobilizing = mobilizing;
	}
	public boolean isFeelingLucky() {
		return feelingLucky;
	}
	public void setFeelingLucky(boolean feelingLucky) {
		this.feelingLucky = feelingLucky;
	}
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
	public URI getDepartmentURI(){
		return departmentURI;
	}
	public void setDepartmentURI(URI departmentURI){
		this.departmentURI = departmentURI;
	}
	public String getMoniker() {
		return moniker;
	}
	public void setMoniker(String moniker) {
		this.moniker = moniker;
	}
	public Set<String> getMajorField() {
		return majorField;
	}
	public void setMajorField(Set<String> majorField) {
		this.majorField = majorField;
	}
	public Set<URI> getGradSchool() {
		return gradSchool;
	}
	public void setGradSchool(Set<URI> gradSchool) {
		this.gradSchool = gradSchool;
	}
	public double getBofScore() {
		return bofScore;
	}
	public void setBofScore(double bofScore) {
		this.bofScore = bofScore;
	}
	
	
}
