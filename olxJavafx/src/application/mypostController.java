package application;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class mypostController implements Initializable {

	Parent root;
	ResultSet posts;
	ResultSet rs;
	ImageView emp;
	@FXML	private TableView<tableModel> table;
	@FXML   private TableColumn<tableModel, String> col_id;
	@FXML	private TableColumn<tableModel, String> col_price;
    @FXML	private TableColumn<tableModel, String> col_name;
    @FXML	private TableColumn<tableModel, String> col_image;
    
    private final ObservableList<tableModel> data = FXCollections.observableArrayList();
	@FXML
    void delete(ActionEvent event) {    //Delete the post when user click the post then left click the mouse choose the delete option 
		  int i = table.getSelectionModel().getSelectedItems().get(0).getID();
		  System.out.println(i);
		recommendationController.db2.deletePost(i);
		  loadUI("mypost");
    }
	@FXML
    void open(ActionEvent event) {    //Open the post when user click the post then left click the mouse choose the delete option
		int i = table.getSelectionModel().getSelectedItems().get(0).getID();
		  rs = recommendationController.db2.showOnePost(i);
		  loadUI("post",rs);
    }
	@FXML
    void back(ActionEvent event) {
		  loadUI("dashboard");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
      col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
      col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
      col_image.setCellValueFactory(new PropertyValueFactory<>("photo"));
      
      posts = recommendationController.db2.showMyPosts(loginController.userID);   //Get logged-in user post from the database by calling database class method 'showmypost()'
      try {                      //Then here those post set on the table
    	  	while(posts.next())      
    	  	{
    	  		Blob blob;
    	  		try {
    	  				blob = posts.getBlob(4);
    	  				byte[] data2 = blob.getBytes(1,(int)blob.length());
    	  				emp = new ImageView(new Image(new ByteArrayInputStream(data2)));
    	  			} catch (SQLException e1) {

    	  				e1.printStackTrace();
    	  			}
    	  		try {
    	  				data.add(new tableModel(posts.getInt(1),posts.getInt(3),posts.getString(2),emp));
    	  			} catch (SQLException e) {

    	  				System.out.println("Error..............................");
    	  				e.printStackTrace();
    	  			}
    	  	}
      	} catch (SQLException e) {
      			e.printStackTrace();
      		}
      		table.setItems(data);
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
	//For load  category UI
			public void loadUI(String ui,ResultSet rs)
			{
				try
				{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/"+ui+".fxml"));
					root = loader.load();
					postController pc =loader.getController();
					pc.back = "mypost";
					try {                                         //This Represent the specific post data
							if(rs.next())
							{
								int sellerUserID = rs.getInt(1);
								pc.postname.setText(rs.getString(2));
								pc.description.setText(rs.getString(3));
								pc.price.setText(""+rs.getInt(4));
								pc.address.setText(rs.getString(6));
								pc.date.setText(rs.getString(7));
								Blob blob2 = rs.getBlob(8);
								byte[] data2 = blob2.getBytes(1,(int)blob2.length());
								pc.image.setImage(new Image(new ByteArrayInputStream(data2)));
								ResultSet sellerData = recommendationController.db2.accessSeller(sellerUserID);
								if(sellerData.next())
								{
									pc.sellerName.setText(sellerData.getString(1));
									pc.sellerPhone.setText(sellerData.getString(2));
								}
							}
					}catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}catch (Exception e)
				{
					System.out.println("e.getMessage()");
					e.printStackTrace();
				}
//				controllerClass.borderpane1.setBottom(null);
				controllerClass.borderpane.setCenter(root);
			}
}
