package domain;

public class JsonResponse {
	
	public static String getCount(int count){
		return String.format("{\"count\": %d}", count);
	}
	
}
