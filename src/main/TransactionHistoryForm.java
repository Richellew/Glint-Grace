package main;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import util.Cart;
import util.DetailTransaction;
import util.HeaderTransaction;
import util.Jewelry;

public class TransactionHistoryForm {
	
	private static TransactionHistoryForm instance;
	 private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\jov2r\\Downloads\\BAD project (1).png";
	 
//	Scene scene;
	BorderPane bPane;
	FlowPane fPane;
	
	Label selectedTransactionLbl;
	
	TableView<HeaderTransaction> tableView1;
	TableView<DetailTransaction> tableView2;
	
	Database db = Database.getConnection();
	Window transactionHistoryWindow;
	
	Vector<HeaderTransaction> transactionList = new Vector<HeaderTransaction>();
	Vector<DetailTransaction> transactionDetailList = new Vector<DetailTransaction>();
	 Vector<Jewelry> jewelryList = new Vector<Jewelry>();
	
	int transactionID = 0;
	
	public static TransactionHistoryForm getInstance() {
		if(instance == null) {
			instance = new TransactionHistoryForm();
		}
		
		return instance;
	}

	public void initialize() {
		
		bPane = new BorderPane();
		fPane = new FlowPane();
		
		selectedTransactionLbl = new Label("Selected Transaction: None");

		transactionHistoryWindow = new Window("Transaction History");
		transactionHistoryWindow.getRightIcons().add(new CloseIcon(transactionHistoryWindow));
		transactionHistoryWindow.getContentPane().getChildren().add(bPane);
				
	}
	
	public void arrangeComponent() {

		tableView1 = new TableView<HeaderTransaction>();
		TableColumn<HeaderTransaction, Integer> column1 = new TableColumn<>("Transaction ID");
		column1.setMinWidth(130);
		column1.setCellValueFactory(new PropertyValueFactory<HeaderTransaction, Integer>("TransactionID"));

		TableColumn<HeaderTransaction, Integer> column2 = new TableColumn<>("User ID");
		column2.setMinWidth(120);
		column2.setCellValueFactory(new PropertyValueFactory<HeaderTransaction, Integer>("UserID"));

		TableColumn<HeaderTransaction, Date> column3 = new TableColumn<>("Transaction Date");
		column3.setMinWidth(170);
		column3.setCellValueFactory(new PropertyValueFactory<HeaderTransaction, Date>("TransactionDate"));

		tableView1.getColumns().add(column1);
		tableView1.getColumns().add(column2);
		tableView1.getColumns().add(column3);
		
		tableView1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
		tableView1.setPadding(new Insets(10, 10, 10, 10));
		tableView1.setMinHeight(250);
		tableView1.setMaxHeight(250);	

		VBox vbox = new VBox(tableView1);
		vbox.setPadding(new Insets(10, 10, 10, 10));

		fPane.setPadding(new Insets(10, 0, 10, 0));
		fPane.getChildren().add(selectedTransactionLbl);
		fPane.setAlignment(Pos.CENTER_LEFT);

		tableView2 = new TableView<>();
		
		TableColumn<DetailTransaction, Integer> column4 = new TableColumn<>("Transaction ID");
		column4.setMinWidth(142);
		column4.setCellValueFactory(new PropertyValueFactory<DetailTransaction, Integer>("TransactionID"));
		
		TableColumn<DetailTransaction, Integer> column5 = new TableColumn<>("Jewelry ID");
		column5.setMinWidth(142);
		column5.setCellValueFactory(new PropertyValueFactory<DetailTransaction, Integer>("JewelryID"));
		
		TableColumn<DetailTransaction, String> column6 = new TableColumn<DetailTransaction, String>("Jewelry Name");
		column6.setMinWidth(170);
		column6.setCellValueFactory(new PropertyValueFactory<DetailTransaction, String>("JewelryName"));
		
		TableColumn<DetailTransaction, String> column7 = new TableColumn<DetailTransaction, String>("Jewelry Brand");
		column7.setMinWidth(142);
		column7.setCellValueFactory(new PropertyValueFactory<DetailTransaction, String>("JewelryBrand"));
		
		TableColumn<DetailTransaction, String> column8 = new TableColumn<DetailTransaction, String>("Jewelry Price");
		column8.setMinWidth(142);
		column8.setCellValueFactory(new PropertyValueFactory<DetailTransaction, String>("JewelryPrice"));

		TableColumn<DetailTransaction, Integer> column9 = new TableColumn<DetailTransaction, Integer>("Quantity");
		column9.setMinWidth(142);
		column9.setCellValueFactory(new PropertyValueFactory<DetailTransaction, Integer>("Quantity"));

		TableColumn<DetailTransaction, String> column10 = new TableColumn<DetailTransaction, String>("Sub Total");
		column10.setMinWidth(143);
		column10.setCellValueFactory(new PropertyValueFactory<DetailTransaction, String>("SubTotal"));
		
		tableView2.getColumns().addAll(column4,column5,column6,column7,column8,column9,column10); 
		tableView2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView2.setPadding(new Insets(10, 10, 10, 10));

		bPane.setTop(tableView1);		
		
		bPane.setAlignment(tableView1, Pos.CENTER);
		bPane.setCenter(fPane);
		bPane.setBottom(tableView2);
		bPane.setAlignment(tableView2, Pos.CENTER);
		try {
            File file = new File(BACKGROUND_IMAGE_PATH);
            String localUrl = file.toURI().toURL().toString();
            Image backgroundImage = new Image(localUrl);
            bPane.setBackground(new Background(new BackgroundImage(backgroundImage,
                    null, null, null, null)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
	}
	
	public void getJewelryData() {
		String query = "SELECT * FROM `jewelry`";
		ResultSet rs = db.executeQuery(query);
		
		try {
			while(rs.next()) {
				 int jewelryID = rs.getInt("JewelryID");
	                String jewelryName = rs.getString("JewelryName");
	                int jewelryBrandID = rs.getInt("BrandID");
	                int jewelryPrice = rs.getInt("JewelryPrice");
	                int jewelryStock = rs.getInt("JewelryStock");
				
	                String queryBrand = "SELECT * FROM `brand` WHERE BrandID = " + jewelryBrandID;
	                
	                ResultSet rsBrand = db.executeQuery2(queryBrand);
	                String jewelryBrand = "";
	                
	                if(rsBrand.next()) {
	                    jewelryBrand = rsBrand.getString("BrandName");                    
	                }
				
	                Jewelry jewelry = new Jewelry(jewelryID, jewelryName, jewelryBrand, jewelryPrice, jewelryStock);
	                jewelryList.add(jewelry);
	                rsBrand.close();
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getData() {
		getJewelryData();
		LoginForm.getInstance();
		int userID = LoginForm.getUser().getUserID();
		String query = "SELECT * FROM `headertransaction` WHERE UserID = " + userID;
		ResultSet rs = db.executeQuery(query);
		
		try {
			while(rs.next()) {
				int transactionId = rs.getInt("TransactionID");
				Date transactionDate = new Date(); 
				transactionDate = rs.getDate("TransactionDate");
				
				HeaderTransaction transactionHead = new HeaderTransaction(transactionId, userID, transactionDate);						
				transactionList.add(transactionHead);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshTable() {
		transactionList.clear();
		jewelryList.clear();
		getData();
		ObservableList<HeaderTransaction> transactionObs = FXCollections.observableArrayList(transactionList);
		tableView1.setItems(transactionObs);
	}
	
	public void selectTable() {
		tableView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HeaderTransaction>() {
			@Override
			public void changed(ObservableValue<? extends HeaderTransaction> observable, HeaderTransaction oldValue, HeaderTransaction newValue) {
				if (newValue != null) {
					transactionDetailList.clear();
					transactionID = newValue.getTransactionID();
					selectedTransactionLbl.setText("Selected Transaction: " + "Transaction " + transactionID);
					
					String queryDetail = String.format("SELECT * FROM `detailtransaction` WHERE TransactionID = %d",transactionID);
					ResultSet rs = db.executeQuery(queryDetail);
					
					try {
						while(rs.next()) {
							  int jewelryID = rs.getInt("JewelryID"); 
	                            int quantity = rs.getInt("Quantity");
	                            String jewelryName = null;
	                            String jewelryBrand = null;
	                            String jewelryPrice = null;
	                            String subTotal = null;
							
						
								for (int i = 0; i<jewelryList.size(); i++) {
	                                if(jewelryList.get(i).getJewelryID() == jewelryID) {
	                                    jewelryName = jewelryList.get(i).getJewelryName();
	                                    jewelryBrand = jewelryList.get(i).getJewelryBrand();
	                                    jewelryPrice = "$" + Integer.toString(jewelryList.get(i).getJewelryPrice());
	                                    subTotal = "$" + Integer.toString(jewelryList.get(i).getJewelryPrice() * quantity);
								}
							}
							
								 DetailTransaction detailTransaction = new DetailTransaction(transactionID, jewelryID, quantity, jewelryName, jewelryBrand, jewelryPrice, subTotal);
		                            transactionDetailList.add(detailTransaction);
						}
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ObservableList<DetailTransaction> detailTransactionObs = FXCollections.observableArrayList(transactionDetailList);
				tableView2.setItems(detailTransactionObs);
			}
		});
	}
	
	public Window getTransactionHistoryWindow(){
		initialize();
		arrangeComponent();
		refreshTable();
		selectTable();
		
		return transactionHistoryWindow;
	}

}
