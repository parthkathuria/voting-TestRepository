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
	public Users saveUser(Users newUser){
		checkNotNull(newUser, "User instance cannot be null");
		DBObject user = new BasicDBObject("_id",newUser.getUsername());
		String password = newUser.getPassword();
		String passwordHash = makePasswordHash(password, Integer.toString(random.nextInt()));
		user.put("password",passwordHash);
		user.put("polls", newUser.getPolls());
		try {
			DB db = mongoConnection();
			DBCollection polls = db.getCollection("users");
			polls.insert(user);			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newUser;
		
	}
	public Users getUser(String username,String password) {
		checkNotNull(username, "Username instance cannot be null");
		checkNotNull(password, "Password instance cannot be null");
		DBObject user;
		Users userData = null;
		try {
			DB db = mongoConnection();
			DBCollection users = db.getCollection("users");
			user = users.findOne(new BasicDBObject("_id",username));
			if(user == null){
	            System.out.println("User not in database");
				return null;
			}
				String hashedAndSalted = user.get("password").toString();
				String salt = hashedAndSalted.split(",")[1];
				if (!hashedAndSalted.equals(makePasswordHash(password, salt))) {
		            System.out.println("Submitted password is not a match");
		            return null;
		        }else{
		        	userData.setUsername(user.get("username").toString());
		        	userData.setName(user.get("name").toString());
		        	userData.setPassword(hashedAndSalted);
		        	userData.setPolls((ArrayList<String>) user.get("polls"));
		        	return userData;
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
	
	private String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
            return encoder.encode(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 is not available", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
        }
    }
	
}
