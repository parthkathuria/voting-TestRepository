package edu.sjsu.cmpe.voting.repository;

import com.yammer.dropwizard.jersey.params.LongParam;

import edu.sjsu.cmpe.voting.domain.Users;

public interface UserRepositoryInterface {

	Users saveUser(Users newUser);
	
	Users getUser(String userId);
	
	void updatePollSubmition(String userId, String pollId);
	
	void updatePollCreation(String userId, String pollId);
	
}
