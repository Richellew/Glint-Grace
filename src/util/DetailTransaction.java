package util;

public class DetailTransaction {
	private int TransactionID, JewelryID, Quantity;
	private String JewelryName, JewelryBrand, SubTotal, JewelryPrice;
	
	public DetailTransaction(int transactionID, int jewelryID, int quantity, String jewelryName, String jewelryBrand,
			String subTotal, String jewelryPrice) {
		super();
		TransactionID = transactionID;
		JewelryID = jewelryID;
		Quantity = quantity;
		JewelryName = jewelryName;
		JewelryBrand = jewelryBrand;
		SubTotal = subTotal;
		JewelryPrice = jewelryPrice;
	}
	public int getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
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
	public String getJewelryName() {
		return JewelryName;
	}
	public void setJewelryName(String jewelryName) {
		JewelryName = jewelryName;
	}
	public String getJewelryBrand() {
		return JewelryBrand;
	}
	public void setJewelryBrand(String jewelryBrand) {
		JewelryBrand = jewelryBrand;
	}
	public String getSubTotal() {
		return SubTotal;
	}
	public void setSubTotal(String subTotal) {
		SubTotal = subTotal;
	}
	public String getJewelryPrice() {
		return JewelryPrice;
	}
	public void setJewelryPrice(String jewelryPrice) {
		JewelryPrice = jewelryPrice;
	}
	
}
