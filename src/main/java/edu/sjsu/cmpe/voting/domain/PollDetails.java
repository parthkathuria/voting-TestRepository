/**
 * 
 */
package edu.sjsu.cmpe.voting.domain;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Wrapper class for Poll
 */
public class PollDetails {

		@JsonProperty("id")
		private String id;
		@JsonProperty("question")
		private String question;
		@JsonProperty("options")
		private ArrayList<Options> options = new ArrayList<Options>();
		@JsonProperty("startDate")
		private String startDate;
		@JsonProperty("endDate")
		private String endDate;
		@JsonProperty("email")
		private String email;
		@JsonProperty("userId")
		private String userId;
		@JsonProperty("first_name")
		private String first_name;
		@JsonProperty("last_name")
		private String last_name;
		
		
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

		/**
		 * @return the startDate
		 */
		public String getStartDate() {
			return startDate;
		}

		/**
		 * @param startDate the startDate to set
		 */
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		/**
		 * @return the endDate
		 */
		public String getEndDate() {
			return endDate;
		}

		/**
		 * @param endDate the endDate to set
		 */
		public void setEndDate(String endDate) {
			this.endDate = endDate;
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
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * @param userId the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
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

}
