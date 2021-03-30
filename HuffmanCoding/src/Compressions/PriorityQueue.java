package Compressions;

public class PriorityQueue{

	private HuffmanTree.HuffmanTreeNode[] nodes;
	private int size;
	private int elementNum;

	//Constructor
	public PriorityQueue() {
		this.size = 1;
		this.elementNum = 0;
		this.nodes = new HuffmanTree.HuffmanTreeNode[this.size];
	}
	
	//Constructor with size
	public PriorityQueue(int size) {
		this.size = size;
		nodes = new HuffmanTree.HuffmanTreeNode[this.size];
		elementNum = 0;
	}
	
	//Inserts node to queue
	public void insert(HuffmanTree.HuffmanTreeNode node) {
		int cnt;
		if(this.elementNum < size) {
			if(this.elementNum == 0) {
				nodes[elementNum] = node;
				elementNum++;
				return;
			}
			for(cnt = elementNum - 1;  cnt >= 0; cnt--) {
				if(node.freq < nodes[cnt].freq)
					nodes[cnt+1] = nodes[cnt];
				else
					break;
			}
			cnt++;
			nodes[cnt] = node;
			elementNum++;
		}
	} 
	
	//Removes node from the queue
	public void remove() {
		nodes[elementNum] = null;
		elementNum--;
	}
	
	//Removes the first node from the queue
	public void removeFirst(){
		for(int i = 0; i < size - 1; i++) {
			nodes[i] = nodes[i+1];
		}
		elementNum--;
		nodes[elementNum] = null;
	}

	//Returns the first element of the queue
	public HuffmanTree.HuffmanTreeNode peek() {
		if(elementNum > 0) {
			return nodes[0];
		} else {
			System.out.println("Empty Queue!");
			return null;
		}

	}

	//Returns total number of elements in the queue
	public int getElementNum() {
		return elementNum;
	}

}
