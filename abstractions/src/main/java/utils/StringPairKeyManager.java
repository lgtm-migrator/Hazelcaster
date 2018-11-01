package utils;

public class StringPairKeyManager {
	
	public static String getKey(String str1, String str2) {
		return str1 + "," + str2;
	}
	
	public static String[] getStringPair(String key) {
		return key.split(",");
	}

}
