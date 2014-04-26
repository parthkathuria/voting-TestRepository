package edu.sjsu.cmpe.voting.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("first_name")
	private String first_name;
	@JsonProperty("last_name")
	private String last_name;
	@JsonProperty("email")
	private String email;
	@JsonProperty("gender")
	private String gender;
	private ArrayList<String> pollsCreated = new ArrayList<String>();
	private ArrayList<String> pollsSubmited = new ArrayList<String>();
	
	/**
	 * @return the pollsCreated
	 */
	public ArrayList<String> getPollsCreated() {
		return pollsCreated;
	}
	/**
	 * @param pollsCreated the pollsCreated to set
	 */
	public void setPollsCreated(ArrayList<String> pollsCreated) {
		this.pollsCreated = pollsCreated;
	}
	/**
	 * @return the pollsSubmited
	 */
	public ArrayList<String> getPollsSubmited() {
		return pollsSubmited;
	}
	/**
	 * @param pollsSubmited the pollsSubmited to set
	 */
	public void setPollsSubmited(ArrayList<String> pollsSubmited) {
		this.pollsSubmited = pollsSubmited;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}
	/**
	 * @param first_name the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}
	/**
	 * @param last_name the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

}
