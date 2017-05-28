package application;
//Should have a constructor accepting two Node arguments that creates an edge connecting two nodes.
public class Edge {
	
	public Node node1;  //the Edge class contains two nodes and a name
	public Node node2;
	public String edgeName;  //name of the edge
	
	public Edge (Node a, Node b) { //A constructor to create an edge connecting two nodes. Accepts two nodes as arguments
		this.node1 = a;
		this.node2 = b;
		this.edgeName = a.returnName() + "_" + b.returnName(); //creates the Edge name by combining the names of the nodes
	}
	public String returnName() {  //A method to return the name of the edge
		return edgeName;
	}
}
