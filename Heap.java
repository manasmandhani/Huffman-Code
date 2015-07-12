

/**
*
* Class Heap implements Heap operations
*
* @version 1.0
*
* @author  Manas Mandhani
*/

public class Heap {
	 int heapSize;
	     
	    public Heap() {
	        heapSize = 0;
	    }
  
	    public int getLeftChild(int i) { 
	        return  (2 * i) + 1;
	    }
	    
	    public int getRightChild(int i) { 
	    	return  (2 * i) + 2;
	    }
	    
	    public int getParent(int i) { 
	        return (int) (i - 1)/2;
	    }
	    
		/**
		* Heapify down so that minimum element is at root
		*/
	    public void HeapifyDown(HuffNode[] array, int i) {
	    	int l = getLeftChild(i);
	    	int r = getRightChild(i);
	    	int smallest = i;
		    	if (l < heapSize && array[l].value < array[smallest].value )
	    		smallest = l;
	    	if (r < heapSize && array[r].value < array[smallest].value)
	    		smallest = r;
	    	if(smallest != i){
	    		HuffNode temp = array[i];
	    		array[i] = array[smallest];
	    		array[smallest] = temp;
	    		HeapifyDown(array, smallest);
	    	}
	    }
	    
		/**
		* Insert a new element into heap
		*/
	    public void HeapInsert (HuffNode key, HuffNode[] array) {
	    	heapSize = heapSize + 1;
	    	int i = heapSize - 1;
	    	array[i] = key;
	    	HuffNode temp = array[i];
	    	while(i > 0 && temp.value < array[getParent(i)].value){
	    		array[i] = array[getParent(i)];
	    		i = getParent(i);
	    	}
	    	array[i] = temp;
	    }
	    
		/**
		* Extract the minimum element from the heap
		*/
	    public HuffNode ExtractMin(HuffNode[] array){
	    	HuffNode min = null;
	    	if(heapSize >= 1){
	    		min = array[0];
	    		array[0] = array[heapSize - 1];
	    		heapSize = heapSize - 1;
	    		HeapifyDown(array, 0);
	    		}
	    	return min;
	    }
	 
		/**
		* Build heap
		*/
	    public void buildHeap(HuffNode[] array) {
	    	this.heapSize = array.length;
	    	int n = array.length / 2;
	    	for (int i = n; i >= 0; i--){
	    		HeapifyDown(array, i);
	    	}
	    }
}