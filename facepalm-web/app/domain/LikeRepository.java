package domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LikeRepository {
	
	static Map<ImageMetadata, Integer> repo = new ConcurrentHashMap<ImageMetadata, Integer>();
	
	public static void like(String siteUrl, String imageUrl){
		
		ImageMetadata im = ImageMetadata.valueOf(siteUrl, imageUrl);
		
		if( isNotLiked(im) ){
				repo.put(im, 1);
		} else {
			Integer count = repo.get(im);
			repo.put(im, count + 1);
		}
		
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
	
}
