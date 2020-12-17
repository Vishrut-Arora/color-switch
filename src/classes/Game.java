package classes;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import java.io.*;
import java.util.ArrayList;

public class Game implements Serializable {
    private static final long serialVersionUID=100;
    private transient static Game game;
    @FXML
    private transient AnchorPane anchorPane;
    @FXML
    private transient ImageView helpButton, circleRotate1, circleRotate2, circleRotate3, circleRotate4,layer1, layer2, layer3, newGameButton;
    @FXML
    private transient ListView<String> listView;
    private int totalStars, highScore, goldenStars;
    private transient GameScreen currentGame;
    private ArrayList<GameScreen> gameScreens;
    @FXML
    private transient final Scene menu, savedGames;
    @FXML
    private transient static Stage primaryStage;
    @FXML
    private transient MediaPlayer mediaPlayer;

    private Game() throws IOException {
        gameScreens = new ArrayList<>();
        this.highScore=0;
        this.totalStars=0;
        this.goldenStars=0;
        FXMLLoader menuFXML = new FXMLLoader(getClass().getResource("../scenes/Menu.fxml"));
        menuFXML.setControllerFactory(c-> this);
        FXMLLoader savedGamesFXML = new FXMLLoader(getClass().getResource("../scenes/SavedGames.fxml"));
        savedGamesFXML.setControllerFactory(c-> this);
        savedGames = new Scene(savedGamesFXML.load(), 400, 800);
        menu = new Scene(menuFXML.load(), 400, 800);
        new Thread(() -> {
            Media sound = new Media(getClass().getResource("../resources/soundtracks/soundtrack1.mp3").toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);
            mediaPlayer.play();
        }).start();
        rotateTransition();
        scaleTransition();
        deSer();
        menu();
        game=this;
        primaryStage.show();
    }

    public static Game getInstance() {
        return game;
    }

    public static void setStage(Stage primaryStage) throws IOException {
        Game.primaryStage=primaryStage;
        if(game==null)
            game = new Game();
    }

    public void setTotalStars(int totalStars) {
        this.totalStars=totalStars;
    }

    public void setHighScore(int highScore) {
        this.highScore=highScore;
    }

    public int getTotalStars() {
        return  totalStars;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getGoldenStars() {return goldenStars;}

    public void setGoldenStars(int goldenStars) {this.goldenStars=goldenStars;}

    private void scaleTransition() {
        ScaleTransition scaleNewGameButton = new ScaleTransition(Duration.millis(750), newGameButton);
        scaleNewGameButton.setToX(1.1);
        scaleNewGameButton.setToY(1.1);
        scaleNewGameButton.setAutoReverse(true);
        scaleNewGameButton.setCycleCount(ScaleTransition.INDEFINITE);
        scaleNewGameButton.play();
    }

    private void rotateTransition() {
        RotateTransition rotate1 = new RotateTransition(Duration.seconds(2), circleRotate1);
        RotateTransition rotate2 = new RotateTransition(Duration.seconds(2), circleRotate2);
        rotate1.setByAngle(-360);
        rotate2.setByAngle(360);
        rotate1.setAxis(Rotate.Z_AXIS);
        rotate2.setAxis(Rotate.Z_AXIS);
        rotate1.setCycleCount(Timeline.INDEFINITE);
        rotate2.setCycleCount(Timeline.INDEFINITE);
        rotate1.setInterpolator(Interpolator.LINEAR);
        rotate2.setInterpolator(Interpolator.LINEAR);

        rotate1.play();
        rotate2.play();
        RotateTransition rotate3 = new RotateTransition(Duration.seconds(2), circleRotate3);
        RotateTransition rotate4 = new RotateTransition(Duration.seconds(2), circleRotate4);
        rotate3.setByAngle(-360);
        rotate4.setByAngle(360);
        rotate3.setAxis(Rotate.Z_AXIS);
        rotate4.setAxis(Rotate.Z_AXIS);
        rotate3.setCycleCount(Timeline.INDEFINITE);
        rotate4.setCycleCount(Timeline.INDEFINITE);
        rotate3.setInterpolator(Interpolator.LINEAR);
        rotate4.setInterpolator(Interpolator.LINEAR);

        rotate3.play();
        rotate4.play();
        RotateTransition rotateLayer1 = new RotateTransition(Duration.seconds(3), layer1);
        RotateTransition rotateLayer2 = new RotateTransition(Duration.seconds(3), layer2);
        RotateTransition rotateLayer3 = new RotateTransition(Duration.seconds(3), layer3);
        rotateLayer1.setByAngle(-360);
        rotateLayer2.setByAngle(360);
        rotateLayer3.setByAngle(-360);
        rotateLayer1.setAxis(Rotate.Z_AXIS);
        rotateLayer2.setAxis(Rotate.Z_AXIS);
        rotateLayer3.setAxis(Rotate.Z_AXIS);
        rotateLayer1.setCycleCount(Timeline.INDEFINITE);
        rotateLayer2.setCycleCount(Timeline.INDEFINITE);
        rotateLayer3.setCycleCount(Timeline.INDEFINITE);
        rotateLayer1.setInterpolator(Interpolator.LINEAR);
        rotateLayer2.setInterpolator(Interpolator.LINEAR);
        rotateLayer3.setInterpolator(Interpolator.LINEAR);
        rotateLayer1.play();
        rotateLayer2.play();
        rotateLayer3.play();

        RotateTransition rotateHelpButton = new RotateTransition(Duration.seconds(3), helpButton);
        rotateHelpButton.setByAngle(-360);
        rotateHelpButton.setAxis(Rotate.Z_AXIS);
        rotateHelpButton.setCycleCount(Timeline.INDEFINITE);
        rotateHelpButton.setInterpolator(Interpolator.LINEAR);
        rotateHelpButton.play();
    }

    public void playGame() {
        currentGame.play();
    }

    public void menu() {
        primaryStage.setScene(menu);
    }

    public void ser(boolean save) {
        try {
            if (save) {
                gameScreens.add(currentGame);
            }
            FileOutputStream file = new FileOutputStream("save.dat");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
            file.close();
        }
        catch(Exception ex) {
            System.out.println("Unable to save");
        }

    }

    public void deSer() {
        try {
            FileInputStream file = new FileInputStream("save.dat");
            ObjectInputStream in = new ObjectInputStream(file);
            Game temp = (Game) in.readObject();
            in.close();
            file.close();
            this.highScore = temp.highScore;
            this.totalStars = temp.totalStars;
            this.gameScreens = temp.gameScreens;
            this.goldenStars = temp.goldenStars;
            listView.getItems().clear();
            for(int i=0;i<gameScreens.size();i++) listView.getItems().add("Saved Game " + (i + 1));
        }
        catch(Exception ex) {
            System.out.println("Unable to load");
        }
    }

    @FXML
    public void resumeButtonClicked() {
        deSer();
        primaryStage.setScene(savedGames);
    }

    @FXML
    public void newGameButtonClicked() throws IOException {
        currentGame = new GameScreen(primaryStage);
        currentGame.generateRandomObstacle();
        playGame();
    }

    @FXML
    public void backButtonSGClicked() {
        menu();
    }

    @FXML
    public void exitButtonClicked() {
        System.exit(0);
    }

    @FXML
    public void gameChosen() throws IOException {
        ObservableList<Integer> selectedGames = listView.getSelectionModel().getSelectedIndices();
        if (!selectedGames.isEmpty()) {
            int selectedGame = selectedGames.get(0);
            currentGame = new GameScreen(primaryStage);
            currentGame.load(gameScreens.get(selectedGame));
            playGame();
        }
    }

    @FXML
    public void helpButtonClicked() {
        MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("../resources/help.mp4").toString()));
        player.setAutoPlay(false);
        player.setCycleCount(1);
        player.setVolume(0);
        MediaView mediaView = new MediaView(player);
        mediaView.setFitWidth(400);
        mediaView.setFitHeight(800);
        anchorPane.getChildren().add(mediaView);
        player.play();
        player.setOnEndOfMedia(()-> anchorPane.getChildren().remove(mediaView));
    }
}
