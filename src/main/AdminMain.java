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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;

public class AdminMain{

	private static AdminMain instance;
	private static final String BACKGROUND_IMAGE_PATH = "C:\\Users\\jov2r\\Downloads\\BAD project (7).jpg";
	Scene scene;
	BorderPane bPane;
	
	MenuBar menuBar;
	Menu userMenu, managementMenu;
	MenuItem logOutMI, manageProductMI, manageBrandMI; 
	
	public static AdminMain getInstance() {
		if (instance == null) {
			instance = new AdminMain();
		}
		return instance;
	}
	
	public void initialize() {
		bPane = new BorderPane();
		
		menuBar = new MenuBar();
		userMenu = new Menu("User");
		managementMenu = new Menu("Management");
		logOutMI = new MenuItem("Logout");
		manageProductMI = new MenuItem("Manage Product");
		manageBrandMI =  new MenuItem("Manage Brand");		

		menuBar.getMenus().add(userMenu);
		menuBar.getMenus().add(managementMenu);		

		userMenu.getItems().add(logOutMI);
		managementMenu.getItems().add(manageProductMI);
		managementMenu.getItems().add(manageBrandMI);
		

		bPane.setTop(menuBar);
		bPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		scene = new Scene(bPane,800,690);
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
	
	public void showPage() {
		initialize();
		
		manageBrandMI.setOnAction((event)->{
			ManageBrandForm mbf = ManageBrandForm.getInstance();
			bPane.setCenter(mbf.getWindow());
		});
		
		manageProductMI.setOnAction((event)->{
			ManageProductForm mpf = ManageProductForm.getInstance();
			bPane.setCenter(mpf.showManageProductWindow());
		});	
//		
		
		logOutMI.setOnAction((event) -> {
			LoginForm.setUser(null);
			LoginForm lf = LoginForm.getInstance();
			lf.showLogin();
		});
		
		Main.changeScene(scene, "Admin Main");
	}
}
