package classes;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.Random;


public class Star extends Entity{
    private static long serialVersionUID=108;
    @FXML
    private transient Polygon p, p2;
    private boolean collided;

    public Star(double x,double y) {
        super(x,y);
        collided=false;
        int rand = new Random().nextInt(49);
        p=new Polygon();
        p2=new Polygon();
        p.getPoints().setAll(new Double[]{
                x, (y-20),
                x-16, y+5,
                x+16, y+5 });
        p2.getPoints().setAll(new Double[]{
                x, (y+15),
                x-16, y-10,
                x+16, y-10 });
        p.setFill(Color.WHITE);
        p.setStrokeWidth(10);
        p2.setStrokeWidth(10);
        p2.setFill(Color.WHITE);
        if(rand==43) {
            p.setFill(Color.GOLD);
            p2.setFill(Color.GOLD);
        }
        else {
            p2.setFill(Color.WHITE);
            p.setFill(Color.WHITE);
        }
        collided=false;
        Group temp = new Group(p,p2);
        ScaleTransition scaleStar = new ScaleTransition(Duration.millis(750), temp);
        scaleStar.setToX(1.1);
        scaleStar.setToY(1.1);
        scaleStar.setAutoReverse(true);
        scaleStar.setCycleCount(ScaleTransition.INDEFINITE);
        scaleStar.play();
        root.getChildren().addAll(temp);
    }

    @Override
    public boolean onCollide(Player player) {
        Shape intersection = Shape.intersect(player.getBall().getCircle(), p);
        if(!collided && !intersection.getBoundsInParent().isEmpty()){
            root.setVisible(false);
            player.setStars(player.getStars()+1);
            if(p.getFill()==Color.GOLD)
                Game.getInstance().setGoldenStars(Game.getInstance().getGoldenStars()+1);
            new Thread(() -> {
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("../resources/soundtracks/starSound.mp3").toString()));
                mediaPlayer.play();
            }).start();
            collided=true;
            return  true;
        }
        return false;
    }

    public boolean getStatus() {
        return this.collided;
    }
}
