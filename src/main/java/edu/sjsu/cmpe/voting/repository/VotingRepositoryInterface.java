package edu.sjsu.cmpe.voting.repository;

import java.util.ArrayList;

import edu.sjsu.cmpe.voting.domain.Poll;

public interface VotingRepositoryInterface {
	
	/**
     * Save a new poll in the repository
     * 
     * @param newPoll
     *            
     * @return a newly created poll
     */

	Poll savePoll(Poll newPoll);

	Poll getPollbyKey(String key);
	
	void updatePoll(String key, String answer);
	
	ArrayList<Poll> getAllPolls();
}