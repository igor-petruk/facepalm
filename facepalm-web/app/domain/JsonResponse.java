package domain;

public class JsonResponse {
	
	public static String getCount(long count){
		return String.format("{\"count\": %d}", count);
	}
	
	public static String getCount(long count, boolean wasLiked){
		return String.format("{\"count\":%d, \"wasLiked\":\"%s\"}", count, wasLiked ? "true" : "false" );
	}
	
}
