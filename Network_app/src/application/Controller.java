package application;
//Controller class contains instructions for GUI elements.

import javafx.fxml.FXML;
import java.io.*;
import java.util.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller {
	
	Network netwrk = new Network(); //empty network
	
	//Declaring the components of GUI: buttons, text fields, labels, ...
	@FXML
	private Button btn1; //create network
	
	@FXML
	private Button btn2; //Add interaction
	
	@FXML
	private Button btn3; //Average degree
	
	@FXML
	private Button btn4; //Find hub(s)
	
	@FXML
	private Button btn5; //Find the degree of the node
	
	@FXML
	private Button btn6; //Clear network
	
	@FXML
	private Button btn7; //Degree distribution
	
	@FXML
	private Button btn8; // Save degree distribution to a file
	
	@FXML
	private MenuItem btn9; // Save updated network distribution to a file
	
	@FXML
	private Button btn10; // Remove interaction/edge from the network

	@FXML
	private ListView<String> listview; //Displays a list of all interactions
		
	@FXML
	private TextField txtField1; //add interaction text field
	
	@FXML
	private TextField txtField2;//add interaction text field
	
	@FXML
	private TextField txtField3;//Find average degree text field
	
	@FXML
	private TextField txtField4; //Find hub text field
	
	@FXML
	private TextField txtField5; //Find degree text field
	
	@FXML
	private TextField txtField6; //Find degree text field
	
	@FXML
	private Label txt2; //Text for number of nodes
	
	@FXML
	private Label txt3; //Text for number of edges
	
	@FXML
	private ListView<String> listview_dis; //List view for degree distribution
	
	@FXML
	private Label txt1; //Label for status updates (bottom left)




	//btn1
	//Button1 action is responsible for opening a File chooser window
	//getting a file path; creating a network 
	//Showing interaction on list view window
	//updating the bottom status label
	public void Button1Action(ActionEvent event) {
		FileChooser fc = new FileChooser();  //opens a file chooser
		fc.setTitle("Select a .txt file containing a network"); //name of the window
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Text Files", "*.txt")); //Only .txt files are allowed
		File selectedFile = fc.showOpenDialog(null); //storing the selected file
		
		String file_path = selectedFile.getAbsolutePath(); //getting a path of the file
		netwrk.createNetwork(file_path);  //creating a network; this method also deals with opening of a file
		
		if (selectedFile != null) { //checks if the file is not empty
			netwrk.edgeList.forEach((Edge) -> { //if the file has a network on the edge list will be iterated
				listview.getItems().add(Edge.returnName()); //updating list view window with interactions from a file
				});
			txt1.setText("The Network was created!");  //updating status label about the success of a method
		}
		String node_count = Integer.toString(netwrk.countNode());  //counts the number of nodes in the current network
																	// and converts integer to a string
		String edge_count = Integer.toString(netwrk.countEdge()); //same here but with edges
		txt2.setText(node_count);  //updates a label to show the number of nodes
		txt3.setText(edge_count);  //updates a label to show the number of edges/interactions
		/*else {
			txt1.setText("Failed to create a network! Check the format and type of the file");
		}*/
	}
	//Add interaction button
	//This button allows to add an interaction
	public void Button2Action(ActionEvent event) {  
			String node_name1 = txtField1.getText();  //getting String values from both text fields of GUI
			String node_name2 = txtField2.getText();
			
			if (!node_name2.equals("")  && !node_name1.equals("")) { //if both text fields were filled
				String a = netwrk.addEdge(node_name1, node_name2);  //add edge to the existing network
				txt1.setText(a); //update the status label about the success of the method
			}
			else {
				txt1.setText("Please enter both node names to create an interaction!"); //if even one field is empty, show  an error message
			}																			// on the status label (bottom left)
			
			String node_count = Integer.toString(netwrk.countNode());  //Updating the count values for node and edge
			String edge_count = Integer.toString(netwrk.countEdge());	//Since they should have changed after adding a new interaction
			txt2.setText(node_count);  //
			txt3.setText(edge_count);
			
			listview.getItems().clear();	//clear list view of all interactions
			netwrk.edgeList.forEach((Edge) -> {   //Updating a list of interactions on list view (centre left)
				listview.getItems().add(Edge.returnName());  //in a way works as a refresh
				});
		}
	
	//Average degree button
	//A Method for a button to find an average degree of the network
	public void Button3Action(ActionEvent event) {
		double a = netwrk.averageDegree();  //finds and average degree, and stores it in a
		txtField3.setText(Double.toString(a));  //a is converted to a string and displayed in the text field
		txtField3.setEditable(false);  //This prevents text field from being edited

	}
	//Find hub button
	//A method for a button to find a hub of the network
	public void Button4Action(ActionEvent event) {
		String a = netwrk.findHub();  //finds a hub(s), stores it in a string
		txtField4.clear();  //clears previous message
		txtField4.setText(a); //set text to a text field
		txtField4.setEditable(false); //prevents editing of a text field
	}
	//Find the degree of a node button
	//Enter the name of node; click the button; degree will be found
	public void Button5Action(ActionEvent event) {
		String node_name = txtField5.getText(); //getting the name of a node from a text field
		int degree = netwrk.findDegree(node_name); //finding a degree
		txtField6.setText(Integer.toString(degree)); //converting degree to string and setting to a text field
		txtField6.setEditable(false); //preventing editing of a text field
	}
	//Clearing the selection/network button
	//This button clear the network selection and permits a new session of analysis to be performed without the need to close the program
	public void Button6Action(ActionEvent event) {
		netwrk = new Network();  //creates an empty network (this removes a full network if there was one)
		txt2.setText(""); //clear text field for node count
		txt3.setText(""); //clear text field for edge count
		txtField1.clear();
		txtField2.clear();
		txtField3.clear();
		txtField4.clear();
		txtField5.clear();
		txtField6.clear();

		listview_dis.getItems().clear();  //clear degree distribution list view
		listview.getItems().clear();	//clear list view of all interactions
		txt1.setText("The Network was cleared!"); //updating the status label
	}
	
	//Degree distribution button
	//Finds degree distribution of the network and prints it out on the right-side list view
	public void Button7Action(ActionEvent event) {
		listview_dis.getItems().clear();  //clears the list view
		HashMap<Integer, Integer> disList = netwrk.degreeDis(); //finds degree distribution and stores it as a HashMap
		listview_dis.getItems().add("Degree : Occurrence");  //writes a line to a list view (these are for column names)

			for (Map.Entry<Integer, Integer> entry : disList.entrySet()) { //iteration over the HashMap
			    int key = entry.getKey(); //degree
			    int value = entry.getValue(); //occurrence
			    String one_line;
			    one_line = Integer.toString(key) + " : " + Integer.toString(value);  //combining degree and occurrence into one String value
				listview_dis.getItems().add(one_line); //updated list view with a new value "one_line" (degree and its occurrence)
			}
	}
	//Save degree distribution to a file
	//This button allows to save the degree distribution list in a chosen file and directory
	public void Button8Action(ActionEvent event) {
        FileChooser fileChooser = new FileChooser(); //opens file chooser to select file and directory
        fileChooser.setTitle("Save Degree Distribution"); //File chooser window name
        File file = fileChooser.showSaveDialog(null); //storing selection to a variable file
        String path = file.getAbsolutePath(); //getting the file's path 
   
        netwrk.saveDD_output(path, txt1); //a network class method to write the list to the file
     }
	
    //Save network to a file
	//This menu item button allows to save the network in a chosen file and directory
    public void MenuItem9Action(ActionEvent event) {
    	
        FileChooser fileChooser = new FileChooser();  //Please see Button8Action as it follows exactly the same principle
        fileChooser.setTitle("Save Degree Distribution");
        File file = fileChooser.showSaveDialog(null);
        String path = file.getAbsolutePath();
        	netwrk.save_network(path, txt1);
	}
    
	public void Button10Action(ActionEvent event) {  
		String node_name1 = txtField1.getText();  //getting String values from both text fields of GUI
		String node_name2 = txtField2.getText();
		
		if (!node_name2.equals("")  && !node_name1.equals("")) { //if both text fields were filled
			String a = netwrk.removeInteraction(node_name1, node_name2);  //remove edge to the existing network
			txt1.setText(a); //update the status label about the success of the method
		}
		else {
			txt1.setText("Please enter both node names to delete an interaction!"); //if even one field is empty, show  an error message
		}																			// on the status label (bottom left)
		
		String node_count = Integer.toString(netwrk.countNode());  //Updating the count values for node and edge
		String edge_count = Integer.toString(netwrk.countEdge());	//Since they should have changed after adding a new interaction
		txt2.setText(node_count);  //
		txt3.setText(edge_count);
		
		listview.getItems().clear();	//clear list view of all interactions
		netwrk.edgeList.forEach((Edge) -> {   //Updating a list of interactions on list view (centre left)
			listview.getItems().add(Edge.returnName());  //in a way works as a refresh
			});
	}

   /* public void Popup10Action(ActionEvent event) { //A failed attempt with a pop up window
    	txtField7.setText("The Application was written and created by Aretas Gaspariunas. 2017 R");
    }*/
}
