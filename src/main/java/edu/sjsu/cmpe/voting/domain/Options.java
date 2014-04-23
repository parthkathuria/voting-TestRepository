/**
 * 
 */
package edu.sjsu.cmpe.voting.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author parthkathuria
 *
 */
public class Options {
	@JsonProperty("option")
	private String option;
	@JsonProperty("count")
	private int count;
	/**
	 * @return the option
	 */
	public String getOption() {
		return option;
	}
	/**
	 * @param option the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
