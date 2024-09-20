package main;

import java.io.File;
import java.net.MalformedURLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;

public class LocationForm {

    private static LocationForm instance;
    private static final String BACKGROUND_IMAGE_PATH = "C:\\\\Users\\\\jov2r\\\\Downloads\\\\BAD project (1).png";
    public static LocationForm getInstance() {
        if (instance == null) {
            instance = new LocationForm();
        }
        return instance;
    }

    public Window getLocationFormWindow() {
        Window locationWindow = new Window("Location Information");

        HBox mainHBox = new HBox();
        mainHBox.setAlignment(Pos.CENTER);

        try {
            File file = new File(BACKGROUND_IMAGE_PATH);
            String localUrl = file.toURI().toURL().toString();
            Image backgroundImage = new Image(localUrl);
            mainHBox.setBackground(new Background(new BackgroundImage(backgroundImage,
                    null, null, null, null)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
  
        VBox leftVBox = new VBox();
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.setSpacing(20);

        Text addressText = new Text("Jl. Letjen S.Parman Kav.28 Jakarta Barat Lt. UG Unit 133, 11470, Jakarta");
        addressText.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        addressText.setFill(Color.BLACK);

        WebView webView = new WebView();
        webView.getEngine().loadContent("<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3966.654480573139!2d106.7885859745988!3d-6.176986993810404!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x2e69f6f5802f381d%3A0xe816d65037c3207a!2sCentral%20Park%20Mall!5e0!3m2!1sen!2sid!4v1702779346141!5m2!1sen!2sid\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>");
        webView.setPrefSize(450, 450);

        leftVBox.getChildren().addAll(addressText, webView);

        VBox rightVBox = new VBox();
        rightVBox.setAlignment(Pos.CENTER_LEFT);

        Label storesLabel = new Label("Stores Near You");
        storesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        storesLabel.setTextFill(Color.BLUE);

        rightVBox.getChildren().add(storesLabel);

        mainHBox.getChildren().addAll(leftVBox, rightVBox);

        ScrollPane scrollPane = new ScrollPane(mainHBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);
        borderPane.setStyle("-fx-background-color: white;");
        locationWindow.getContentPane().getChildren().add(borderPane);

        return locationWindow;
    }
}