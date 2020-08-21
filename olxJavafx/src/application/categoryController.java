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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class categoryController implements Initializable{

	 @FXML	public Label cat_field;

	 @FXML	private TextField min;
	 @FXML	private TextField max;
	 @FXML	private Label minErrorField;
	 @FXML	private Label maxErrorField;
	 
	 	public static String cat;
		private Parent root;
		private ImageView emp;
		public ResultSet rs,posts;
		public int sellerUserID;
		@FXML	private TableView<tableModel> table;
		@FXML   private TableColumn<tableModel, String> col_id;
		@FXML	private TableColumn<tableModel, String> col_price;
	    @FXML	private TableColumn<tableModel, String> col_name;
	    @FXML	private TableColumn<tableModel, String> col_image;
	    
	    private final ObservableList<tableModel> data = FXCollections.observableArrayList();
		
		 @FXML
		  private void selectedItem(MouseEvent event)
		  {
			  int i = table.getSelectionModel().getSelectedItems().get(0).getID();
			  rs = recommendationController.db2.showOnePost(i);
			  loadUI("post",rs);
		  }
		 @FXML
		 void go(ActionEvent event) {
			
			 if(min.getText().trim().isEmpty() && max.getText().trim().isEmpty())
			 {
				 maxErrorField.setText("Add Max Price");
				 minErrorField.setText("Add Min Price");
			 }
			 else if(min.getText().trim().isEmpty())
			 {
				 minErrorField.setText("Add Min Price");
			 }
			 else if(max.getText().trim().isEmpty())
			 {
				 maxErrorField.setText("Add Max Price");
			 }
			 else if( (min.getText().trim().isEmpty() || max.getText().trim().isEmpty()) == false)
			 { 
				 posts = recommendationController.db2.filterPosts(controllerClass.cate, Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));
				 try {
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
		 }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

			col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
	      col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
	      col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
//			col_image.setPrefWidth(80); 
	      col_image.setCellValueFactory(new PropertyValueFactory<>("photo"));
	      //Access specific category post from database
	      posts = recommendationController.db2.accessAllPosts(cat);
	      
	      try {
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
			public void loadUI(String ui,ResultSet rs)
			{
				try
				{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/"+ui+".fxml"));
					root = loader.load();
					postController pc =loader.getController();
					pc.back = "category";
					pc.cat = cat_field.getText();
					try {                                         //This Represent the specific post data
						if(rs.next())
						{
							sellerUserID = rs.getInt(1);
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
				controllerClass.borderpane.setCenter(root);
			}
	 
	 public void cat(String c)
	 {
		 cat_field.setText(c);
	 }
}
