package classes;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Color Switch");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("../resources/icon.jpg")));
        Game.setStage(primaryStage);
        Game.getInstance();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
