package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import play.db.jpa.Model;

@Entity
public class ImageEntity extends Model implements Comparable<ImageEntity> {
	
	@Column
	String siteUrl;
	
	@Column
	String imageUrl;
	
	@Column
	String userToken;
	
	@Column
	Date date;
	
	public ImageEntity()
	{
		
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}

	public void setSiteUrl(String siteUrl)
	{
		this.siteUrl = siteUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public void setUserToken(String userToken)
	{
		this.userToken = userToken;
	}
	
	@PrePersist
	public void setDate(Date date)
	{
		if ( date == null){
			this.date = new Date();
		} else {
			this.date = date;
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((siteUrl == null) ? 0 : siteUrl.hashCode());
		result = prime * result + ((userToken == null) ? 0 : userToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if ( this == obj )
			return true;
		if ( !super.equals(obj) )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		ImageEntity other = (ImageEntity) obj;
		if ( id != other.id )
			return false;
		if ( imageUrl == null ) {
			if ( other.imageUrl != null )
				return false;
		} else if ( !imageUrl.equals(other.imageUrl) )
			return false;
		if ( siteUrl == null ) {
			if ( other.siteUrl != null )
				return false;
		} else if ( !siteUrl.equals(other.siteUrl) )
			return false;
		if ( userToken == null ) {
			if ( other.userToken != null )
				return false;
		} else if ( !userToken.equals(other.userToken) )
			return false;
		return true;
	}

	@Override
	public int compareTo(ImageEntity o)
	{
		return date.compareTo(o.date);
	}
	
}
