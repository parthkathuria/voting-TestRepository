/**
 * 
 */
package edu.sjsu.cmpe.voting.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Poll {

	//@JsonProperty("id")
	private String id;
	@JsonProperty("question")
	private String question;
	@JsonProperty("options")
	private ArrayList<Options> options = new ArrayList<Options>();
	/*//@JsonProperty("option1")
	private String option1;
	//@JsonProperty("option2")
	private String option2;
	
	//private String answer;
	//@JsonProperty("option1Count")
	private int option1Count;
	//@JsonProperty("option2Count")
	private int option2Count;*/

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
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
	 * @return the options
	 */
	public ArrayList<Options> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(ArrayList<Options> options) {
		this.options = options;
	}

}
