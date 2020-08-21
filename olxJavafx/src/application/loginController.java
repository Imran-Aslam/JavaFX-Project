package application;

import java.net.URL;


import java.sql.ResultSet;

import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class loginController implements Initializable {
	
	Parent root;
	public static int userID;
	public static String userName;
	String name;
	ResultSet rs;
	@FXML	private TextField username;
    @FXML	private Label usernameErrorField;
    @FXML	private Label passwordErrorField;
    @FXML	private PasswordField password;

	
	@FXML
	public void login(ActionEvent event)
	{
		if(username.getText().trim().isEmpty() && password.getText().trim().isEmpty())
		{
			usernameErrorField.setText("Username is Empty.");
			passwordErrorField.setText("Password is Empty.");
		}else if(username.getText().trim().isEmpty())
		{
			usernameErrorField.setText("Username is Empty.");
		}else if(password.getText().trim().isEmpty())
		{
			passwordErrorField.setText("Password is Empty.");
		}else if((username.getText().trim().isEmpty() || password.getText().trim().isEmpty())== false)
		{
			String pass = password.getText();
			rs = recommendationController.db2.accessUserInformation(username.getText());//Access the user information from the database
			try {
					if( rs.next())   // if there is row in rs means username correct
					{
						if(pass.equals(rs.getString(8)))   //Here check entered password with database saved passowrd
						{
							userID = rs.getInt(1);
							name = rs.getString(2);
							userName = rs.getString(7);	
							loadUI("logged");          //Change the upper pane where user go on your dashboad
							loadUI2("recommendation");
						}else{
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText(null);
								alert.setContentText("Your Password is not Correct !!!");	
								alert.show();
						}
					}
					else{
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText(null);
							alert.setContentText("You are not registered yet !!! !");	
							alert.show();
						}
		} catch (Exception e) {        //SQLException
			e.printStackTrace();
			}
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
			loggedinController.name = name;
			controllerClass.borderpane1.setTop(root);
			controllerClass.borderpane1.setBottom(controllerClass.bottomPic);
		}
		//For load UI
				public void loadUI2(String ui)
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


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

		}

}
