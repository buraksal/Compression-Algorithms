package Compressions;
public class FrequencyChecker {

	private int[] tmpTable = new int[255];
	public int[] freqTable;
	public char[] dataTable;

	//Default Constructor
	public FrequencyChecker() {

	}
	
	//Checks the frequencies of all chars in the given string
	public void checkFrequency(String str) {
		int asciiVal;
		for(int i = 0; i < str.length(); i++) {
			asciiVal = str.charAt(i);
			tmpTable[asciiVal]++;
		}
		this.cleanZeroes();
	}

	//Finds elements which occured in the string
	private int findUnique() {
		int cnt = 0;
		for(int i = 0; i < tmpTable.length; i++) {
			if(tmpTable[i] != 0) {
				cnt++;
			}
		}
		return cnt;
	}
	
	//Cleans up the elements which did not occur in the string
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
}
