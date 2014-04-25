/**
 * 
 */
package edu.sjsu.cmpe.voting.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import edu.sjsu.cmpe.voting.domain.Options;
import edu.sjsu.cmpe.voting.domain.Poll;

public class VotingRepository implements VotingRepositoryInterface {

	/** In-memory map to store polls. (Key, Value) -> (PollKey, Poll) */
	private final ConcurrentHashMap<String, Poll> pollInMemoryMap;
	static Random rnd;
	// Characters used to generate unique 4 letter key.
	static final String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	// Key to be generated and associated with every poll question
	private long pollKey;

	/** Constructor to create a Voting Repository */
	public VotingRepository() {
		// checkNotNull(pollMap, "bookMap must not be null for BookRepository");
		pollInMemoryMap = null;
		pollKey = 0;

	}

	/**
	 * Method to generate a random unique key for each poll question posted by
	 * an user.
	 */
	private final String generatePollKey() {
		int len = 4;
		rnd = new Random();

		// Building a key of length 4.
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(characters.charAt(rnd.nextInt(characters.length())));
		System.out.println("Key : " + sb);
		// Checking uniqueness of the Key generated
		Poll poll = getPollbyKey(sb.toString());
		if (poll == null)
			return sb.toString();
		else
			return generatePollKey();
	}

	/**
	 * Method to save the polls created by users.
	 */
	public Poll savePoll(Poll newPoll) {
		checkNotNull(newPoll, "Poll instance cannot be null");
		String key = generatePollKey();
		newPoll.setId(key);
		BasicDBObject pollInMemory = new BasicDBObject("_id", newPoll.getId());
		pollInMemory.append("question", newPoll.getQuestion());
		pollInMemory.append("options", new ArrayList());
		pollInMemory.append("startDate", newPoll.getStartDate());
		pollInMemory.append("endDate",newPoll.getEndDate());
		pollInMemory.append("email", newPoll.getEmail());
		try {
			DB db = mongoConnection();
			DBCollection poll = db.getCollection("poll");
			poll.insert(pollInMemory);
			for(int i = 0; i < newPoll.getOptions().size();i++){
				BasicDBObject options = new BasicDBObject("option",newPoll.getOptions().get(i).getOption()).append("count", newPoll.getOptions().get(i).getCount());
				poll.update(new BasicDBObject("_id",key), new BasicDBObject("$push", new BasicDBObject("options", options)));
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newPoll;
	}

	/**
	 * Method that returns the poll question associated with the key.
	 */
	public Poll getPollbyKey(String key) {
		checkNotNull(key, "");
		DB db;

		try {
			db = mongoConnection();
			Poll poll = new Poll();
			DBCollection pollColl = db.getCollection("poll");
			DBObject pollObj = pollColl.findOne(new BasicDBObject("_id", key));
			// System.out.println("Poll object " + pollObj);

			if (pollObj != null) {
				// System.out.println(pollObj.get("_id").toString());
				poll.setId(pollObj.get("_id").toString());
				poll.setQuestion(pollObj.get("question").toString());
				poll.setStartDate(pollObj.get("startDate").toString());
				poll.setEndDate(pollObj.get("endDate").toString());
				poll.setEmail(pollObj.get("email").toString());
				ArrayList<DBObject> optionsObj = (ArrayList<DBObject>) pollObj.get("options");
				ArrayList<Options> options = new ArrayList<Options>();
				for (int i = 0; i < optionsObj.size(); i++) {
					Options op = new Options();
					op.setOption(optionsObj.get(i).get("option").toString());
					op.setCount((Integer) optionsObj.get(i).get("count"));
					options.add(op);
				}
				poll.setOptions(options);
				// poll1.setAnswer(pollObj.get("answer").toString());
				System.out.println("Poll " + poll);
				return poll;
			} else {
				return null;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Method called when PUT operation is performed to record the answer to the
	 * poll.
	 */
	public void updatePoll(String key, String answer) {

		checkNotNull(key, "Key instance cannot be null");
		DB db;
		Poll poll = new Poll();
		try {
			db = mongoConnection();
			DBCollection pollColl = db.getCollection("poll");
			DBObject pollObj = pollColl.findOne(new BasicDBObject("_id", key));

			poll.setId(pollObj.get("_id").toString());
			poll.setQuestion(pollObj.get("question").toString());
			poll.setStartDate(pollObj.get("startDate").toString());
			poll.setEndDate(pollObj.get("endDate").toString());
			poll.setEmail(pollObj.get("email").toString());
			ArrayList<DBObject> optionsObj = (ArrayList<DBObject>) pollObj.get("options");
			ArrayList<Options> options = new ArrayList<Options>();
			for (int i = 0; i < optionsObj.size(); i++) {
				Options op = new Options();
				op.setOption(optionsObj.get(i).get("option").toString());
				op.setCount((Integer) optionsObj.get(i).get("count"));
				options.add(op);
			}
			poll.setOptions(options);
			// Option 1 selection (Yes) Incrementing the option count by 1,
			// everytime it is selected
			for (int i = 0; i < options.size(); i++) {
				if (answer.equalsIgnoreCase(poll.getOptions().get(i)
						.getOption())) {
					int count = poll.getOptions().get(i).getCount();
					poll.getOptions().get(i).setCount(++count);
					pollColl.update(
							new BasicDBObject("_id", poll.getId()).append("options.option", poll.getOptions().get(i).getOption()),
							new BasicDBObject("$set", new BasicDBObject("options.$.count",poll.getOptions().get(i).getCount())), false, false);
				}
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public DB mongoConnection() throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("votingRepository");
		return db;
	}

	public ArrayList<Poll> getAllPolls() {
		try {
			ArrayList<Poll> poll = new ArrayList<Poll>();

			DB db = mongoConnection();
			DBCollection pollColl = db.getCollection("poll");
			DBCursor cursor = pollColl.find();
			// System.out.println(cursor);
			for (int i = 0; i < cursor.size(); i++) {
				if (cursor.hasNext()) {
					DBObject pollObject = cursor.next();
					if (pollObject.get("_id").toString().equals("NHG4")) {
						Poll p = new Poll();
						p.setId(pollObject.get("_id").toString());
						p.setQuestion(pollObject.get("question").toString());
						p.setStartDate(pollObject.get("startDate").toString());
						p.setEndDate(pollObject.get("endDate").toString());
						p.setEmail(pollObject.get("email").toString());
						ArrayList<DBObject> optionsObj = (ArrayList<DBObject>) pollObject.get("options");
						ArrayList<Options> options = new ArrayList<Options>();
						for (int j = 0; j < optionsObj.size(); j++) {
							Options op = new Options();
							op.setOption(optionsObj.get(j).get("option").toString());
							op.setCount((Integer) optionsObj.get(j).get("count"));
							options.add(op);
						}
						p.setOptions(options);
						System.out.println(p.getQuestion());
						poll.add(p);
					}

				} else {
					break;
				}
			}
			for (int j = 0; j < poll.size(); j++) {
				System.out.println(poll.get(j).getId());
			}
			return poll;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
