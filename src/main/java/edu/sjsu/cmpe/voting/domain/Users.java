package edu.sjsu.cmpe.voting.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {
	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;
	@JsonProperty("name")
	private String name;
	@JsonProperty("polls")
	private ArrayList<String> polls = new ArrayList<String>();
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the polls
	 */
	public ArrayList<String> getPolls() {
		return polls;
	}
	/**
	 * @param polls the polls to set
	 */
	public void setPolls(ArrayList<String> polls) {
		this.polls = polls;
	}
	


}
