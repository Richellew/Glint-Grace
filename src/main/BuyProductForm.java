package main;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import util.Cart;
import util.User;
import util.Jewelry;

public class BuyProductForm{
	
	private static BuyProductForm instance;
	  private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\jov2r\\Downloads\\BAD project (1).png";

	Scene scene;
	BorderPane bPane1, bPanequan;
	GridPane gPane;
	FlowPane bottomBtn;
	Window buyproductWindow;
	
	TextField nameField, phoneField;
	TextArea addressField; 
	Label selectJewelryLbl, quantityLbl, jewelryNameLbl;  
	Button addJewelryToCartBtn, clearCartBtn, checkOutBtn;
	Spinner<Integer> quantitySp;
	private Button paidBtn;
	
	TableView<Jewelry> jewelryTable;;
	TableView<Cart> cartTable;
	Vector<Jewelry> jewelrylist;
	Vector<Cart> cartlist;
	Database db = Database.getConnection();
	
	int jewelryId;
	int userID = LoginForm.getUser().getUserID();
	
	public static BuyProductForm getInstance() {
		if (instance == null) {
			instance = new BuyProductForm();
		}
		return instance;
	}
	

		 public void setTableJewelry() { 
		        
		jewelryTable = new TableView<>(); 
		jewelrylist = new Vector<>(); 
		TableColumn<Jewelry, Integer> col1 = new TableColumn<Jewelry, Integer>("Jewelry ID");  
		        TableColumn<Jewelry, String> col2 = new TableColumn<Jewelry, String>("Jewelry Name");  
		        TableColumn<Jewelry, Integer> col3 = new TableColumn<Jewelry, Integer>("Jewelry Brand"); 
		        TableColumn<Jewelry, String> col4 = new TableColumn<Jewelry, String>("Jewelry Price"); 
		        TableColumn<Jewelry, Integer> col5 = new TableColumn<Jewelry, Integer>("Jewelry Stock"); 
		        
		        col1.setCellValueFactory(new PropertyValueFactory<Jewelry, Integer>("JewelryID"));  
		        col2.setCellValueFactory(new PropertyValueFactory<Jewelry, String>("JewelryName"));  
		        col3.setCellValueFactory(new PropertyValueFactory<Jewelry, Integer>("JewelryBrand"));  
		        col4.setCellValueFactory(new PropertyValueFactory<Jewelry, String>("JewelryPriceWithCurrency"));  
		        col5.setCellValueFactory(new PropertyValueFactory<Jewelry, Integer>("JewelryStock")); 
		
		        jewelryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    
		        jewelryTable.setMaxHeight(250);
		        jewelryTable.setMinHeight(250);		        

		col1.setMinWidth(999/5);
		col2.setMinWidth(999/5);
		col3.setMinWidth(999/5);
		col4.setMinWidth(999/5);
		col5.setMinWidth(999/5);		
		
		jewelryTable.getColumns().addAll(col1, col2, col3, col4, col5);
		
		 bPane1.setTop(jewelryTable);
	     bPane1.setAlignment(jewelryTable, Pos.TOP_CENTER);
		
		col1.getCellData(0);
	}
		 
	
	public void setTableCart() {
		cartTable = new TableView<>();
		cartlist = new Vector<>();
		TableColumn<Cart, Integer> col1 = new TableColumn<Cart, Integer>("User ID");
		TableColumn<Cart, Integer> col2 = new TableColumn<Cart, Integer>("Jewelry ID");
		TableColumn<Cart, Integer> col3 = new TableColumn<Cart, Integer>("Quantity");
		
		col1.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("UserID"));
		col2.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("JewelryID"));
		col3.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("Quantity"));
		
		cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		col1.setMinWidth(999/3);
		col2.setMinWidth(999/3);
		col3.setMinWidth(999/3);
		
		cartTable.setMaxHeight(250);
		cartTable.setMinHeight(250);		
	
		cartTable.getColumns().addAll(col1,col2,col3);
	}
	
public void init() {
		
		
		bPane1 = new BorderPane();
		bPanequan = new BorderPane();
		
		gPane = new GridPane();
		bottomBtn = new FlowPane();

		buyproductWindow = new Window("Buy Product");
		
		setTableJewelry();  
        setTableCart();
				
        selectJewelryLbl = new Label("Selected Jewelry: None");
		
		quantityLbl = new Label("Quantity: ");

		quantitySp = new Spinner<>(0, 100, 0, 1);

		addJewelryToCartBtn = new Button("Add Jewelry To Cart");
		
		
		gPane.setAlignment(Pos.CENTER);
		
		clearCartBtn = new Button("Clear Cart");
		bottomBtn.getChildren().add(clearCartBtn);
		
		checkOutBtn = new Button("Checkout");
		bottomBtn.getChildren().add(checkOutBtn);
		
		bottomBtn.setHgap(15);
		bottomBtn.setPadding(new Insets(8, 0, 0, 0));
		
		gPane.add(selectJewelryLbl, 0, 1);
        gPane.add(quantityLbl, 1, 2);
        gPane.add(quantitySp, 2, 2);
        gPane.add(addJewelryToCartBtn, 3, 2);
        gPane.setHgap(7);


		bPanequan.setCenter(gPane);
		
		bPanequan.setBottom(cartTable);
		bPanequan.setAlignment(cartTable, Pos.CENTER);
		
		bPane1.setCenter(bPanequan);

		bPane1.setBottom(bottomBtn);
		
		bPane1.setPadding(new Insets(8, 8, 8, 8));
		try {
            File file = new File(BACKGROUND_IMAGE_PATH);
            String localUrl = file.toURI().toURL().toString();
            Image backgroundImage = new Image(localUrl);
            bPane1.setBackground(new Background(new BackgroundImage(backgroundImage,
                    null, null, null, null)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
		
		bottomBtn.setAlignment(Pos.BOTTOM_CENTER);

		buyproductWindow.getRightIcons().add(new CloseIcon(buyproductWindow));
		buyproductWindow.getContentPane().getChildren().add(bPane1);

		
	}
	
	public void getData() {
		String query = "SELECT * FROM jewelry";
		ResultSet rs = db.executeQuery(query);
		
		
		try {
			while(rs.next()) {
				 int jewelryID = rs.getInt("JewelryID");  
	                String jewelryName = rs.getString("JewelryName"); 
	                int jewelrybrandID = rs.getInt("BrandID");
	                int jewelryPrice = rs.getInt("JewelryPrice");  
	                int jewelryStock = rs.getInt("JewelryStock");
				
	                String querybrand = "SELECT * FROM brand WHERE BrandID = " + jewelrybrandID;
	                
	                ResultSet rsb = db.executeQuery2(querybrand);
	                String jewelryBrand = "";
	                
	                
	                
				if(rsb.next()) {
					jewelryBrand = rsb.getString("brandName");					
				}
				
				Jewelry jewelry = new Jewelry(jewelryID, jewelryName, jewelryBrand, jewelryPrice, jewelryStock); 
                jewelrylist.add(jewelry); 
                rsb.close();
			}
			rs.close();
			
			rs = db.executeQuery("SELECT * FROM cart WHERE UserID = " + userID);
			while(rs.next()) {
				 int jewelryID = rs.getInt("JewelryID");  
	                int quantity = rs.getInt("Quantity");
	                
	                Cart cart = new Cart(userID, jewelryID, quantity);
	                cartlist.add(cart);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void selectTable() {
		jewelryTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Jewelry>() {

			@Override
			 public void changed(ObservableValue<? extends Jewelry> observable, Jewelry oldValue, Jewelry newValue) {
                if (newValue != null) {
                    selectJewelryLbl.setText("Selected Jewelry: " + newValue.getJewelryName()); 
                    
                    jewelryId = newValue.getJewelryID();
				}
			}
		});
	}
	
	public void refreshTable() {
		 jewelrylist.clear();  
	        cartlist.clear();
	        getData();
	        ObservableList<Jewelry> jewelryObs = FXCollections.observableArrayList(jewelrylist);  
	        jewelryTable.setItems(jewelryObs);
	        ObservableList<Cart> cartObs = FXCollections.observableArrayList(cartlist);
	        cartTable.setItems(cartObs);
	}
	
	public void addJewelry() {
		 addJewelryToCartBtn.setOnMouseClicked((event)->{
	            if (jewelryId < 1) {
	                AlertError("You must select the product!");
	            } else if (quantitySp.getValue() == 0) {
	                AlertError("You must input quantity more than 0!");
	            } else {	               
	                int selectedQuantity = quantitySp.getValue();
	                Jewelry selectedJewelry = getSelectedJewelry(jewelryId);

	                if (selectedJewelry != null && selectedQuantity > selectedJewelry.getJewelryStock()) {
	                    AlertError("Insufficient stock! Maximum available quantity: " + selectedJewelry.getJewelryStock());
	                } else {
	                    String query = String.format("INSERT INTO cart(UserID, JewelryID, Quantity) VALUES ('%d','%d','%d')", userID, jewelryId, quantitySp.getValue());
	                    db.executeUpdate(query);
	                    quantitySp.getValueFactory().setValue(0);
	                    selectJewelryLbl.setText("Selected Jewelry: None");
	                    jewelryId = 0;
	                    refreshTable();
	                }
			}
		});
	}
	private Jewelry getSelectedJewelry(int jewelryId) {
	    for (Jewelry jewelry : jewelrylist) {
	        if (jewelry.getJewelryID() == jewelryId) {
	            return jewelry;
	        }
	    }
	    return null;
	}	
	
	
	public void AlertInformation(String content) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setHeaderText("Message");
		info.setContentText(content);
		info.showAndWait();
	}
	
	public void AlertError(String content) {

		Alert error = new Alert(AlertType.ERROR);
		error.setHeaderText("Error");
		error.setContentText(content);
		error.showAndWait();
	}
	
	public void Checkout() {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		now = Calendar.getInstance().getTime();
		sdf.format(now);
		java.sql.Date sqlDate = new java.sql.Date(now.getTime());		
		
		String query = "INSERT INTO headertransaction (UserID, TransactionDate) VALUES (?,?)";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setInt(1, LoginForm.getUser().getUserID());
			ps.setDate(2, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int transactionID = 0;
		String transactionQuery = String.format("SELECT TransactionID FROM headertransaction WHERE UserID = %d ORDER BY TransactionID DESC LIMIT 1",userID);
		ResultSet rs = db.executeQuery(transactionQuery);
		try {
			while(rs.next()){
				transactionID = rs.getInt("TransactionID");
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		
		  for (int i = 0; i < cartlist.size(); i++) {		        
		        String updateStockQuery = "UPDATE jewelry SET JewelryStock = JewelryStock - ? WHERE JewelryID = ?";
		        try {
		            PreparedStatement updateStockPS = db.prepareStatement(updateStockQuery);
		            updateStockPS.setInt(1, cartlist.get(i).getQuantity());
		            updateStockPS.setInt(2, cartlist.get(i).getJewelryID());
		            updateStockPS.executeUpdate();
		            updateStockPS.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }

		        String queryDetail = "INSERT INTO detailtransaction (TransactionID, JewelryID, Quantity) VALUES (?, ?, ?)";
		        try {
		            PreparedStatement detailPS = db.prepareStatement(queryDetail);
		            detailPS.setInt(1, transactionID);
		            detailPS.setInt(2, cartlist.get(i).getJewelryID());
		            detailPS.setInt(3, cartlist.get(i).getQuantity());
		            detailPS.executeUpdate();
		            detailPS.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        Jewelry selectedJewelry = getSelectedJewelry(cartlist.get(i).getJewelryID());
		        if (selectedJewelry != null && cartlist.get(i).getQuantity() > selectedJewelry.getJewelryStock()) {
		            AlertError("Insufficient stock for item: " + selectedJewelry.getJewelryName() +
		                    ". Maximum available quantity: " + selectedJewelry.getJewelryStock());		       
		            rollbackCheckout(transactionID);
		            return;
		        }
		  }
		  String queryDelete = "DELETE FROM cart WHERE UserID = " + userID;
			db.executeUpdate(queryDelete);
			refreshTable();
	}
		
		  private void rollbackCheckout(int transactionID) {			   
			    String deleteHeaderTransactionQuery = "DELETE FROM headertransaction WHERE TransactionID = ?";
			    try {
			        PreparedStatement deleteHeaderTransactionPS = db.prepareStatement(deleteHeaderTransactionQuery);
			        deleteHeaderTransactionPS.setInt(1, transactionID);
			        deleteHeaderTransactionPS.executeUpdate();
			        deleteHeaderTransactionPS.close();
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			 
			    String deleteDetailTransactionQuery = "DELETE FROM detailtransaction WHERE TransactionID = ?";
			    try {
			        PreparedStatement deleteDetailTransactionPS = db.prepareStatement(deleteDetailTransactionQuery);
			        deleteDetailTransactionPS.setInt(1, transactionID);
			        deleteDetailTransactionPS.executeUpdate();
			        deleteDetailTransactionPS.close();
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			}
	public Window getBuyWindow() {
		init();
		refreshTable();
		selectTable();
		addJewelry();
		
		clearCartBtn.setOnMouseClicked(e -> {
			Alert conforclear = new Alert(AlertType.CONFIRMATION);
			conforclear.setContentText("Are you sure to clear cart?");
			conforclear.showAndWait().ifPresent(respone -> {
				if (respone == ButtonType.OK) {
					String queryDelete = "DELETE FROM cart WHERE UserID = " + userID;
					db.executeUpdate(queryDelete);
					refreshTable();
				}
			});
			
		});
		
		checkOutBtn.setOnMouseClicked(e -> {
            Alert conforcheckout = new Alert(AlertType.CONFIRMATION);
            conforcheckout.setContentText("Are you sure want to checkout?");
            conforcheckout.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {                  
                    showUserInformationForm();
                }
            });
        });

        return buyproductWindow;
    }
    private void showUserInformationForm() {     
        Stage userInfoStage = new Stage();
        userInfoStage.setTitle("User Information");  
 
        Label nameLabel = new Label("Name:");
        nameField = new TextField();

        Label addressLabel = new Label("Address:");
        addressField = new TextArea();  

        Label phoneLabel = new Label("Phone:");
        phoneField = new TextField();
    
        Button submitBtn = new Button("Submit");
        submitBtn.setOnAction(event -> {            
            handleUserInformation();
            userInfoStage.close();
            showPhotoPage();
        });

        GridPane userInfoLayout = new GridPane();
        userInfoLayout.setVgap(10);
        userInfoLayout.setHgap(10);
        userInfoLayout.setPadding(new Insets(10));
        userInfoLayout.setAlignment(Pos.CENTER); 

        userInfoLayout.add(nameLabel, 0, 0);
        userInfoLayout.add(nameField, 1, 0);

        userInfoLayout.add(addressLabel, 0, 1);
        userInfoLayout.add(addressField, 1, 1);

        userInfoLayout.add(phoneLabel, 0, 2);
        userInfoLayout.add(phoneField, 1, 2);

        userInfoLayout.add(submitBtn, 1, 3);

        Scene userInfoScene = new Scene(userInfoLayout, 600, 400, Color.WHITE);
        userInfoStage.setScene(userInfoScene);

        userInfoStage.show();
    }
    
	 private void showPhotoPage() {
		    Stage photoStage = new Stage();
		    photoStage.setTitle("Photo Page");

		    ImageView photoImageView = new ImageView();	
		    String paymentPhotoPath = "C:\\Users\\jov2r\\Downloads\\WhatsApp Image 2023-12-15 at 23.44.18.jpeg";
		    Image paymentPhoto = new Image(new File(paymentPhotoPath).toURI().toString());
		    photoImageView.setImage(paymentPhoto);
		    photoImageView.setFitWidth(400);
		    photoImageView.setFitHeight(400);
		    Button okBtn = new Button("OK");
		    okBtn.setOnAction(event -> {
		        photoStage.close();
		        Checkout();
		        refreshTable();
		    });


		    paidBtn = new Button("Sudah Dibayar");
		    paidBtn.setOnAction(event -> {		   
		        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
		        informationAlert.setHeaderText("Pesanan Anda Diproses");
		        informationAlert.setContentText("Terima kasih atas pembayaran Anda. Pesanan Anda sedang diproses.");
		        informationAlert.showAndWait();
		    });

		 
		    VBox photoLayout = new VBox(10);
		    photoLayout.getChildren().addAll(photoImageView, okBtn, paidBtn);
		    photoLayout.setAlignment(Pos.CENTER);
		    photoLayout.setPadding(new Insets(10));

	
		    Scene photoScene = new Scene(photoLayout);
		    photoStage.setScene(photoScene);

		
		    photoStage.showAndWait();  
		}
	    private void handleUserInformation() {
	        String name = nameField.getText();
	        String address = addressField.getText();
	        String phone = phoneField.getText();

	        Alert informationAlert = new Alert(AlertType.INFORMATION);
	        informationAlert.setHeaderText("Success");
	        informationAlert.setContentText("Checkout successful!");	       
	        refreshTable();
	        informationAlert.showAndWait();
	    }

	    

	}