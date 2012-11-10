package models;

public class Pair {
	
	private ImageEntity imageEntity;
	private Long counter;
	
	public Pair(ImageEntity imageEntity, Long counter)
	{
		this.imageEntity = imageEntity;
		this.counter = counter;
	}
	
	public ImageEntity getImageEntity()
	{
		return imageEntity;
	}
	public void setImageEntity(ImageEntity imageEntity)
	{
		this.imageEntity = imageEntity;
	}
	public Long getCounter()
	{
		return counter;
	}
	public void setCounter(Long counter)
	{
		this.counter = counter;
	}
	
	
	
}
