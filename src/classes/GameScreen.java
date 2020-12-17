package classes;

import javafx.animation.*;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GameScreen implements Serializable {
    private static final long serialVersionUID=102;
    @FXML
    private transient ImageView boostButton, pauseButton, circleRotate1, circleRotate2, replayButton, continueButton, circleRotate3, circleRotate4, circleRotate5, circleRotate6, circleRotate7, circleRotate8, logo, hand, resumeButton;
    @FXML
    private transient AnchorPane anchorPane;
    @FXML
    private transient Label goldenStarsLabel, scoreLabel, scoreLabel2, totalStarsLabel2,  totalStarsLabel, highScoreLabel;
    @FXML
    private transient Timeline timeline;
    private ArrayList<Entity> obstacles = new ArrayList<>();
    @FXML
    private transient final Group entities;
    private Player player;
    @FXML
    private transient final Stage primaryStage;
    @FXML
    private transient final Scene gameScreen, pauseScreen, endScreen, continueScreen;
    private boolean saveStatus;
    private double entitiesY;

    public GameScreen(@NamedArg("primaryStage") Stage primaryStage) throws IOException {
        this.primaryStage=primaryStage;
        player = new Player();
        FXMLLoader gameScreenFXML = new FXMLLoader(getClass().getResource("../scenes/GameScreen.fxml"));
        gameScreenFXML.setControllerFactory(c-> this);
        gameScreen = new Scene(gameScreenFXML.load(), 400, 800);
        FXMLLoader pauseScreenFXML = new FXMLLoader(getClass().getResource("../scenes/Pause.fxml"));
        pauseScreenFXML.setControllerFactory(c-> this);
        pauseScreen = new Scene(pauseScreenFXML.load(), 400, 800);
        FXMLLoader endScreenFXML = new FXMLLoader(getClass().getResource("../scenes/EndGame.fxml"));
        endScreenFXML.setControllerFactory(c-> this);
        endScreen = new Scene(endScreenFXML.load(), 400, 800);
        FXMLLoader continueScreenFXML = new FXMLLoader(getClass().getResource("../scenes/Continue.fxml"));
        continueScreenFXML.setControllerFactory(c-> this);
        continueScreen = new Scene(continueScreenFXML.load(), 400, 800);
        scaleTransition();
        rotateTransition();
        entities = new Group(hand,logo,circleRotate4,circleRotate3, boostButton);
        anchorPane.getChildren().addAll(entities, player.getBall().getGroup());
        entitiesY=0;
        setTimeline();
        scoreLabel.toFront();
        pauseButton.toFront();
        totalStarsLabel2.setText(Integer.toString(Game.getInstance().getTotalStars()));
    }

    private void setTimeline() {
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        TranslateTransition entitiesTransition = new TranslateTransition(Duration.seconds(0.7), entities);
        entitiesTransition.setInterpolator(Interpolator.LINEAR);
        AtomicInteger ticks= new AtomicInteger();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(20), e -> {
            ticks.getAndIncrement();
            if(ticks.get() %2==0&&player.getBall().getYMotion()<15) {
                player.getBall().setYMotion(2);
            }
            double y=(int)player.getBall().getY()+player.getBall().getYMotion();
            if(y>570 && player.getStars()==0){
                y=570;
            }
            if(y>800) {
                timeline.stop();
                exitButtonCSClicked();
            }
            player.getBall().setY(y);
            double finalY = y;
            gameScreen.setOnKeyPressed(k -> {
                if(k.getCode() == KeyCode.UP){ player.getBall().jump();
                }
            });
            if(finalY<=400){
                entitiesTransition.setByY(100);
                entitiesTransition.play();
            }
            Collision();
            if(updateScore())
                generateRandomObstacle();
        });
        timeline.getKeyFrames().add(keyFrame);
    }

    private boolean updateScore(){
        if(player.getStars()>Integer.parseInt(scoreLabel.getText())) {
            scoreLabel.setText(player.getStars() + "");
            scoreLabel2.setText(player.getStars() + "");
            totalStarsLabel2.setText(Game.getInstance().getTotalStars() + "");
            saveStatus=false;
            if(Integer.parseInt(scoreLabel.getText())>Integer.parseInt(highScoreLabel.getText())){
                highScoreLabel.setText(scoreLabel.getText());
            }
            return true;
        }
        return false;

    }

    private boolean getSaveStatus() {
        return saveStatus;
    }

    private void Collision(){
        Iterator iterator= obstacles.iterator();
        while(iterator.hasNext()) {
            Entity entity= (Entity) iterator.next();
            if (entity.onCollide(player) && entity.getClass() != Star.class && entity.getClass() != Switch.class) {
                if (Game.getInstance().getTotalStars() >= 10) {
                    timeline.pause();
                    primaryStage.setScene(continueScreen);
                } else
                    exitButtonCSClicked();
                break;
            }
        }
    }

    public void generateRandomObstacle() {
        Random rand=new Random();
        double x=200,y=270;
        for(int i=0;i<2;i++){
            Entity e = null;
            if(!obstacles.isEmpty()){
                x=obstacles.get(obstacles.size()-1).getX();
                y=obstacles.get(obstacles.size()-1).getY()-420;
                Entity temp = obstacles.get(obstacles.size()-1);
                if(temp.getClass()==Switch.class)
                    y+=200;
            }
            int num = rand.nextInt(Math.min(player.getStars()+1, 7)) + 1;
            if (num == 1)
                e = new Circle(x,y);
            else if (num == 4)
                e = new Rhombus(x,y);
            else if (num == 3)
                e = new LineInLine(x,y);
            else if (num == 5)
                e = new Wave(x,y);
            else if (num == 2)
                e = new Cross(x,y);
            else if(num==6)
                e = new CircleByCircle(x, y);
            else if(num==7)
                e = new CircleInCircle(x, y);
            obstacles.add(e);
            Star t = new Star(x, y);
            obstacles.add(t);
            Switch s = new Switch(x, y-200);
            obstacles.add(s);
            entities.getChildren().addAll(e.getGroup(), t.getGroup(), s.getGroup());
        }

    }
    private void boost(){
        AnimationTimer tm=new AnimationTimer() {
            @Override
            public void handle(long now) {
                for(Entity entity:obstacles)
                    entity.onCollide(player);
                if (updateScore())
                    generateRandomObstacle();
            }
        };
        tm.start();
        TranslateTransition entitiesTransition = new TranslateTransition(Duration.seconds(0.7), entities);
        entitiesTransition.setInterpolator(Interpolator.LINEAR);
        entitiesTransition.setDuration(Duration.seconds(3));
        entitiesTransition.setByY(3050);
        entitiesTransition.play();
        entitiesTransition.setOnFinished(event -> tm.stop());
    }

    private void scaleTransition() {
        ScaleTransition scaleReplayButton = new ScaleTransition(Duration.millis(750), replayButton);
        scaleReplayButton.setToX(1.1);
        scaleReplayButton.setToY(1.1);
        scaleReplayButton.setAutoReverse(true);
        scaleReplayButton.setCycleCount(ScaleTransition.INDEFINITE);
        scaleReplayButton.play();
        ScaleTransition scaleContinueButton = new ScaleTransition(Duration.millis(750), continueButton);
        scaleContinueButton.setToX(1.1);
        scaleContinueButton.setToY(1.1);
        scaleContinueButton.setAutoReverse(true);
        scaleContinueButton.setCycleCount(ScaleTransition.INDEFINITE);
        scaleContinueButton.play();
        ScaleTransition scalePlayButton = new ScaleTransition(Duration.millis(750), resumeButton);
        scalePlayButton.setToX(1.1);
        scalePlayButton.setToY(1.1);
        scalePlayButton.setAutoReverse(true);
        scalePlayButton.setCycleCount(ScaleTransition.INDEFINITE);
        scalePlayButton.play();
        ScaleTransition scaleBoostButton = new ScaleTransition(Duration.millis(750), boostButton);
        scaleBoostButton.setToX(1.1);
        scaleBoostButton.setToY(1.1);
        scaleBoostButton.setAutoReverse(true);
        scaleBoostButton.setCycleCount(ScaleTransition.INDEFINITE);
        scaleBoostButton.play();
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
        RotateTransition rotate5 = new RotateTransition(Duration.seconds(2), circleRotate5);
        RotateTransition rotate6 = new RotateTransition(Duration.seconds(2), circleRotate6);
        rotate5.setByAngle(-360);
        rotate6.setByAngle(360);
        rotate5.setAxis(Rotate.Z_AXIS);
        rotate6.setAxis(Rotate.Z_AXIS);
        rotate5.setCycleCount(Timeline.INDEFINITE);
        rotate6.setCycleCount(Timeline.INDEFINITE);
        rotate5.setInterpolator(Interpolator.LINEAR);
        rotate6.setInterpolator(Interpolator.LINEAR);
        rotate5.play();
        rotate6.play();
        RotateTransition rotate7 = new RotateTransition(Duration.seconds(2), circleRotate7);
        RotateTransition rotate8 = new RotateTransition(Duration.seconds(2), circleRotate8);
        rotate7.setByAngle(-360);
        rotate8.setByAngle(360);
        rotate7.setAxis(Rotate.Z_AXIS);
        rotate8.setAxis(Rotate.Z_AXIS);
        rotate7.setCycleCount(Timeline.INDEFINITE);
        rotate8.setCycleCount(Timeline.INDEFINITE);
        rotate7.setInterpolator(Interpolator.LINEAR);
        rotate8.setInterpolator(Interpolator.LINEAR);
        rotate7.play();
        rotate8.play();
    }

    public void play() {
        gameScreen.setOnKeyPressed(k -> {
            if(k.getCode() == KeyCode.UP){ timeline.play();
            }
        });
        boostButton.setVisible(Game.getInstance().getGoldenStars() > 0);
        primaryStage.setScene(gameScreen);
    }

    public void save() {
        if(!saveStatus) {
            Game.getInstance().ser(true);
            saveStatus=true;
        }
    }

    public void pause() {
        timeline.pause();
        primaryStage.setScene(pauseScreen);
    }

    public void resume() {
        gameScreen.setOnKeyPressed(k -> {
            if(k.getCode() == KeyCode.UP){ timeline.play();
            }
        });
        primaryStage.setScene(gameScreen);
    }

    public Player getPlayer() {
        return this.player;
    }

    public double getEntitiesY() {
        return entitiesY;
    }

    public void load(GameScreen gameScreen) {
        this.player.getBall().setY(gameScreen.getPlayer().getBall().getY());
        this.player.getBall().setColorByIndex(gameScreen.getPlayer().getBall().getColorByIndex());
        this.player.setStars(gameScreen.getPlayer().getStars());
        this.entitiesY=gameScreen.getEntitiesY();
        this.saveStatus=gameScreen.getSaveStatus();
        updateScore();
        Iterator iterator =gameScreen.obstacles.iterator();
        while(iterator.hasNext()){
            Entity e= null;
            Entity obstacle= (Entity) iterator.next();
            if(obstacle.getClass()==Circle.class)
                e = new Circle(obstacle.getX(), obstacle.getY());
            else if(obstacle.getClass()==Cross.class)
                e = new Cross(obstacle.getX(), obstacle.getY());
            else if(obstacle.getClass()==LineInLine.class)
                e = new LineInLine(obstacle.getX(), obstacle.getY());
            else if(obstacle.getClass()==Rhombus.class)
                e = new Rhombus(obstacle.getX(), obstacle.getY());
            else if(obstacle.getClass()==Wave.class)
                e = new Wave(obstacle.getX(), obstacle.getY());
            else if(obstacle.getClass()==Star.class) {
                if(!((Star) obstacle).getStatus())
                    e = new Star(obstacle.getX(), obstacle.getY());
            }
            else if(obstacle.getClass()==Switch.class) {
                if(!((Switch) obstacle).getStatus())
                    e = new Switch(obstacle.getX(), obstacle.getY());
            }
            else if(obstacle.getClass()==CircleByCircle.class) {
                    e = new CircleByCircle(obstacle.getX(), obstacle.getY());
            }
            else if(obstacle.getClass()==CircleInCircle.class) {
                    e = new CircleInCircle(obstacle.getX(), obstacle.getY());
            }
            if(e!=null) {
                obstacles.add(e);
                entities.getChildren().add(e.getGroup());
            }
        }
        entities.setTranslateY(entitiesY);
    }

    @FXML
    public void pauseButtonClicked() {
        pause();
    }

    @FXML
    public void homeButtonClicked() {
        Game.getInstance().menu();
    }

    @FXML
    public void resumeButtonClicked() {
        resume();
    }

    @FXML
    public void saveGameButtonClicked() {
        entitiesY=entities.getTranslateY();
        save();
    }

    @FXML
    public void exitButtonCSClicked() {
        timeline.stop();
        Game.getInstance().setTotalStars(Game.getInstance().getTotalStars()+ player.getStars());
        Game.getInstance().setHighScore(Math.max(Game.getInstance().getHighScore(), player.getStars()));
        totalStarsLabel.setText(Integer.toString(Game.getInstance().getTotalStars()));
        highScoreLabel.setText(Integer.toString(Game.getInstance().getHighScore()));
        goldenStarsLabel.setText(Integer.toString(Game.getInstance().getGoldenStars()));
        Game.getInstance().ser(false);
        primaryStage.setScene(endScreen);
    }

    @FXML
    private void replayButtonClicked() throws IOException {
        Game.getInstance().newGameButtonClicked();
    }

    @FXML
    public void continueButtonClicked() {
        Game.getInstance().setTotalStars(Game.getInstance().getTotalStars()-10);
        totalStarsLabel2.setText(Integer.toString(Game.getInstance().getTotalStars()));
        resume();
    }

    @FXML
    public void boostButtonClicked() {
        timeline.pause();
        boost();
        gameScreen.setOnKeyPressed(k -> {
            if(k.getCode() == KeyCode.UP){ timeline.play();
            }
        });
        Game.getInstance().setGoldenStars(Game.getInstance().getGoldenStars()-1);
    }
}
