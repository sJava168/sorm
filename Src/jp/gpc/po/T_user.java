package jp.gpc.po;

import java.sql.*;
import java.util.*;

public class T_user {

	private java.sql.Timestamp regTime;
	private Integer id;
	private String pwd;
	private Integer age;
	private String username;
	private java.sql.Timestamp corectDate;


	public java.sql.Timestamp getRegTime(){
		return regTime;
	}
	public Integer getId(){
		return id;
	}
	public String getPwd(){
		return pwd;
	}
	public Integer getAge(){
		return age;
	}
	public String getUsername(){
		return username;
	}
	public java.sql.Timestamp getCorectDate(){
		return corectDate;
	}
	public void setRegTime(java.sql.Timestamp regTime){
		this.regTime = regTime;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public void setPwd(String pwd){
		this.pwd = pwd;
	}
	public void setAge(Integer age){
		this.age = age;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public void setCorectDate(java.sql.Timestamp corectDate){
		this.corectDate = corectDate;
	}
}
