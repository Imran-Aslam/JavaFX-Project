package application;

import java.io.File;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class addpostController implements Initializable {

	Parent root;
	String imgPath;
	ToggleGroup tg = new ToggleGroup();
	
	ObservableList<String> cat_list = FXCollections.observableArrayList("Mobiles","Vehicles","Electronics and Home Appliances","Propert for Sale");
	ObservableList<String> list = FXCollections.observableArrayList("Islamabad","Lahore","Faisalabad","Rawalpindi","Multan"); 

	@FXML	private TextField postName;
    @FXML	private TextArea description;
    @FXML	private TextField price;
    @FXML	private ComboBox<String> city;
    @FXML	private ComboBox<String> category;
    @FXML	private ComboBox<String> subcategory;
    @FXML	private TextField address;
    @FXML	private ImageView pic;
    @FXML	private TextField picPath;
    @FXML	private RadioButton newpost;
    @FXML	private RadioButton used;
    @FXML
    void cancel(ActionEvent event) {
    	loadUI("dashboard");
    }

    @FXML
    void ok(ActionEvent event) {
    		
    	RadioButton tbtn = (RadioButton) tg.getSelectedToggle();
    	if( (postName.getText().isEmpty() || description.getText().isEmpty() || price.getText().isEmpty() ||  
    			category.getSelectionModel().getSelectedItem().isEmpty() || subcategory.getSelectionModel().getSelectedItem().isEmpty()
    			|| address.getText().isEmpty() || picPath.getText().isEmpty() ) == false) {
    		
    
    		recommendationController.db2.addPost(loginController.userID,postName.getText(), description.getText(), category.getSelectionModel().getSelectedItem(),
    				subcategory.getSelectionModel().getSelectedItem(),
    				Integer.parseInt(price.getText()), city.getSelectionModel().getSelectedItem(), address.getText(), tbtn.getText(), imgPath);
    		loadUI("dashboard");
    	}else
    	{
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Field is Empty !!!");
			alert.show();
    	}
    	
    }

    @FXML
    void uploadImage(ActionEvent event) {
    	
    	FileChooser file = new FileChooser();
		file.getExtensionFilters().add(new ExtensionFilter("Images","*.png","*.jpg"));
		File f = file.showOpenDialog(null);
		imgPath = f.getAbsolutePath();
		File fi = new File(imgPath);
		picPath.setText(imgPath);
		pic.setImage(new Image(fi.toURI().toString()));
    }
    
    @FXML
    void categoryAction(ActionEvent event) {
    	
    	if(category.getSelectionModel().getSelectedItem().equalsIgnoreCase("Mobiles"))
    		subcategory.getItems().addAll("Mobile Phones","Tablets");
    	if(category.getSelectionModel().getSelectedItem().equalsIgnoreCase("Vehicles"))
    		subcategory.getItems().addAll("Bikes","Cars");
    	if(category.getSelectionModel().getSelectedItem().equalsIgnoreCase("Electronics and Home Appliances"))
    		subcategory.getItems().addAll("Laptops","TV-Audio-Video","Generator","AC and Coolers");
    	if(category.getSelectionModel().getSelectedItem().equalsIgnoreCase("Property for Sale"))
    		subcategory.getItems().addAll("Houses","Plots and Lands","Flats and Apartments");
    }
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		category.setItems(cat_list);
		newpost.setToggleGroup(tg);
		used.setToggleGroup(tg);
		city.setItems(list);
		
	}
	//For load UI
	public void loadUI(String ui)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/"+ui+".fxml"));
			root = loader.load();
			dashboardController dc = loader.getController();
			dc.name.setText(loggedinController.name);
		}catch (Exception e)
		{
			System.out.println("e.getMessage()");
			e.printStackTrace();
		}
		controllerClass.borderpane.setCenter(root);
	}

}
