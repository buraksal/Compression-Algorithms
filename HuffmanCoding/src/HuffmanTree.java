
public class HuffmanTree {
	
	static class HuffmanTreeNode {
		int freq;
		char data;
		String code;
		HuffmanTreeNode left;
		HuffmanTreeNode right;
	}
	
	public HuffmanTree() {
		
	}

	public static void createCode(HuffmanTreeNode root, String code) {
		if (root.left == null && root.right == null) {
            System.out.println(root.data + ":" + code);
            
            return;
		}
		if(root.left != null)
			createCode(root.left, code + "0");
		if(root.right != null)
			createCode(root.right, code + "1");
	}
	
	public static void main(String[] args) {
		String str = "Eerie eyes seen near lake.";
		HuffmanFrequencyChecker hfc = new HuffmanFrequencyChecker();
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
		createCode(root,"");
		System.out.println("asd");
	}

	
}
