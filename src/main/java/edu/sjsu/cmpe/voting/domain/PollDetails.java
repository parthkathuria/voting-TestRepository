/**
 * 
 */
package edu.sjsu.cmpe.voting.domain;


/**
 * Wrapper class for Poll
 */
public class PollDetails {

	private String _id;
	private String question;
	private String option1;
	private String option2;
	private int option1Count;
	private int option2Count;

	public PollDetails(Poll poll) {
		// TODO Auto-generated constructor stub
//		this._id = poll.getUniqueKey();
//		this.question = poll.getQuestion();
//		this.option1 = poll.getOption1();
//		this.option2 = poll.getOption2();
//		this.option1Count = poll.getOption1Count();
//		this.option2Count = poll.getOption2Count();
	}

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
	 * @return the yesCount
	 */
	public int getOption1Count() {
		return option1Count;
	}

	/**
	 * @param yesCount
	 *            the yesCount to set
	 */
	public void setOption1Count(int yesCount) {
		this.option1Count = yesCount;
	}

	/**
	 * @return the noCount
	 */
	public int getOption2Count() {
		return option2Count;
	}

	/**
	 * @param noCount
	 *            the noCount to set
	 */
	public void setOption2Count(int noCount) {
		this.option2Count = noCount;
	}

	/**
	 * @return the uniqueKey
	 */
	public String getUniqueKey() {
		return _id;
	}

	/**
	 * @param uniqueKey
	 *            the uniqueKey to set
	 */
	public void setUniqueKey(String uniqueKey) {
		this._id = uniqueKey;
	}

	/**
	 * @return the option1
	 */
	public String getOption1() {
		return option1;
	}

	/**
	 * @param option1
	 *            the option1 to set
	 */
	public void setOption1(String option1) {
		this.option1 = option1;
	}

	/**
	 * @return the option2
	 */
	public String getOption2() {
		return option2;
	}

	/**
	 * @param option2
	 *            the option2 to set
	 */
	public void setOption2(String option2) {
		this.option2 = option2;
	}

}
