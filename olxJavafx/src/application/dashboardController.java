package application;

import java.net.URL;

import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class dashboardController implements Initializable {
	
	Parent root;
	ResultSet rs;
	@FXML	public Label name;
	
	@FXML
	public void addpost(ActionEvent event)
	{
		loadUI("addPost");
	}
	@FXML
	public void accountinfo(ActionEvent event)
	{
		loadUI("info");
	}
	@FXML
	public void editinfo(ActionEvent event)
	{	
		loadUI("editAccount");
	}
	@FXML
	void mypost(ActionEvent event) {
		loadUI("mypost");
	}
	@FXML
	public void signout(ActionEvent event)
	{
		controllerClass.borderpane.setTop(controllerClass.borderpane1);
		loadUI("front");
	}
	
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
		if(ui.equals("front"))
		{
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		}else
			controllerClass.borderpane.setCenter(root);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		name.setText(loggedinController.name);
		rs = recommendationController.db2.accessUserInformation(loginController.userName);
		infoController.data = rs;
		editAccountController.info = rs; }    }
