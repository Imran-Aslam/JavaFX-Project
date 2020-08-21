package application;

import java.net.URL;

import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class signupController implements Initializable {
	
		Parent root;
	 	@FXML	private ComboBox<String> city;
	    @FXML	private RadioButton male;
	    @FXML	private RadioButton female;
	    @FXML	private Button submit;
	    @FXML	private Button cancel;
	    @FXML	private TextField firstname;
	    @FXML	private TextField lastname;
	    @FXML	private TextField username;
	    @FXML	private TextField password;
	    @FXML	private TextField confirmpassword;
	    @FXML	private TextField address;
	    @FXML	private TextField mobileNo;
	 // create a toggle group
		ToggleGroup tg = new ToggleGroup();
		
		ObservableList<String> list = FXCollections.observableArrayList("Islamabad","Lahore","Faisalabad","Rawalpindi","Multan"); 
		
		@FXML
		private void checkUsername(MouseEvent event)
		{
			recommendationController.db2.checkUsername(username.getText());
		}
		@FXML
		private void submit(ActionEvent event)
		{
			RadioButton tbtn = (RadioButton) tg.getSelectedToggle();
			if( (firstname.getText().trim().isEmpty() || lastname.getText().trim().isEmpty() || username.getText().trim().isEmpty()
					|| password.getText().trim().isEmpty() || confirmpassword.getText().trim().isEmpty() || mobileNo.getText().trim().isEmpty() ||
					address.getText().trim().isEmpty() ) == false )
			{
				int response = JOptionPane.showConfirmDialog(null," Do You Want to Continue ? ","Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(response == JOptionPane.YES_OPTION)
				{
					recommendationController.db2.addUser(firstname.getText(), lastname.getText(),tbtn.getText(),mobileNo.getText(),address.getText(),
							username.getText(),password.getText(),city.getSelectionModel().getSelectedItem());
					loadUI("front");
				}
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
		private void cancel(ActionEvent event)
		{
			loadUI("front");
		}
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			male.setToggleGroup(tg);
			female.setToggleGroup(tg);
			city.setItems(list);
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
			Scene scene = new Scene(root);
				Main.stage.setScene(scene);
		}
		
}
