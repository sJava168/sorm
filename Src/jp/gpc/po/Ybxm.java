package jp.gpc.po;

import java.sql.*;
import java.util.*;

public class Ybxm {

	private String owner;
	private String unit;
	private String subject;
	private Integer id;


	public String getOwner(){
		return owner;
	}
	public String getUnit(){
		return unit;
	}
	public String getSubject(){
		return subject;
	}
	public Integer getId(){
		return id;
	}
	public void setOwner(String owner){
		this.owner = owner;
	}
	public void setUnit(String unit){
		this.unit = unit;
	}
	public void setSubject(String subject){
		this.subject = subject;
	}
	public void setId(Integer id){
		this.id = id;
	}
}
