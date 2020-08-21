package application;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class loggedinController implements Initializable {

	Parent root;
	public static String name;

	@FXML
	public void dash(ActionEvent event)
	{
		controllerClass.borderpane1.setBottom(null);
		loadUI("dashboard");
	}
	
	
	//For load UI
	public void loadUI(String ui)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/"+ui+".fxml"));
			root = loader.load();
			dashboardController dc = loader.getController();
			dc.name.setText(name);
		}catch (Exception e)
		{
			System.out.println("e.getMessage()");
			e.printStackTrace();
		}
		controllerClass.borderpane.setCenter(root);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	

}
