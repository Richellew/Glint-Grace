package main;

import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;

public class CustomerMain{
	
	private static CustomerMain instance;
	private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\jov2r\\Downloads\\BAD project (7).jpg";

	Scene scene;
	
	BorderPane bPane;
	
	MenuBar menuBar;
	Menu userMenu, aboutUs, managementMenu;
	MenuItem logOutMI, buyWatchMI, myTransactionHistoryMI, visionMI, locationMI; 
	
	public static CustomerMain getInstance() {
		if (instance == null) {
			instance = new CustomerMain();
		}
		
		return instance;
	}
	
	public void initialize() {
		bPane = new BorderPane();
		
		menuBar = new MenuBar();
		userMenu = new Menu("User");
		managementMenu = new Menu("Management");
		aboutUs = new Menu("About us");
		logOutMI = new MenuItem("Logout");
		buyWatchMI = new MenuItem("Buy Jewelry");
		myTransactionHistoryMI =  new MenuItem("My Transaction History");
		visionMI = new MenuItem("Vision");
		locationMI = new MenuItem("Location");		

		menuBar.getMenus().add(userMenu);
		menuBar.getMenus().add(managementMenu);
		menuBar.getMenus().add(aboutUs);		

		userMenu.getItems().add(logOutMI);
		managementMenu.getItems().add(buyWatchMI);
		managementMenu.getItems().add(myTransactionHistoryMI);
		aboutUs.getItems().add(visionMI);
		aboutUs.getItems().add(locationMI);	
		
		
		bPane.setTop(menuBar);
		bPane.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
		scene = new Scene(bPane,1000,730);
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
	
	public void showCustomerPage() {
		initialize();
		
		buyWatchMI.setOnAction((event)->{
			BuyProductForm bpf = BuyProductForm.getInstance();
			bPane.setCenter(bpf.getBuyWindow());
		});
		
		myTransactionHistoryMI.setOnAction((event) -> {
			TransactionHistoryForm thf = TransactionHistoryForm.getInstance();
			bPane.setCenter(thf.getTransactionHistoryWindow());
		});
		
	 visionMI.setOnAction((event) -> {
			     VisionForm vf = VisionForm.getInstance();
			     bPane.setCenter(vf.getVisionFormWindow());
		 });
		
	 locationMI.setOnAction(event -> {
		 LocationForm lf = LocationForm.getInstance();
		 bPane.setCenter(lf.getLocationFormWindow());
	 });
		logOutMI.setOnAction((event)->{
			LoginForm.setUser(null);
			LoginForm lf = LoginForm.getInstance();
			lf.showLogin();
		});
		
		Main.changeScene(scene, "Customer Main");
	}

	

}
