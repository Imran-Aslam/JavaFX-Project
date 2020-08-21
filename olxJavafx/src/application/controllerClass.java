package application;

import java.net.URL;



import java.util.ResourceBundle;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class controllerClass implements Initializable {

	@FXML
	public BorderPane borderPane;
	
	public static BorderPane borderpane;
	public static BorderPane borderpane1;
	public static Label bottomPic;
	@FXML
	public BorderPane borderpane2;
	@FXML
    private Label labelImage;

	@FXML	private Menu mobile;
    @FXML	private Menu car;
    @FXML	private Menu house;
    @FXML	private Menu home;
    @FXML	private MenuItem mobiles;
    @FXML	private MenuItem tablets;
    @FXML	private MenuItem laptops;
    @FXML	private MenuItem tv_audio;
    @FXML	private MenuItem generator;
    @FXML	private MenuItem acCoolers;
    @FXML	private MenuItem bikes;
    @FXML	private MenuItem cars;
    @FXML	private MenuItem houses;
    @FXML	private MenuItem plots;
    @FXML	private MenuItem flats;



	@FXML
	 Button login,signup;
	Parent root;
	Pane view;
	public static String cate;
	@FXML
	public void login(ActionEvent event)
	{
		borderpane2.setBottom(null);
		loadUI("login");
	}
	@FXML
	public void signup(ActionEvent event)
	{
		borderpane2.setBottom(null);
		loadUI("signup");
	}
	@FXML
	private void menu(MouseEvent event)
	{
			loadUI("recommendation");
			borderpane2.setBottom(labelImage);
	}
	@FXML
	private void menu2(MouseEvent event)
	{
			borderpane2.setBottom(null);
			menuloadUI("category","Mobiles");
	}
	@FXML
	private void menu3(MouseEvent event)
	{
			borderpane2.setBottom(null);
			menuloadUI("category","Cars");
	}
	@FXML
	private void menu4(MouseEvent event)
	{
			borderpane2.setBottom(null);	
			menuloadUI("category","Houses");
	}
	@FXML
	public void category(ActionEvent event)
	{
		borderpane2.setBottom(null);
		if(event.getSource() == home)
			menuloadUI("category","home");
		if(event.getSource() == mobiles)
			menuloadUI("category","Mobiles");
		if(event.getSource() == tablets)
			menuloadUI("category","Tablets");
		if(event.getSource() == laptops)
			menuloadUI("category","Laptops");
		if(event.getSource() == tv_audio)
			menuloadUI("category","TV-Audio-Video");
		if(event.getSource() == generator)
			menuloadUI("category","Generator");
		if(event.getSource() == acCoolers)
			menuloadUI("category","AC and Coolers");
		if(event.getSource() == bikes)
			menuloadUI("category","Bikes");
		if(event.getSource() == cars)
			menuloadUI("category","Cars"); 
		if(event.getSource() == houses)
			menuloadUI("category","Houses");
		if(event.getSource() == plots)
			menuloadUI("category","Plots and Lands");
		if(event.getSource() == flats)
			menuloadUI("category","Flats and Apartments");
	}
	//This function is used to load the category layout when user click on menu item
	public void menuloadUI(String ui,String cate_name)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/"+ui+".fxml"));
			categoryController.cat = cate_name;         //Here set category name so that only show the specific category post in category layout
			root = loader.load();
			categoryController cc = loader.getController();
			cc.cat(cate_name);							//Set the category name on the top of category layout
		}catch (Exception e)
		{
			System.out.println("e.getMessage()");
			e.printStackTrace(); }
		borderPane.setCenter(root); }
	//For load UI
	public void loadUI(String ui)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/"+ui+".fxml"));
			root = loader.load();
		}catch (Exception e)
		{
			System.out.println("e.getMessage()");
			e.printStackTrace();
		}
			borderpane.setCenter(root);
	}
	  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		borderpane = borderPane;
		borderpane1 = borderpane2;
		bottomPic = labelImage;
		loadUI("recommendation");
		
	}
}
