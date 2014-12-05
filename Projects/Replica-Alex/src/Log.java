

import java.util.Date;

public class Log {
	
	/*
	 * Mark - Constructors
	 */

	public Log() {
		super();
		init();
	}
	
	public Log(Account account, String activity) {
		super();
		this.account = account;
		this.activity = activity;
		init();
	}
	
	/*
	 * Mark - Initialization
	 */

	private void init(){
		date = new Date();
	}

	/*
	 * Mark - Basic - Properties
	 */
	 
	private Account account;
	private String activity;
	private Date date;

	/*
	 * Mark - Basic - Getters & Setters
	 */
	 
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Date getDate() {
		return date;
	}

}
