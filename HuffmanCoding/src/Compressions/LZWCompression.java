package Compressions;
import java.util.*;

public class LZWCompression {

	//Compressed the given string 
	public static List<Integer> compress(String uncompressed) {
		int dictSize = 256;
		int cnt = 0;
		Map<String,Integer> dictionary = new HashMap<String,Integer>();
		for (int i = 0; i < 256; i++)
			dictionary.put("" + (char)i, i);

		String w = "";
		List<Integer> result = new ArrayList<Integer>();
		for (char c : uncompressed.toCharArray()) {
			cnt++;
			if(cnt%10000 == 0) {
				System.out.println("Still going on: " + cnt + " Total Length: " + uncompressed.toCharArray().length);
			}
			String wc = w + c;
			if (dictionary.containsKey(wc))
				w = wc;
			else {
				result.add(dictionary.get(w));
				dictionary.put(wc, dictSize++);
				w = "" + c;
			}
		}

		if (!w.equals(""))
			result.add(dictionary.get(w));

		return result;
	}

	//Decompressed the compressed message
	public static String decompress(List<Integer> compressed) {

		int dictSize = 256;
		Map<Integer,String> dictionary = new HashMap<Integer,String>();
		for (int i = 0; i < 256; i++)
			dictionary.put(i, "" + (char)i);

		String w = "" + (char)(int)compressed.remove(0);
		StringBuffer result = new StringBuffer(w);
		for (int k : compressed) {
			String entry;
			if (dictionary.containsKey(k))
				entry = dictionary.get(k);
			else if (k == dictSize)
				entry = w + w.charAt(0);
			else
				throw new IllegalArgumentException("Bad compressed k: " + k);

			result.append(entry);

			dictionary.put(dictSize++, w + entry.charAt(0));

			w = entry;
		}
		return result.toString();
	}
}