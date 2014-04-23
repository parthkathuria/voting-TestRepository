package edu.sjsu.cmpe.voting.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.voting.domain.Poll;
import edu.sjsu.cmpe.voting.domain.PollDetails;

@JsonPropertyOrder({"poll","links"})
public class PollDto extends LinksDto {
	
	private Poll poll;

	public PollDto(Poll poll) {
		super();
		this.poll = poll;
	    }
	/**
	 * @return the poll
	 */
	public Poll getPoll() {
		return poll;
	}

	/**
	 * @param poll the poll to set
	 */
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	
	

}
