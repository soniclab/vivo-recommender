package edu.northwestern.sonic.model;

import java.net.URI;
import java.util.Set;

import thewebsemantic.Functional;
import thewebsemantic.Id;
import thewebsemantic.Namespace;
import thewebsemantic.RdfProperty;
/*
 * author: Anup
 */
@Namespace("http://sonic.northwestern.edu/")
public class User {
	private String email;
	private String name;
	private String password;
	private URI uri;
	private String[] keywords;
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
	private String imageUrl;
	
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/Keywords")
	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/Password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/Affiliation")
	public boolean isAffiliation() {
		return affiliation;
	}
	public void setAffiliation(boolean affiliation) {
		this.affiliation = affiliation;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/FriendOfAFriend")
	public boolean isFriendOfFriend() {
		return friendOfFriend;
	}
	public void setFriendOfFriend(boolean friendOfFriend) {
		this.friendOfFriend = friendOfFriend;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/Citation")
	public boolean isCitation() {
		return citation;
	}
	public void setCitation(boolean citation) {
		this.citation = citation;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/MostQualified")
	public boolean isMostQualified() {
		return mostQualified;
	}
	public void setMostQualified(boolean mostQualified) {
		this.mostQualified = mostQualified;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/BirdsOfFeather")
	public boolean isBirdsOfFeather() {
		return birdsOfFeather;
	}
	public void setBirdsOfFeather(boolean birdsOfFeather) {
		this.birdsOfFeather = birdsOfFeather;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/FollowCrowd")
	public boolean isFollowCrowd() {
		return followCrowd;
	}
	public void setFollowCrowd(boolean followCrowd) {
		this.followCrowd = followCrowd;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/SocialExchange")
	public boolean isExchange() {
		return exchange;
	}
	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/Mobilizing")
	public boolean isMobilizing() {
		return mobilizing;
	}
	public void setMobilizing(boolean mobilizing) {
		this.mobilizing = mobilizing;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/FeelingLucky")
	public boolean isFeelingLucky() {
		return feelingLucky;
	}
	public void setFeelingLucky(boolean feelingLucky) {
		this.feelingLucky = feelingLucky;
	}
	@Id
	@Functional
	@RdfProperty("http://vivoweb.org/ontology/core#primaryEmail")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Functional
	@RdfProperty("http://www.w3.org/2000/01/rdf-schema#label")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/URI")
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/Department")
	public String getDepartment(){
		return department;
	}
	public void setDepartment(String department){
		this.department = department;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/DepartmentURI")
	public URI getDepartmentURI(){
		return departmentURI;
	}
	public void setDepartmentURI(URI departmentURI){
		this.departmentURI = departmentURI;
	}
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/Moniker")
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
	@Functional
	@RdfProperty("http://sonic.northwestern.edu/ImageURL")
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
