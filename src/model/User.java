package model;
// default package
// Generated Nov 24, 2014 3:39:25 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private int id;
	private String firstName;
	private String secondName;
	private String nick;
	private String email;
	private String password;
	private Date signUpDate;
	private int statusid;

	public User() {
	}

	public User(int id, int statusid) {
		this.id = id;
		this.statusid = statusid;
	}

	public User(int id, String firstName, String secondName, String nick,
			String email, String password, Date signUpDate, int statusid) {
		this.id = id;
		this.firstName = firstName;
		this.secondName = secondName;
		this.nick = nick;
		this.email = email;
		this.password = password;
		this.signUpDate = signUpDate;
		this.statusid = statusid;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getNick() {
		return this.nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getSignUpDate() {
		return this.signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public int getStatusid() {
		return this.statusid;
	}

	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}

}
