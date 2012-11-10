package models;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import play.db.jpa.Model;

@Entity
@Access(AccessType.FIELD)

//@NamedQueries(value = {
//		@NamedQuery(name = ImageEntity.COUNT_QUERY, query = "select count(ImageEntity) from ImageEntity ie where ie.siteUrl = :siteUrl and ie.imageUrl = :imageUrl"),
//		@NamedQuery(name = "likedImagesByUserQuery", query = "select ImageEntity from ImageEntity ie where ie.userToken = :userToken"),
//		@NamedQuery(name = "likedImagesOnSiteCountQuery", query = "select count(*) from ImageEntity ie where ie.siteUrl = :siteUrl"),
//		@NamedQuery(name = "likedImagesOnSiteQuery", query = "select ImageEntity from ImageEntity ie where ie.siteUrl = :siteUrl")
//})

public class ImageEntity extends Model {
	
	public static final String COUNT_QUERY = "countQuery";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@Column
	String siteUrl;
	
	@Column
	String imageUrl;
	
	@Column
	String userToken;
	
	public ImageEntity()
	{
		// TODO Auto-generated constructor stub
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
	
}
