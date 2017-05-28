package application;
//String variable containing the name of the node
//default constructor that creates a node with an empty name
//constructor with a String argument that creates a node with the given name
//The class should also have a method that returns the name of the node.
public class Node {
	public String name;  //this variable represents the name of the Node class
	
	public Node () {   //default constructor
		this.name = "";
	}
	public Node (String n) {  //Constructor with a String argument for a name
		this.name = n;
	}
	public String returnName() {  //A method to return the name of the node
		return name;
	}
}
