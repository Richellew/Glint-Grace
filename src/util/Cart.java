package util;

public class Cart {

	protected int UserID, JewelryID, Quantity;

	public Cart(int userID, int jewelryID, int quantity) {
		super();
		UserID = userID;
		JewelryID = jewelryID;
		Quantity = quantity;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getJewelryID() {
		return JewelryID;
	}

	public void setJewelryID(int jewelryID) {
		JewelryID = jewelryID;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

}