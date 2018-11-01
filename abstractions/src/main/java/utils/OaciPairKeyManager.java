package utils;

public class OaciPairKeyManager {
	
	public static String getKey(String oaci1, String oaci2) {
		return oaci1 + "," + oaci2;
	}
	
	public static String[] getOaciPair(String key) {
		return key.split(",");
	}

}
