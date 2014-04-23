package edu.sjsu.cmpe.voting.repository;

import edu.sjsu.cmpe.voting.domain.Users;

public interface UserRepositoryInterface {

	Users saveUser(Users newUser);
	
	Users getUser(String username,String password);
	
}
