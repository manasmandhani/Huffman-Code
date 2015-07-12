

/**
*
* Class "HuffNode" maintains the nodes. Each node has a name and frequency and 
* it maitains the parent child relationship among nodes.
*
* @version 1.0
*
* @author  Manas Mandhani
*/

public class HuffNode {
	char name;
	int value;
	HuffNode left_child;
	HuffNode right_child;
	
	public HuffNode(char name, int value, HuffNode left_child, HuffNode right_child){
		this.name = name;
		this.value = value;
		this.left_child = left_child;
		this.right_child = right_child;
	}
	
	public boolean isleaf(){
	    if(this.left_child == null && this.right_child == null){
	        return true;
	    }else{
	        return false;
	    }
}
}