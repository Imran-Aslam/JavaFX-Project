package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class database {
	//..Members..
			public Connection connection;
			public Statement statement;
			public ResultSet rs;
			//..Methods..
			// This is Constructor which establish the database connections
			database()
			{
				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					// Establish the database connection.
					connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","hr","Oracle_1");
					// create a statement for execution of SQL commands.
					statement = connection.createStatement();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," \n****  Exception Caught in ****\n"+e+"\n"+e.getMessage()+"\n"+e.getCause());
				}
			} // End of database Constructor 	      
			

			// This function is used to add a user a database
			void addUser(String firstName,String lastName,String gender,String mobile,String address,String username,String password,String city)
			{
				try
				{
					String query = "BEGIN \r\n" + 
							"insertUser (?,?,?,?,?,?,?,?);\r\n" + 
							"END;";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, firstName);
					pst.setString(2, lastName);
					pst.setString(3, gender);
					pst.setString(4, mobile);
					pst.setString(5, address);
					pst.setString(6, city);
					pst.setString(7, username);
					pst.setString(8, password);

					pst.executeUpdate();
//					connection.close();
					JOptionPane.showMessageDialog(null,"Successfully Registered. Thanks..","",JOptionPane.PLAIN_MESSAGE);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in Add User\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			//This function is used to Add a Post in data
			void addPost(int userID,String postName,String description,String category,String subCategory,int price,String city,
					String address,String status,String pic)
			{
				FileInputStream fi = null;     
				   try {
					   //Here image path convert into file then convert file into input stream so that only image binary store in the database
					   //That binary we also use any way to see the image e.g. we can use online platform like convert binary into image
					   //This is our core functionality that we cannot store the image path in the database but we store image binary in the database 
					   File file = new File(pic);
					   fi = new FileInputStream(file);
				   		} catch (FileNotFoundException e) {
				   			// TODO Auto-generated catch block
				   			e.printStackTrace();
				   			}
				try
				{
					String query = "BEGIN\r\n" + 
							"insertPost(?,?,?,?,?,?,?,?,?,?);\r\n" + 
							"END;";
					
					
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, userID);
					pst.setString(2, postName);
					pst.setString(3, description);
					pst.setString(4, category);
					pst.setString(5, subCategory);
					pst.setInt(6, price);
					pst.setString(7, city);
					pst.setString(8, address);
					pst.setString(9, status);
					pst.setBinaryStream(10, fi);
					pst.execute();
					JOptionPane.showMessageDialog(null,"Successfully Post Added.","",JOptionPane.PLAIN_MESSAGE);
//					connection.close();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in Add Posts\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			void checkUsername(String username)									//This function is used to check the userName i.e., exist or not exist
			{
				try
				{
					String query = "Select userName From registeredUser where userName='"+username+"'";
					rs = statement.executeQuery(query);
					if(rs.next())
					{
						if(username.equals(rs.getString(1)))
						{
							JOptionPane.showMessageDialog(null," This Username is already exits. Try another\n","Warning",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in Check User Name\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			// This function access the table from the database
			ResultSet accessUserInformation(String userName)    //This function is used to check the userName and password when user login yourself
			{																	//This function is also used to show the userInformation
				try
				{
					rs = statement.executeQuery("Select * From registeredUser where userName='"+userName+"'");
//					connection.close();
					return rs;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in Access User Information\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
				return null;
			} // End of accessTable function
			
			ResultSet accessAllPosts(String subCategory)							//This function is used to show Specific category
			{
				try
				{
					rs = statement.executeQuery("Select addPost.postID,addPost.postName,addPost.postPrice,postImages.postPic From addPost INNER JOIN postImages"
							+ " ON addPost.postID=postImages.postID where postSubcategory='"+subCategory+"'");
//					connection.close();
					return rs;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught In Access All Posts\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}
			// Filter Function
			ResultSet filterPosts(String subCategory,int min,int max)							//This function is used to show Specific category
			{
				try
				{
					rs = statement.executeQuery("Select addPost.postID,addPost.postName,addPost.postPrice,postImages.postPic From addPost INNER JOIN postImages"
							+ " ON addPost.postID=postImages.postID where postSubcategory='"+subCategory+"' AND (addPost.postPrice BETWEEN "+min+" AND "+max+")");
//					connection.close();
					return rs;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught In Access All Posts\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}
			ResultSet showOnePost(int postID)									//This function is used to get one clickable post Information
			{
				try
				{
					rs = statement.executeQuery("Select addPost.userID,addPost.postName,addPost.postDescription,addPost.postPrice,"
							+ "addPost.postCity,addPost.postAddress,addPost.postDate, postImages.postPic From addPost INNER JOIN postImages"
							+ " ON addPost.postID=postImages.postID Where addPost.postID="+postID);
//					connection.close();
					return rs;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in ShowOnePost\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
				return null;
				
			}
			
			ResultSet accessSeller(int userID)                			   //This function is used to get Seller Name and mobile no. to show in SinglePostPanel
			{
				try
				{
					rs = statement.executeQuery("select name,mobileNo From registeredUser where userID="+userID);
//					connection.close();
					return rs;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in Access Seller\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}
			
			ResultSet showMyPosts(int userID)                     //This Function Show the LogIn user MY POSTS
			{
				try
				{
					rs = statement.executeQuery("Select addPost.postID,addPost.postName,addPost.postPrice,postImages.postPic From addPost INNER JOIN postImages"
							+ " ON addPost.postID=postImages.postID Where addPost.userID="+userID+"");
//					connection.close();
					return rs;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in show My Posts\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}
			
			void deletePost(int postID)                 //This Function Delete the Post
			{
				try
				{
					String query = "BEGIN \r\n" + 
							"deletePost (?);\r\n" + 
							"END;";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, postID);
					pst.executeQuery();
					
					JOptionPane.showMessageDialog(null,"Successfully Deleted the Post. ","",JOptionPane.PLAIN_MESSAGE);
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null," Exception Caught in Delete Post\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			//This Function is used to Update the LoggedIn user Data 
			void updateData(int userID , String name , String lastName , String gender , String mobile , String address , String password )                          
			{
				try {
					String query = "BEGIN \r\n" + 
							"updateUser (?,?,?,?,?,?,?);\r\n" + 
							"END;";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, userID);
					pst.setString(2, name);
					pst.setString(3, lastName);
					pst.setString(4, gender);
					pst.setString(5, mobile);
					pst.setString(6, address);
					pst.setString(7, password);
					pst.execute();
						JOptionPane.showMessageDialog(null,"Successfully Update the Data. ","",JOptionPane.PLAIN_MESSAGE);
//						connection.close();
						
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null," Exception Caught in Update Data\n"+e," Error",JOptionPane.ERROR_MESSAGE);
				}
			}

}
