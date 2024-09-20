package main;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import util.Brand;
import util.Jewelry;  // Updated import statement

public class ManageProductForm {

    private static ManageProductForm instance;
    private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\jov2r\\Downloads\\BAD project (1).png";
    // Scene scene;
    BorderPane bPane;
    GridPane gPane;
    FlowPane fPane;

    Label jewelryNameLbl, jewelryPriceLbl, jewelryStockLbl, jewelryBrandLbl;
    Button insertJewelryBtn, updateJewelryBtn, deleteJewelryBtn;
    TextField jewelryNameTF, jewelryPriceTF;
    Spinner<Integer> jewelryStockSpn;
    ComboBox<String> jewelryBrandCBX;
    TableView<Jewelry> jewelryTable;

    Vector<Brand> brandList = new Vector<Brand>();
    Vector<Jewelry> jewelryList = new Vector<Jewelry>();

    Database db = Database.getConnection();

    Boolean kondisi = false;
    Window manageWindow;

    int jewelryID = 0;

    public static ManageProductForm getInstance() {
        if (instance == null) {
            instance = new ManageProductForm();
        }

        return instance;
    }

    public void getBrandData() {
        String query = "SELECT * FROM brand";
        ResultSet rs = db.executeQuery(query);

        try {
            while (rs.next()) {
                int brandID = rs.getInt("BrandID");
                String brandName = rs.getString("BrandName");

                Brand brand = new Brand(brandID, brandName);
                brandList.add(brand);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        brandList.clear();
        getBrandData();

        bPane = new BorderPane();
        gPane = new GridPane();
        fPane = new FlowPane();

        manageWindow = new Window("Manage Product");

        jewelryNameLbl = new Label("Jewelry Name: ");
        jewelryNameLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        jewelryPriceLbl = new Label("Jewelry Price: ");
        jewelryPriceLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        jewelryStockLbl = new Label("Jewelry Stock: ");
        jewelryStockLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        jewelryBrandLbl = new Label("Jewelry Brand: ");
        jewelryBrandLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        insertJewelryBtn = new Button("Insert Jewelry");
        insertJewelryBtn.setPrefWidth(200);
        insertJewelryBtn.setPrefHeight(30);

        updateJewelryBtn = new Button("Update Jewelry");
        updateJewelryBtn.setPrefWidth(200);
        updateJewelryBtn.setPrefHeight(30);
		
        deleteJewelryBtn = new Button("Delete Jewelry");
        deleteJewelryBtn.setPrefWidth(200);
        deleteJewelryBtn.setPrefHeight(30);

        jewelryNameTF = new TextField();
        jewelryNameTF.setPromptText("Name");
        jewelryPriceTF = new TextField();
        jewelryPriceTF.setPromptText("Price");

        jewelryStockSpn = new Spinner<>(0, 1000, 0);

        jewelryBrandCBX = new ComboBox<>();
        jewelryBrandCBX.getItems().add("Choose One");
        for (int i = 0; i < brandList.size(); i++) {
            jewelryBrandCBX.getItems().add(brandList.get(i).getBrandName());
        }
        jewelryBrandCBX.getSelectionModel().select(0);

        manageWindow.getRightIcons().add(new CloseIcon(manageWindow));
        manageWindow.getContentPane().getChildren().add(bPane);

	}
	
	public void arrangeComponent() {
		
		 jewelryTable = new TableView<>();

	        TableColumn<Jewelry, Integer> column1 = new TableColumn<>("Jewelry ID");
	        column1.setCellValueFactory(new PropertyValueFactory<>("JewelryID"));
	        column1.setMinWidth(150);

	        TableColumn<Jewelry, String> column2 = new TableColumn<>("Jewelry Name");
	        column2.setCellValueFactory(new PropertyValueFactory<>("JewelryName"));
	        column2.setMinWidth(150);

	        TableColumn<Jewelry, String> column3 = new TableColumn<>("Jewelry Brand");
	        column3.setCellValueFactory(new PropertyValueFactory<>("JewelryBrand"));
	        column3.setMinWidth(150);

	        TableColumn<Jewelry, String> column4 = new TableColumn<>("Jewelry Price");
	        column4.setCellValueFactory(new PropertyValueFactory<Jewelry, String>("JewelryPriceWithCurrency"));
	        column4.setMinWidth(150);

	        TableColumn<Jewelry, Integer> column5 = new TableColumn<>("Jewelry Stock");
	        column5.setCellValueFactory(new PropertyValueFactory<>("JewelryStock"));
	        column5.setMinWidth(150);

	        jewelryTable.getColumns().addAll(column1, column2, column3, column4, column5);
	        
	        jewelryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	        jewelryNameTF.setMinWidth(170);
	        jewelryPriceTF.setMinWidth(170);

	        gPane.add(jewelryNameLbl, 0, 0);
	        gPane.add(jewelryStockLbl, 0, 1);
	        gPane.add(jewelryNameTF, 1, 0);
	        gPane.add(jewelryStockSpn, 1, 1);
	        gPane.add(jewelryPriceLbl, 2, 0);
	        gPane.add(jewelryBrandLbl, 2, 1);
	        gPane.add(jewelryPriceTF, 3, 0);
	        gPane.add(jewelryBrandCBX, 3, 1);
	        gPane.setHgap(10);
	        gPane.setVgap(10);
	        gPane.setPadding(new Insets(10, 0, 0, 0));
	        gPane.setAlignment(Pos.CENTER);

	        fPane.setPadding(new Insets(6, 0, 20, 0));
	        fPane.setHgap(15);
	        fPane.getChildren().addAll(insertJewelryBtn, updateJewelryBtn, deleteJewelryBtn);
	        fPane.setAlignment(Pos.TOP_CENTER);

		   
	        bPane.setTop(jewelryTable);
	        bPane.setCenter(gPane);
	        bPane.setBottom(fPane);     
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
	
	public void getData() {
		String query = "SELECT * FROM jewelry";
        ResultSet rs = db.executeQuery(query);
		
		try {
			while(rs.next()) {
				int jewelryID = rs.getInt("JewelryID");
                String jewelryName = rs.getString("JewelryName");
                int jewelryBrandID = rs.getInt("BrandID");
                int jewelryPrice = rs.getInt("JewelryPrice");
                int jewelryStock = rs.getInt("JewelryStock");

                String queryBrand = "SELECT * FROM brand WHERE BrandID = " + jewelryBrandID;
                ResultSet rsBrand = db.executeQuery2(queryBrand);
                String jewelryBrand = "";            
                if (rsBrand.next()) {
                    jewelryBrand = rsBrand.getString("BrandName");			
                }

                Jewelry jewelry = new Jewelry(jewelryID, jewelryName, jewelryBrand, jewelryPrice, jewelryStock);
                jewelryList.add(jewelry);
                rsBrand.close();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
		}
	}
	
	public void refreshTable() {
		jewelryList.clear();
        getData();
        ObservableList<Jewelry> jewelryObs = FXCollections.observableArrayList(jewelryList);
        jewelryTable.setItems(jewelryObs);
	}
	
	public void addJewelry() {
		 insertJewelryBtn.setOnMouseClicked((event) -> {
	            String brandName = jewelryBrandCBX.getValue();

	            if (brandName.equals("Choose One") || jewelryNameTF.getText().equals("") || jewelryPriceTF.getText().equals("")
	                    || jewelryStockSpn.getValue() == 0) {
	                Alert error = new Alert(AlertType.ERROR);
	                error.setHeaderText("Error");
	                error.setContentText("Data needed not complete!");
	                error.showAndWait();
	            } else {
	                brandList.clear();
	                getBrandData();
	                int brandID = 1;
	                for (int i = 0; i < brandList.size(); i++) {
	                    if (brandList.get(i).getBrandName().equals(brandName)) {
	                        brandID = brandList.get(i).getBrandID();
	                    }
	                }
	                
				
	                String query = String.format("INSERT INTO jewelry(BrandID, JewelryName, JewelryPrice, JewelryStock) VALUES ('%d','%s','%d','%d')",
	                        brandID, jewelryNameTF.getText(), Integer.parseInt(jewelryPriceTF.getText()),
	                        jewelryStockSpn.getValue());
	                db.executeUpdate(query);
	                jewelryNameTF.setText("");
	                jewelryPriceTF.setText("");
	                jewelryStockSpn.getValueFactory().setValue(0);
	                jewelryBrandCBX.getSelectionModel().select(0);
	                AlertInformation("New jewelry successfully inserted!");
	                refreshTable();
			}
		});
	}
	
	public void editTable() {
		jewelryTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Jewelry>() {

			@Override
			public void changed(ObservableValue<? extends Jewelry> observable, Jewelry oldValue, Jewelry newValue) {
                if (newValue != null) {
                    kondisi = true;

                    brandList.clear();
                    getBrandData();
                    jewelryID = newValue.getJewelryID();
                    jewelryNameTF.setText(newValue.getJewelryName());
                    jewelryPriceTF.setText(Integer.toString(newValue.getJewelryPrice()));
                    jewelryStockSpn.getValueFactory().setValue(newValue.getJewelryStock());
                    String brandValue = newValue.getJewelryBrand();

                    for (int i = 0; i < brandList.size(); i++) {
                        if (brandList.get(i).getBrandName().equals(brandValue)) {
                            jewelryBrandCBX.getSelectionModel().select(i + 1);
                        }
                    }
											
                    updateJewelryBtn.setOnMouseClicked((event) -> {
                        if (jewelryID <= 0) {
                            System.out.println("Test");
                            AlertError("You must select a jewelry from the table first!");
                        } else {
                            String brandName = jewelryBrandCBX.getValue();
                            brandList.clear();
                            getBrandData();
                            int brandID = 1;
                            for (int i = 0; i < brandList.size(); i++) {
                                if (brandList.get(i).getBrandName().equals(brandName)) {
                                    brandID = brandList.get(i).getBrandID();
                                }
                            }
                            String query = String.format(
                                    "UPDATE jewelry SET BrandID='%d',JewelryName='%s',JewelryPrice='%d',JewelryStock='%d' WHERE JewelryID = %d",
                                    brandID, jewelryNameTF.getText(), Integer.parseInt(jewelryPriceTF.getText()),
                                    jewelryStockSpn.getValue(), jewelryID);
                            db.executeUpdate(query);
                            jewelryNameTF.setText("");
                            jewelryPriceTF.setText("");
                            jewelryStockSpn.getValueFactory().setValue(0);
                            jewelryBrandCBX.getSelectionModel().select(0);
                            jewelryID = 0;
                            AlertInformation("Jewelry successfully updated!");
                            refreshTable();
                        }
                    });
				
						
					 deleteJewelryBtn.setOnMouseClicked((event) -> {
	                        if (jewelryID <= 0) {
	                            System.out.println("Test");
	                            AlertError("You must select a jewelry from the table first!");
	                        } else {
	                            String query = String.format("DELETE FROM jewelry WHERE JewelryID = %d", jewelryID);
	                            db.executeUpdate(query);
	                            jewelryNameTF.setText("");
	                            jewelryPriceTF.setText("");
	                            jewelryStockSpn.getValueFactory().setValue(0);
	                            jewelryBrandCBX.getSelectionModel().select(0);
	                            jewelryID = 0;
	                            AlertInformation("Jewelry successfully deleted!");
	                            refreshTable();
						}
					});
						
				}		
			} 	
		});
	}
	
	public void AlertError(String content) {
		Alert error = new Alert(AlertType.ERROR);
		error.setHeaderText("Error");
		error.setContentText(content);
		error.show();
	}
	
	public void AlertInformation(String content) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setHeaderText("Message");
		info.setContentText(content);
		info.show();
	}
	
	
	public Window showManageProductWindow() {	
		initialize();
		arrangeComponent();
		refreshTable();
		addJewelry();
		editTable();
		
		updateJewelryBtn.setOnMouseClicked((event) -> {
            AlertError("You must select a jewelry from the table first!");
        });

        deleteJewelryBtn.setOnMouseClicked((event) -> {
            AlertError("You must select a jewelry from the table first!");
        });

        return manageWindow;
	}
}