package domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LikeRepository {
	
	static Map<ImageMetadata, Integer> repo = new ConcurrentHashMap<ImageMetadata, Integer>();
	
	public static Integer like(String siteUrl, String imageUrl){
		
		ImageMetadata im = ImageMetadata.valueOf(siteUrl, imageUrl);
		
		Integer value = 1;
		
		if( isNotLiked(im) ){
				repo.put(im, value);
		} else {
			Integer count = repo.get(im);
			value = 1 + repo.put(im, new Integer(count + 1));
		}
		
		return value;
		
	}
	
	public static int getLikeCount(String siteUrl, String imageUrl){
		ImageMetadata im = ImageMetadata.valueOf(siteUrl, imageUrl);
		if ( repo.containsKey(im) ){
			return repo.get(im);
		} else{
			return 0;
		}
	}
	
	public static boolean isNotLiked(String siteUrl, String imageUrl){
		ImageMetadata im = ImageMetadata.valueOf(siteUrl, imageUrl);
		return ! repo.containsKey(im);
	}
	
	static boolean isNotLiked(ImageMetadata im){
		return ! repo.containsKey(im);
	}
	
	public static void reset(){
		repo.clear();
	}
	
}
