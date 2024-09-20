package main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class VisionForm {

    private static VisionForm instance;

    public static VisionForm getInstance() {
        if (instance == null) {
            instance = new VisionForm();
        }
        return instance;
    }

    public Window getVisionFormWindow() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        try {
            FileInputStream inputStream = new FileInputStream("C:\\Users\\jov2r\\Downloads\\IWPC.gif");
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(500); 
            imageView.setPreserveRatio(true);
            hbox.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        VBox visionVBox = new VBox(10);
        visionVBox.setAlignment(Pos.CENTER);

        Text visionTitle = new Text("VISION");
        visionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text visionText = new Text("To be the premier destination for exquisite jewelry, inspiring confidence and celebrating life's precious moments with timeless elegance.");
        visionText.setFont(Font.font("Arial", 15));
        visionText.setWrappingWidth(400);
        visionText.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        visionVBox.getChildren().addAll(visionTitle, visionText);

       VBox missionVbox = new VBox(10);
        missionVbox.setAlignment(Pos.CENTER);

        Text missionTitle = new Text("MISSION");
        missionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        Text missionText = new Text(
                "Exceptional Craftsmanship: \"Deliver exceptional craftsmanship by creating meticulously designed and expertly crafted jewelry pieces that stand the test of time.\"\n\n"
                + "Customer-Centric Approach: \"Cultivate lasting relationships with our customers through personalized experiences, attentive service, and a deep understanding of their unique preferences.\"");
        missionText.setFont(Font.font("Arial", 15));
        missionText.setWrappingWidth(400);
        missionText.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);

        missionVbox.getChildren().addAll(missionTitle, missionText);

        HBox rootHBox = new HBox(20); 
        rootHBox.getChildren().addAll(hbox, visionVBox, missionVbox);
        rootHBox.setAlignment(Pos.CENTER);
        rootHBox.setStyle("-fx-background-color: white;");
   
        Window visionWindow = new Window("Glint&Grace Profile");
        visionWindow.getContentPane().getChildren().add(rootHBox);
      
        visionWindow.setPrefSize(1000, 500); 
       
        
        return visionWindow;
    }
}