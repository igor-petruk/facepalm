package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import play.db.jpa.Model;

@Entity
public class Comment extends Model implements Comparable<Comment> {

	@Column(length = 1024)
	private String comment;
	
	@Column
	private String userId;
	
	@Column
	private Date date;
	
	@Column
	private String relatedObjectId;
	
	public static Comment valueOf(String userId, String comment, String relatedObjectId){
		Comment cmt = new Comment();
		
		cmt.setUserId(userId);
		cmt.setComment(comment);
		cmt.setRelatedObjectId(relatedObjectId);
		
		return cmt;
	}
	
	public Comment()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public void setRelatedObjectId(String relatedObjectId)
	{
		this.relatedObjectId = relatedObjectId;
	}
	
	public String getRelatedObjectId()
	{
		return relatedObjectId;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
	
	@PrePersist
	public void setPostingDate(){
		date = new Date();
	}
	
	@Override
	public int compareTo(Comment o)
	{
		return o.compareTo(this);
	}
	
}
