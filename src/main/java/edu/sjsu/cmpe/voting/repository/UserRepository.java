package edu.sjsu.cmpe.voting.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import sun.misc.BASE64Encoder;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import edu.sjsu.cmpe.voting.domain.Users;

public class UserRepository implements UserRepositoryInterface {
	private Random random = new SecureRandom();

	public Users saveUser(Users newUser) {
		checkNotNull(newUser, "User instance cannot be null");
		Users u = getUser(newUser.getId());
		if (u == null) {
			DBObject user = new BasicDBObject("_id", newUser.getId()).append(
					"pollsCreated", newUser.getPollsCreated()).append(
					"pollsSubmited", newUser.getPollsSubmited());
			try {
				DB db = mongoConnection();
				DBCollection polls = db.getCollection("users");
				polls.insert(user);

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return newUser;
		}else{
			System.out.println("User already exists.");
			return u;
		}

	}

	public Users getUser(String uid) {
		checkNotNull(uid, "User instance cannot be null");
		Users user = new Users();
		DBObject userObj;
		try {
			DB db = mongoConnection();
			DBCollection users = db.getCollection("users");
			userObj = users.findOne(new BasicDBObject("_id", uid));
			if (userObj == null) {
				System.out.println("User not in database");
				return null;
			} else {
				user.setId(userObj.get("_id").toString());
				user.setPollsCreated((ArrayList<String>) userObj.get("pollsCreated"));
				user.setPollsSubmited((ArrayList<String>) userObj.get("pollsSubmited"));
				return user;
			}

		} catch (UnknownHostException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void updatePollSubmition(String pollId) {
		checkNotNull(pollId, "Poll Id instance cannot be null");
		
	}
	
	public DB mongoConnection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("votingRepository");
		return db;
	}

	@Override
	public void updatePollCreation(String userId, String pollId) {
		checkNotNull(pollId, "Poll Id instance cannot be null");
		checkNotNull(userId, "User Id instance cannot be null");
		Users user = getUser(userId);
		
		try {
			DB db = mongoConnection();
			DBCollection userColl = db.getCollection("users");
			userColl.update(new BasicDBObject("_id",user.getId()), new BasicDBObject("$addToSet", new BasicDBObject("pollsCreated", pollId)));
		}catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
