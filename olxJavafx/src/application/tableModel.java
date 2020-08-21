package application;

import javafx.scene.image.ImageView;

public class tableModel {
	
	private ImageView photo;
    private  final int ID;
    private  final int price;
    private  final String name;
	public tableModel(int iD, int price, String name,ImageView photo) {
		
		this.ID = iD;
		this.price = price;
		this.name = name;
		this.photo = photo;
//		this.photo.setFitHeight(150);
//	    this.photo.setFitWidth(150);
	    this.photo.setFitWidth(photo.getFitWidth());
	    this.photo.setFitHeight(photo.getFitHeight());
	}
	public ImageView getPhoto() {
		return photo;
	}
	public void setPhoto(ImageView photo) {
		this.photo = photo;
	}
	public int getID() {
		return ID;
	}
	public int getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}


}
