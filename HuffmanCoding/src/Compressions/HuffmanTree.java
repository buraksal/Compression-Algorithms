package Compressions;

public class HuffmanTree {
	
	public static class HuffmanTreeNode {
		int freq;
		char data;
		String code;
		HuffmanTreeNode left;
		HuffmanTreeNode right;
		
		public HuffmanTreeNode findNode(HuffmanTreeNode root, char data){
			HuffmanTreeNode toFind = new HuffmanTreeNode();
			if (root == null) 
				return toFind;
		 
		    if (root.data == data) {
		    	toFind = root; 
		    	return toFind;
		    }
		        
		    toFind = findNode(root.left, data); 
		    if(toFind.code != null) {
		    	return toFind;
		    }
		    toFind = findNode(root.right, data); 
			
			return toFind;
		}
	}
	
	public HuffmanTree() {
		
	}

	public static void createCode(HuffmanTreeNode root, String code) {
		if (root.left == null && root.right == null) {
            System.out.println(root.data + ":" + code);
            root.code = code;
            return;
		}
		if(root.left != null)
			createCode(root.left, code + "0");
		if(root.right != null)
			createCode(root.right, code + "1");
	}


	public static HuffmanTreeNode createTree(String str) {	
		FrequencyChecker hfc = new FrequencyChecker();
		hfc.checkFrequency(str);
		PriorityQueue pq = new PriorityQueue(hfc.dataTable.length);
		for(int i = 0; i < hfc.dataTable.length; i++) {
			HuffmanTreeNode htn = new HuffmanTreeNode();
			htn.data = hfc.dataTable[i];
			htn.freq = hfc.freqTable[i];
			htn.left = null;
			htn.right = null;
			pq.insert(htn);
		}
		
		HuffmanTreeNode root = null;
		
		while(pq.getElementNum() > 1) {
			HuffmanTreeNode node1 = pq.peek();
			pq.removeFirst();
			HuffmanTreeNode node2 = pq.peek();
			pq.removeFirst();
			HuffmanTreeNode f = new HuffmanTreeNode();
			f.freq = node1.freq + node2.freq;
			f.left = node1;
			f.right = node2;
			root = f;
			pq.insert(f);
		}
		return root;
	}
	
	
	public static void main(String[] args) {
		String str = "asdasdfnbsafcwemrwecwecfhwemgceyERMCEBNVFGEDSWMTYRWatghwjen "
				+ "sbahymtaWFAEHAYTCTEaeeem<whrcWGHMCAREHYGMACHQMGTCQEYERGRHECTQEW"
				+ "FHAYTCTEaeeem<whrcWGHMCAREHYMCAREHYGMACHQMGTCQEYERGRHECTQEWFH";
		HuffmanTreeNode root = createTree(str);
		createCode(root,"");
		String encodedStr = encode(root, str);
		System.out.println("Encoding: " + encodedStr);
		String decodedStr = decode(root, encodedStr);
		System.out.println("Decoding: " + decodedStr);
	}

	public static String decode(HuffmanTreeNode root, String encodedStr) {
		String str = "";
		HuffmanTreeNode newNode = new HuffmanTreeNode();
		newNode = root;
		for(int i = 0; i < encodedStr.length(); i++) {
			if(newNode.right == null && newNode.left == null) {
				str += newNode.data;
				newNode = root;
			}
			
			if(encodedStr.charAt(i) == '0' && newNode.left != null) {
				newNode = newNode.left;
			}
			if(encodedStr.charAt(i) == '1' && newNode.right != null) {
				newNode = newNode.right;
			}
		}
		return str;
	}

	public static String encode(HuffmanTreeNode root, String str) {

		HuffmanTreeNode newNode = new HuffmanTreeNode();
		String encodedStr = "";
		for(int i = 0; i < str.length(); i++) {
			newNode = root.findNode(root, str.charAt(i));
			encodedStr += newNode.code;
		}
		return encodedStr;
	}
	
}
