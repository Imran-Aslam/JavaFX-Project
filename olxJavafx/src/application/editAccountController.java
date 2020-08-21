package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class editAccountController implements Initializable {
	
	Parent root;
	private String gender;
	public static ResultSet info;
	@FXML	private RadioButton male;
    @FXML	private RadioButton female;
    @FXML	private TextField name;
    @FXML	private TextField lastname;
    @FXML	private TextField password;
    @FXML	private TextField address;
    @FXML	private TextField mobile;
    @FXML	private TextField city;
    @FXML	private TextField username;
    
    ToggleGroup tg = new ToggleGroup();

    @FXML
    void cancel(ActionEvent event) {
    	
    	loadUI("dashboard");
    }
    @FXML
    void ok(ActionEvent event) {
    	
    	RadioButton tbtn = (RadioButton) tg.getSelectedToggle();
		if( (name.getText().trim().isEmpty() || lastname.getText().trim().isEmpty() || password.getText().trim().isEmpty()
				|| mobile.getText().trim().isEmpty() || address.getText().trim().isEmpty() ) == false)
		{
			int response = JOptionPane.showConfirmDialog(null," Do You Want to Continue ? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(response == JOptionPane.YES_OPTION)
			{
				//Call the database method 
				recommendationController.db2.updateData(loginController.userID,name.getText(),lastname.getText(),tbtn.getText(),mobile.getText(),address.getText(),password.getText());
				loadUI("dashboard");
			}
		}else
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Field is Empty !!!");
		}

    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		male.setToggleGroup(tg);    //For radio Button
		female.setToggleGroup(tg);
		try {					//Here set the Logged-in user data in text fields after accessing data from database
			while(info.next())  //Here 'info' resultSet, we already initialize in dashboard controller in initialize method 
			{
				name.setText(info.getString(2));
				lastname.setText(info.getString(3));
				gender = info.getString(4);
				mobile.setText(info.getString(5));
				address.setText(info.getString(6));
				username.setText(info.getString(7));
				password.setText(info.getString(8));
				city.setText(info.getString(11));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (gender.equals("Male"))   //Selected Radio Button according database gender value
		{
			male.selectedProperty().setValue(true);
		}else
		{
			female.selectedProperty().setValue(true);
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
