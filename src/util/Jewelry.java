package util;

public class Jewelry {
	
	private int JewelryID;
	private String JewelryName;
	private String JewelryBrand;
	private int JewelryPrice;
	private int JewelryStock;
	private String JewelryPriceWithCurrency;
	
	public Jewelry(int jewelryID, String jewelryName, String jewelryBrand, int jewelryPrice, int jewelryStock) {
		super();
		JewelryID = jewelryID;
		JewelryName = jewelryName;
		JewelryBrand = jewelryBrand;
		JewelryPrice = jewelryPrice;
		JewelryStock = jewelryStock;
		JewelryPriceWithCurrency = "$" + jewelryPrice;
		
	}
	public int getJewelryID() {
		return JewelryID;
	}
	public void setJewelryID(int jewelryID) {
		JewelryID = jewelryID;
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
	public int getJewelryPrice() {
		return JewelryPrice;
	}
	public void setJewelryPrice(int jewelryPrice) {
		JewelryPrice = jewelryPrice;
	}
	public void setJewelryPriceWithCurrency(String jewelryPrice) {
        JewelryPriceWithCurrency = "$" + jewelryPrice;
	}	
	public String getJewelryPriceWithCurrency() {
        return JewelryPriceWithCurrency;
    }
	public int getJewelryStock() {
		return JewelryStock;
	}
	public void setJewelryStock(int jewelryStock) {
		JewelryStock = jewelryStock;
	}
}