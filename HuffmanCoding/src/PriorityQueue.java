
public class PriorityQueue{
	
	private HuffmanTree.HuffmanTreeNode[] nodes;
	private int size;
	private int elementNum;
	
	
    public PriorityQueue() {
    	this.size = 1;
    	this.elementNum = 0;
    	this.nodes = new HuffmanTree.HuffmanTreeNode[this.size];
    }
    
    public PriorityQueue(int size) {
    	this.size = size;
    	nodes = new HuffmanTree.HuffmanTreeNode[this.size];
    	elementNum = 0;
    }
    
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
    
    /*
    public void insert(int value) {
    	int cnt;
    	if (this.elementNum < size) {
    		if(this.elementNum == 0) {
    			datas[elementNum] = value;
    			elementNum++;
    			return;
    		}
			for(cnt = elementNum - 1;  cnt >= 0; cnt--) {
				if(value >= datas[cnt])
					datas[cnt+1] = datas[cnt];
				else
					break;
			}
			cnt++;
			datas[cnt] = value;
			elementNum++;
    	} else {
    		System.out.println("Already Full");
    	}
    }
    */  

    public void remove() {
    	nodes[elementNum] = null;
		elementNum--;
    }
    
    public void removeFirst(){
    	for(int i = 0; i < size - 1; i++) {
    		nodes[i] = nodes[i+1];
    	}
    	elementNum--;
    	nodes[elementNum] = null;
    }
    
    public HuffmanTree.HuffmanTreeNode peek() {
    	if(elementNum > 0) {
    		return nodes[0];
    	} else {
    		System.out.println("Empty Queue!");
    		return null;
    	}
    	
    }
    
    public int getElementNum() {
    	return elementNum;
    }

    public static void main(String[] args) {
    	PriorityQueue priorityQueue = new PriorityQueue(10);
    	
    	System.out.println("Last item: " + priorityQueue.peek());
    	/*
        priorityQueue.insert(77);
        priorityQueue.insert(55);
        priorityQueue.insert(66);
        priorityQueue.insert(61);
        priorityQueue.insert(817);
        System.out.println("Last item: " + priorityQueue.peek());
        priorityQueue.insert(727);
        priorityQueue.insert(572);
        priorityQueue.insert(617);
        priorityQueue.insert(1);
        priorityQueue.insert(7);
        priorityQueue.insert(2);
        priorityQueue.insert(6);
        
        System.out.print("Deleted elements from queue: ");
        System.out.print(priorityQueue.remove()+" ");
        System.out.print(priorityQueue.remove()+" ");
        System.out.print(priorityQueue.remove()+" ");
        System.out.print(priorityQueue.remove()+" ");
        System.out.print(priorityQueue.remove()+" ");
        System.out.print(priorityQueue.remove()+" ");
       
        priorityQueue.insert(6);
         */
	}
	
}
