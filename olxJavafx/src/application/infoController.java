package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

public class infoController implements Initializable {
	
	Parent root;
	public static ResultSet data;
	@FXML	public TextField firstname;
    @FXML	public TextField lastname;
    @FXML	public TextField username;
    @FXML	public TextField password;
    @FXML	public TextField address;
    @FXML	public TextField mobile;
    @FXML	public TextField city;
    @FXML	public TextField gender;
    @FXML	public TextField post;


	@FXML
    void back(ActionEvent event) {
    	loadUI("dashboard");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {                         //Here set the Logged-in user data in text fields after accessing data from database
			while(data.next())    //Here 'data' resultSet, we already initialize in dashboard controller in initialize method 
			{
				firstname.setText(data.getString(2));
				lastname.setText(data.getString(3));
				gender.setText(data.getString(4));
				mobile.setText(data.getString(5));
				address.setText(data.getString(6));
				username.setText(data.getString(7));
				password.setText(data.getString(8));
				city.setText(data.getString(11));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				controllerClass.borderpane.setCenter(root);
			}
}
