package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class postController implements Initializable{

	Parent root;
	String back;
	String cat;
	@FXML	public ImageView image;
    @FXML	public TextArea description;
    @FXML	public Label price;
    @FXML	public Label postname;
    @FXML	public Label date;
    @FXML	public Label address;
    @FXML	public Label sellerName;
    @FXML	public Label sellerPhone;

    @FXML
    void back(ActionEvent event) {
    	if(back.equals("recommendation"))     //if user click on any post from main page then when user click on back button then back on the page on the main page
    		loadUI("front");
    	else
    		loadUI(back);
    }
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	//For load UI
	public void loadUI(String ui)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/"+ui+".fxml"));
			root = loader.load();
			categoryController cc = loader.getController();
			cc.cat_field.setText(cat);;
		}catch (Exception e)
		{
//			System.out.println("e.getMessage()");
//			e.printStackTrace();
		}
		if(ui.equals("front"))
		{
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		}else
			controllerClass.borderpane.setCenter(root);
	}

}
