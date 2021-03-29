package Compressions;
public class FrequencyChecker {
	
	private int[] tmpTable = new int[255];
	public int[] freqTable;
	public char[] dataTable;
	
	//Default Constructor
	public FrequencyChecker() {
		
	}
	
	public void checkFrequency(String str) {
		int asciiVal;
		for(int i = 0; i < str.length(); i++) {
			//System.out.println(str.charAt(i));
			asciiVal = str.charAt(i);
			tmpTable[asciiVal]++;
		}
		this.cleanZeroes();
	}
	
	private int findUnique() {
		int cnt = 0;
		for(int i = 0; i < tmpTable.length; i++) {
			if(tmpTable[i] != 0) {
				cnt++;
			}
		}
		return cnt;
	}
	
	private void cleanZeroes() {
		int tmp = 0;
		int cnt = this.findUnique();
		freqTable = new int[cnt];
		dataTable = new char[cnt];
		for(int i = 0; i < tmpTable.length; i++) {
			if(tmpTable[i] != 0) {
				freqTable[tmp] = tmpTable[i];
				dataTable[tmp] = (char)i;
				tmp++;
			}
		}
	}
	/*
	public static void main(String[] args) {
		String str = "Let's see the simple code to convert char to int in java.";
		HuffmanFrequencyChecker freqTable = new HuffmanFrequencyChecker();
		freqTable.checkFrequency(str);
		int[] table = freqTable.freqTable;
		char[] dataTable = freqTable.dataTable;
		//FrequencyChecker freqChecker = new FrequencyChecker();
		for(int i = 0; i < table.length; i++) {
			System.out.println("Char: " + dataTable[i] + " with frequency: " +table[i]);
		}
	}
	*/
}
