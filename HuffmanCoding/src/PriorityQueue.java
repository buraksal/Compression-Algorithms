
public class PriorityQueue{
	
	private int[] frequencies;
	private int size;
	private int elementNum;
	
	
    public PriorityQueue() {
    	this.size = 1;
    	this.elementNum = 0;
    	this.frequencies = new int[this.size];
    }
    
    public PriorityQueue(int size) {
    	this.size = size;
    	frequencies = new int[this.size];
    	elementNum = 0;
    }
    
    public void insert(int value) {
    	int cnt;
    	if (this.elementNum < size) {
    		if(this.elementNum == 0) {
    			frequencies[elementNum] = value;
    			elementNum++;
    			return;
    		}
			for(cnt = elementNum - 1;  cnt >= 0; cnt--) {
				if(value >= frequencies[cnt])
					frequencies[cnt+1] = frequencies[cnt];
				else
					break;
			}
			cnt++;
			frequencies[cnt] = value;
			elementNum++;
    	} else {
    		System.out.println("Already Full");
    	}
    }
    
    public int remove() {
    	if(this.elementNum == 0) {
    		System.out.println("Already Empty");
    		return -1;
    	}else {
    		elementNum--;
    		return frequencies[elementNum];
    	}
    }
    
    public int peek() {
    	if(elementNum > 0) {
    		return frequencies[elementNum - 1];
    	} else {
    		System.out.println("Empty Queue!");
    		return -1;
    	}
    	
    }

    public static void main(String[] args) {
    	PriorityQueue priorityQueue = new PriorityQueue(10);
    	
    	System.out.println("Last item: " + priorityQueue.peek());
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
	}
	
}
