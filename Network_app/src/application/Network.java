package application;
//The class should contain a list of nodes and a list of edges
//A default constructor that creates an empty network
//Some methods to aid in analysis of the network
import java.util.*;

import javafx.scene.control.Label;

import java.io.*;

public class Network {
	public ArrayList<Node> nodeList;  //The network class contains a lists of nodes and edges
	public ArrayList<Edge> edgeList;

	public Network () {				//default constructor
		edgeList = new ArrayList<Edge>();
		nodeList = new ArrayList<Node>();
	}
	
	//This method adds a node to the node list of the network; checks if it's not a duplicate before adding to the list
	//Accepts the name of the node as an argument (String) 
	//The name of the node can be found by using .returnName() method
	public void addNode(String node_name) {  //void means it does not return any type
		int duplicates = 0;
		for (Iterator<Node> item = nodeList.iterator(); item.hasNext();) { //iteration over the list of nodes
			Node node = item.next();  //a single node bound to variable
			if(node.returnName().equals(node_name)){  //checks if the argument name matches any name on the node list
				duplicates = 1;  //if there is a match, the duplicate value is updated
			}
		}	
			if (duplicates == 0) { 				//if duplicate value is not 0 the node list will not be updated since it already exists in the list
				nodeList.add(new Node(node_name));
		}
	}
	
	//This method add an edge to the edge list of the network; checks if it's not a duplicate before adding to the list
	//Accepts the names of the nodes as an argument (String); two node names must be provided
	//The name of the node can be found by using .returnName() method
	public String addEdge(String node_name1, String node_name2) {
		int duplicates = 0;
		
		String edge_name1 = node_name1 + "_" + node_name2; //Creates two versions of the name since both can represent the same node
		String edge_name2 = node_name2 + "_" + node_name1; //For example A_B and B_A edges are the same 
		
		Node node1 = new Node(node_name1);  //creating Node objects so that they can be used to create a new edge.
		Node node2 = new Node(node_name2);
		addNode(node_name1); //adds nodes to the node list
		addNode(node_name2);
		for (Iterator<Edge> item = edgeList.iterator(); item.hasNext();) {  //iteration over the edge list
			Edge edge = item.next();   //a single edge bound to a variable
			if(edge.returnName().equals(edge_name1) || edge.returnName().equals(edge_name2)) {  //checks if the argument edge name matches any edge name on the edge list
				duplicates = 1;  //if there is a match, the duplicate value is updated
			}
		}
		if (duplicates == 0) {  //if there was not name match, the duplicate value is not updated
			edgeList.add(new Edge(node1, node2)); //the new edge is added to the edge list
			return "The interaction/edge was added to the network";
		} else {
			return "The interaciton/edge already exist in the network!";
		}
	}
	
	//A method to create a network
	public void createNetwork(String filePath) { //void means it does not return anything
	//accepts file path as an argument

		BufferedReader input = null;
		
		try {
			input = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(filePath))); //open a file
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");   //check if the file can be opened
			
		}
		
		//###List iteration
		String line;
				
		try {
			    while ((line = input.readLine()) != null) {//one line at the time while there is a line with characters
			    	String[] elements = line.split("\t");//breaks the line into two elements/nodes; one lines represents an edge
			    	Node node1 = new Node(elements[0]); //declare nodes as new objects
			    	Node node2 = new Node(elements[1]);
			    	addNode(node1.returnName());  //add nodes to the node list/node array
			    	addNode(node2.returnName());
			    	addEdge(node1.returnName(), node2.returnName()); //add an edge to the edge list
				}
			    input.close(); //closes input; possibly saves some resources
		}
		catch(IOException e) {
			System.out.println("Reading error " + e);
		}
			System.out.println("Network was created successfully!");
	}

	//A method to add an additional interaction/edge to the already existing network
	public void addInteraction(String node1_name, String node2_name) {  //Accepts two node names (have to be strings) as arguments
			//The method checks if any of the nodes specified are not duplicates before adding them to the node list
			//then the edge is created from the two nodes, checked for duplication, and added to the edge list
		
			addNode(node1_name);  //uses Network class addNode method to add the node to the node list; the addNode method checks for duplicates
			addNode(node2_name);
			addEdge(node1_name, node2_name); //uses Network class addEdge method to add the edge to the edge list
	}
	
	//A method to remove interaction/edge from the already existing network
	public String removeInteraction(String node1_name, String node2_name) {
		
		ArrayList<Edge> hit_list = new ArrayList <Edge>();
		String edge_name1 = node1_name + "_" + node2_name; //Creates two versions of the name since both can represent the same node
		String edge_name2 = node2_name + "_" + node1_name; //For example A_B and B_A edges are the same 

		for (Iterator<Edge> it = edgeList.iterator(); it.hasNext(); ) {
		    Edge edge = it.next();
			if (edge.returnName().equals(edge_name1) || edge.returnName().equals(edge_name2)) {
				it.remove();
				hit_list.add(edge);
				break;
			} 
		}
		for (Iterator<Node> it = nodeList.iterator(); it.hasNext(); ) {
		    Node node = it.next();
		    int degree = findDegree(node.returnName());

			if ((node.returnName().equals(node1_name) || node.returnName().equals(node2_name)) && degree == 0) {
				it.remove();
				System.out.println("The node " + node.returnName() + " was removed from the network");
			}
		}
		if (hit_list.isEmpty()) {
			return "The interaction/edge does not exist in the network!";
		} else {
			return "The interaction/edge was removed from the network!";
		}
	}
	//####
	//STEP 3 NETWORK ANALYSIS
	//####
	
	//A method to find a degree/connectivity of the node
	public int findDegree(String node_name) {  //the method returns an integer value; accepts the name of the node as an argument (String)
		//This method counts an occurrence of an argument/node in each edge of the edge list; 
		
			int degree_counter = 0;
			
			for (Iterator<Edge> item = edgeList.iterator(); item.hasNext();) {  //iteration over the edge list
				Edge edgy = item.next();  //storing an edge from a list as a variable
				String[] elements = edgy.returnName().split("_");  //finds the name of the edge using returnName() method (see Edge class)
															   //Splits the name into to elements since the edge name is a combination of the node names
				if(node_name.equals(elements[0])) {  //checks if the name of the node provided matches to node name from the edge
					degree_counter ++;  //if there is a match the counter is updated
				}
				if(node_name.equals(elements[1])) {	//checks if the name of the node provided (argument) matches to node name from the edge
					degree_counter ++; //if there is a match the counter is updated
				}
			}
			return degree_counter;  //returns the degree of the node
	}
	
	//A method to find an average degree of the network
	public double averageDegree() {  //returns a double type
		//The method counts the total number of nodes and degrees and finds a mean average of the degree
		//The principle is very similar to ordinary mean calculation
		
		double degree_sum = 0;  //a variable for the sum of all node degree
		int total_nodes = 0;  //the total number of nodes
		double avrg_degree;  //average degree of the network
		
		for (Iterator<Node> item = nodeList.iterator(); item.hasNext();) {  //iteration over the node list
			Node node = item.next(); //stores the node
			String node_name = node.returnName();  //gets the name of the node using returnName() method (see Node class)
			degree_sum += findDegree(node_name);  //finds degree of the node and adds it to the degree_sum
			total_nodes ++; //updates node count by one
		}
		avrg_degree = degree_sum / total_nodes;  //calculates the average degree
		
		double roundOff = Math.round(avrg_degree * 100.0) / 100.0; //to makes things tidier the average degree value is rounded to two numbers after the decimal point
		return roundOff;  //returns the rounded value
	}

	//A method to find a hub or hubs (node with most edges) of the network
	public String findHub() { //returns type String
		//The method searches for the node with the highest degree, and the value is stored; (loop 1)
		//Then the method uses the degree value to search for how many nodes have exactly the same value (loop 2)
		
		//loop 1
		String node_names = "The biggest degree is "; //a string for the return value
		int maxdegree = 0;
		for (Iterator<Node> item = nodeList.iterator(); item.hasNext();) { //iteration over the node list
			Node node = item.next(); //stores the node as a variable
			int node_degree = findDegree(node.returnName()); //finds the name degree of the node
			if(node_degree > maxdegree) { //checks if node degree value from iteration is higher than the current stored max degree value
				maxdegree = node_degree; //if the node degree value is more than the store max degree value, the max degree value gets updated
			}
		}
		node_names += Integer.toString(maxdegree) + "; Nodes with the degree: ";  //adding the found highest degree of the node to the String variable; this latter will be returned at the end of the method
		//loop 2
		for (Iterator<Node> item = nodeList.iterator(); item.hasNext();) { //iteration over the node list
			Node node = item.next();  //stores the node as a variable
			int node_degree = findDegree(node.returnName()); //finds the degree of the node from the iterated list
			if (node_degree == maxdegree) {  	//checks if the maximum degree value is the same degree value for the node
				node_names += node.returnName() + " "; //if the node has the same value as max degree value, the name of the 
			}											// node is added to the string
		}
		return node_names; //this prints out the highest degree in the network and all the node that have it
	}
	
	//A method to find the degree distribution of the network
	public HashMap<Integer, Integer> degreeDis() {  		
		//A method prints out a table of the network degree distribution and returns HashMap
		//the first column contains the degree; the second column contains the number of nodes with that degree
		//The method uses 3 loops; 1 loop finds the degree of the node, 2 loop is within the 1 loop and searches for how many nodes have that degree
		//The values are stored in HashMap; the 3 loop iterates the HashMap and prints out the keys and values
		
		HashMap<Integer, Integer> distributionList = new HashMap<Integer, Integer>(); //declaring an empty hashmap
		
		//loop 1
		//finding the degree
		for (Iterator<Node> item = nodeList.iterator(); item.hasNext();) { //iteration over node list
			Node node = item.next(); //storing node 
			int degree = findDegree(node.returnName()); //finding the degree of the node
			int counter = 0;  //a counter for counting the occurrence of the degree on the node list
			
			//loop 2
			//counting degree occurrence
			for (Iterator<Node> item1 = nodeList.iterator(); item1.hasNext();) { //iteration over node list
				Node node1 = item1.next(); //storing node
				int degreeofNode = findDegree(node1.returnName()); //finding the degree of the node
				if (degreeofNode == degree) {   //checking if the degree of node from the loop 1 matches the one from this loop
					counter += 1; //if there is a match the counter is updated by 1
				}
			}
		distributionList.put(degree, counter); //updating HashMap with a new key and a new value; duplicate keys are not allowed in the HashMap
		}
		//loop 3
		for (Map.Entry<Integer, Integer> entry : distributionList.entrySet()) { //iteration over the HashMap
		    int key = entry.getKey(); //degree
		    int value = entry.getValue(); //occurrence
		    
			System.out.print(key + " "); //prints out the values 
			System.out.println(value);
		}
		return distributionList;
	}
	
	//Save degree distribution to an output file
	public void saveDD_output(String file, Label txt1) {
	//This method accepts a file path and a label; Label is used in control class to provide information about the status
	   	BufferedWriter output = null;

        try {
      	  output = new BufferedWriter(  //creates the output file and checks for an error
      		    new OutputStreamWriter(
      		      new FileOutputStream(file))); //file path input
      		  txt1.setText("The file was successfully created");
      		}
      		catch(FileNotFoundException e) {
      		  System.out.println("File not created");
      		  txt1.setText("File not created");
      		}
        
    	try {
    		
    		HashMap<Integer, Integer> disList = degreeDis(); //calculating degree distribution of the network
    		output.write("Degree : Occurrence"); //writes to a file
    		output.newLine(); //writes an empty line

    			for (Map.Entry<Integer, Integer> entry : disList.entrySet()) { //iteration over the HashMap
    			    int key = entry.getKey(); //degree
    			    int value = entry.getValue(); //occurrence
    			    String one_line;
    			    one_line = Integer.toString(key) + " : " + Integer.toString(value);
    			    output.write(one_line);   //writing to a file
    			    output.newLine();
  		}
  		  output.close();
  		  System.out.println("The output writing to the file was successfull");
  		  txt1.setText("The output writing to the file was successfull");  //Label message for GUI
  		}
  		catch(IOException e) {
  		  System.out.println("Writing error " + e);
  		  txt1.setText("Writing error"); //Label message for GUI
  		}
	}
	
	//A method to save output file with updated interactions
	public void save_network(String file, Label txt1) {
	//This method saves a new network to an output file. The principle is very similar a method above for creating a file with degree distribution
		//Accepts file path and label as arguments
		BufferedWriter output = null;

        try {
      	  output = new BufferedWriter(  //creates the output file and checks for an error
      		    new OutputStreamWriter(
      		      new FileOutputStream(file))); //file path
      		  txt1.setText("The file was successfully created");
      		}
      		catch(FileNotFoundException e) {
      		  System.out.println("File not created");
      		  txt1.setText("File not created");
      		}
        
    	try {
    		for (Iterator<Edge> item = edgeList.iterator(); item.hasNext();) {  //iteration over the edge list
    			Edge edge = item.next();   //a single edge bound to a variable
    			String[] elements = edge.returnName().split("_"); //edges in edgeList are separated by "_"
    			String interaction = elements[0] + "\t" + elements[1]; //adding a space between edges, so that output matches input
    			output.write(interaction);   //writing to a file
    			output.newLine(); //writing a new line; we don't want all information on one line
  		}
  		  output.close();
  		  System.out.println("The output writing to the file was successful");
  		  txt1.setText("The output writting to the file was successful"); //GUI status message
  		}
  		catch(IOException e) {
  		  System.out.println("Writing error " + e);
  		  txt1.setText("Writing error"); //GUI status message
  		}
	}
	
	//A method to count the number of nodes on node list
	public int countNode() { 
		//A basic iteration over the list and counting of elements
		int counter = 0;
		for (Iterator<Node> item = nodeList.iterator(); item.hasNext();) { //iteration over the list of nodes
			Node node = item.next(); //the variable is not used but with out it the application crashes
			counter += 1;
		}
		return counter;
	}
	
	//A method to count the number of edges on edge list
	public int countEdge() {
		//Please see countNode()
		int counter = 0;
		for (Iterator<Edge> item = edgeList.iterator(); item.hasNext();) { //iteration
			Edge edge = item.next(); //the variable is not used but with out it the application crashes

			counter += 1;
		}
		return counter;
	}
 }
