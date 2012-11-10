package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity
public class UserEntity extends GenericModel {

	@Id
	private String appUserId;
	
	@Column
	private String firstName;
	
	@Column
	private String secondName;
	
	@Column
	private String pictureUrl;
	
	public UserEntity()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void setAppUserId(String appUserId)
	{
		this.appUserId = appUserId;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setSecondName(String secondName)
	{
		this.secondName = secondName;
	}

	public void setPictureUrl(String pictureUrl)
	{
		this.pictureUrl = pictureUrl;
	}
	
}
