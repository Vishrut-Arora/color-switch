package classes;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Ball extends Entity{
    private static final long serialVersionUID=111;
    private transient Circle circle;
    private int yMotion, color;
    private transient Color colors[];

    public Ball(double x, double y) {
        super(x, y);
        colors= new Color[]{Color.CYAN, Color.DEEPPINK, Color.DARKVIOLET, Color.YELLOW};
        Color arr[]={Color.CYAN,Color.DEEPPINK,Color.DARKVIOLET,Color.YELLOW};
        color = new Random().nextInt(4);
        circle= new Circle(x, y, 12, arr[color]);
        y = circle.getCenterY();
        yMotion=0;
        root.getChildren().add(circle);
    }

    public Shape getCircle() {
        return circle;
    }

    @Override
    public void setY(double val){
        super.setY(val);
        circle.setCenterY(val);
    }

    public int getYMotion(){ return this.yMotion;}

    public void setYMotion(int val){ this.yMotion+=val;}

    @Override
    public boolean onCollide(Player player) {
        return false;
    }
    public Color getColor() {
        return (Color) circle.getFill();
    }

    public void setColorByIndex(int color) {
        circle.setFill(colors[color]);
    }

    public int getColorByIndex() {
        return color;
    }

    public void setColor(Color color) {
        circle.setFill(color);
    }

    public void jump(){
        if(yMotion>0){ yMotion=0; }
        yMotion=yMotion-9;
        new Thread(() -> {
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("../resources/soundtracks/ballJump.mp3").toString()));
            mediaPlayer.play();
        }).start();
    }
}
