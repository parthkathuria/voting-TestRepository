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
			DBObject user = new BasicDBObject("_id", newUser.getId());
			user.put("name", newUser.getName());
			user.put("first_name", newUser.getFirst_name());
			user.put("last_name", newUser.getLast_name());
			user.put("email", newUser.getEmail());
			user.put("gender", newUser.getGender());

			user.put("pollsCreated", newUser.getPollsCreated());
			user.put("pollsSubmitted", newUser.getPollsSubmitted());
			try {
				DB db = mongoConnection();
				DBCollection polls = db.getCollection("users");
				polls.insert(user);

			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return newUser;
		} else {
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
				user.setName(userObj.get("name").toString());
				user.setFirst_name(userObj.get("first_name").toString());
				user.setLast_name(userObj.get("last_name").toString());
				user.setEmail(userObj.get("email").toString());
				user.setGender(userObj.get("gender").toString());
				user.setPollsCreated((ArrayList<String>) userObj.get("pollsCreated"));
				user.setPollsSubmitted((ArrayList<String>) userObj.get("pollsSubmitted"));
				return user;
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public DB mongoConnection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("votingRepository");
		return db;
	}

	public void updatePollCreation(String userId, String pollId) {
		checkNotNull(pollId, "Poll Id instance cannot be null");
		checkNotNull(userId, "User Id instance cannot be null");
		Users user = getUser(userId);

		try {
			DB db = mongoConnection();
			DBCollection userColl = db.getCollection("users");
			userColl.update(new BasicDBObject("_id", user.getId()),
					new BasicDBObject("$addToSet", new BasicDBObject(
							"pollsCreated", pollId)));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updatePollSubmition(String userId, String pollId) {
		checkNotNull(pollId, "Poll Id instance cannot be null");
		checkNotNull(userId, "User Id instance cannot be null");
		Users user = getUser(userId);

		try {
			DB db = mongoConnection();
			DBCollection userColl = db.getCollection("users");
			userColl.update(new BasicDBObject("_id", user.getId()),
					new BasicDBObject("$addToSet", new BasicDBObject(
							"pollsSubmitted", pollId)));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deletePoll(String pollId) {
		// TODO Auto-generated method stub
		DB db;
		try {
			db = mongoConnection();
			DBCollection userColl = db.getCollection("users");
			userColl.update(new BasicDBObject("pollsCreated", pollId),
					new BasicDBObject("$pull", new BasicDBObject(
							"pollsCreated", pollId)), false, true);
			userColl.update(new BasicDBObject("pollsSubmitted", pollId),
					new BasicDBObject("$pull", new BasicDBObject(
							"pollsSubmitted", pollId)), false, true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
