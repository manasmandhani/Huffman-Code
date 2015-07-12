

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Map.Entry;

/**
*
* Class "HuffmanCode" implements Humman Encoding and Decoding
*
* @version 1.0
*
* @author  Manas Mandhani
*/
public class HuffmanCode {
	HuffNode[] node_list;
	LinkedHashMap<Character, Integer> map = new LinkedHashMap<Character, Integer>();
	ArrayList<Code> list = new ArrayList<Code>();
	Code code;
	String code_bits = "";
	char[] c_array;
	ArrayList<Character> char_list = new ArrayList<Character>();
	float coded_file_size = 0;
	
	/**
	* Build the Huffman tree by adding two minimum elements and then inserting this new element
	* back to the heap. This new element becomes the parent. The minimum element becomes the left child
	* and the second minimum element becomes the right child. 
	*/
	public HuffNode BuildTree(){
		Heap heap = new Heap();
		heap.buildHeap(node_list);
		int size = heap.heapSize - 1;
		for (int i = 0; i < size; i++){
		HuffNode left_child = heap.ExtractMin(node_list);
		HuffNode right_child = heap.ExtractMin(node_list);
		HuffNode parent = new HuffNode('*', (left_child.value + right_child.value), left_child, right_child);
		heap.HeapInsert(parent, node_list);
	}
		HuffNode topMost = heap.ExtractMin(node_list);
		return topMost;
		}
	
	/**
	* Perform huffman encoding. Traverse down the tree from the root node until a leaf node is encountered and
	* keep on assigning codes '0' or '1' based on the left or right positions.
	*/
	public void Encoding(HuffNode node, int[] arr, int curr){
		if (node.left_child != null){
			arr[curr] = 0;
			Encoding(node.left_child, arr, curr+1);
		}
		if (node.right_child != null){
			arr[curr] = 1;
			Encoding(node.right_child, arr, curr+1);
		}
		
		if(node.isleaf()){           // check if a node is leaf node
			char name = node.name;
			String bits = "";
			System.out.print(node.name + " ");
			for (int i = 0; i < curr; i++){
				bits = bits + arr[i];
				System.out.print(arr[i]);
			}
			coded_file_size = coded_file_size + (bits.length() * node.value);
			code = new Code(name, bits);
			list.add(code);
			System.out.println("");
		}
	}
	
	/**
	* Compute the Compression Ratio. Compression ratio is given by the the ratio of Raw File Size 
	* to Coded File Size.
	*/
	public float getCompressionRatio(long rawFileSize){
		float compression_ratio = 0;
		compression_ratio = (float)(rawFileSize / (coded_file_size/8));
		return compression_ratio;
	}
	
	/**
	* Build Coding bits to decode the encoded text.
	*/
	public void BuildCodeBits(){
		for (int j = 0; j < char_list.size(); j++){
		for (int i = 0; i < list.size(); i++){
			if(char_list.get(j) == list.get(i).name){
			code_bits = code_bits + list.get(i).code;
			break;
				}
			}
		}
		
		System.out.println("\nCode bits: ");
		System.out.println(code_bits);
		}
	
	/**
	* Perform Decoding of the coded text based on the code bits. Traverse from the root. 
	* Move left when a '0' is encountered and move right when a '1' is encountered. 
	*/
	public void Decoding(HuffNode topMost){
		HuffNode node = topMost;
		String decoded_string = "";
		for (int i = 0; i < code_bits.length(); i++){
			 if(Character.getNumericValue(code_bits.charAt(i))==0){
				if (node.left_child != null){
					node = node.left_child;
				}
			}
			else if(Character.getNumericValue(code_bits.charAt(i))==1){
			if (node.right_child != null){
				node = node.right_child;
			}
			}
			 
			if(node.isleaf()){
					decoded_string = decoded_string + node.name;
					node = topMost;
				}
		}
		    System.out.println("\nDecoded Text: ");
		    System.out.println(decoded_string);
	}

	/**
	* Method main controls the execution of the program. Uses Hash Map to count the frequencies of characters.
	*/
	public static void main(String[] args) {
		HuffmanCode huff = new HuffmanCode();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter file name");
		String file_name = sc.next();
		File file = new File(file_name);
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Please enter a valid file or check if the location is correct");
			System.exit(0);
		}
		while(sc.hasNext()){
			huff.c_array = sc.nextLine().toCharArray();
			for (int i = 0; i < huff.c_array.length; i++){
			huff.char_list.add(huff.c_array[i]);
			}
			huff.char_list.add('\n');
            for (Character c : huff.c_array) {
                 if (huff.map.containsKey(c)) {
                    huff.map.put(c, huff.map.get(c) + 1);
                } else {
                    huff.map.put(c, 1);
                }
            }
            if (huff.map.containsKey('\n')) {
                huff.map.put('\n', huff.map.get('\n') + 1);
            } else {
                huff.map.put('\n', 1);
            }
		}
		int i = 0;
		huff.node_list = new HuffNode[huff.map.size()];
        for (Entry<Character, Integer> entry : huff.map.entrySet()) {
        	huff.node_list[i] = new HuffNode(entry.getKey(), entry.getValue(), null, null);
        	i++;
        }
       final long start_time = System.currentTimeMillis();
       HuffNode topMost =  huff.BuildTree();
       int[] arr = new int[500];
       huff.Encoding(topMost, arr, 0);
       huff.BuildCodeBits();
       huff.Decoding(topMost);
       final long end_time = System.currentTimeMillis();
       float compressRatio = huff.getCompressionRatio(file.length());
       System.out.println("Compression Ratio: " + compressRatio);
       System.out.println("Total time taken in Milliseconds " + (end_time - start_time));
       }
	}