package classes;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Switch extends Entity{
    private static long serialVersionUID=108;
    @FXML
    private transient Arc arc1, arc2, arc3, arc4;
    private boolean collided;
    @FXML
    private transient Color colors[];

    public Switch(double x,double y) {
        super(x,y);
        colors=new Color[]{Color.CYAN,Color.DEEPPINK,Color.DARKVIOLET,Color.YELLOW};
        arc1 = new Arc();
        arc1.setCenterX(x);
        arc1.setCenterY(y);
        arc1.setRadiusX(15);
        arc1.setRadiusY(15);
        arc1.setStartAngle(90);
        arc1.setLength(92);
        arc1.setType(ArcType.ROUND);
        arc1.setFill(Color.CYAN);

        arc2 = new Arc();
        arc2.setCenterX(x);
        arc2.setCenterY(y);
        arc2.setRadiusX(15);
        arc2.setRadiusY(15);
        arc2.setStartAngle(180);
        arc2.setLength(92);
        arc2.setType(ArcType.ROUND);
        arc2.setFill(Color.DEEPPINK);

        arc3 = new Arc();
        arc3.setCenterX(x);
        arc3.setCenterY(y);
        arc3.setRadiusX(15);
        arc3.setRadiusY(15);
        arc3.setStartAngle(270);
        arc3.setLength(92);
        arc3.setType(ArcType.ROUND);
        arc3.setFill(Color.DARKVIOLET);

        arc4 = new Arc();
        arc4.setCenterX(x);
        arc4.setCenterY(y);
        arc4.setRadiusX(15);
        arc4.setRadiusY(15);
        arc4.setStartAngle(360);
        arc4.setLength(92);
        arc4.setType(ArcType.ROUND);
        arc4.setFill(Color.YELLOW);
        Group temp = new Group(arc1,arc2,arc3,arc4);
        collided=false;
        root.getChildren().addAll(temp);
    }

    @Override
    public boolean onCollide(Player player) {
        Shape intersection = Shape.intersect(player.getBall().getCircle(), arc1);
        if(!collided && !intersection.getBoundsInParent().isEmpty()){
            root.setVisible(false);
                Color c=player.getBall().getColor();
                int x= new Random().nextInt(4);
                while(c==colors[x]) {
                    x= new Random().nextInt(4);
                }
                player.getBall().setColor(colors[x]);
                collided = true;
            new Thread(() -> {
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("../resources/soundtracks/colorSwitcher.mp3").toString()));
                mediaPlayer.play();
            }).start();
            return true;
        }
        return false;
    }

    public boolean getStatus() {
        return this.collided;
    }
}
