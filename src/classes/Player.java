package classes;
import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID=101;
    private Ball ball;
    private int stars;

    public Player() {
        ball = new Ball(200, 570);
        stars=0;
    }

    public Ball getBall() {
        return ball;
    }
    public int getStars(){
        return this.stars;
    }
    public void setStars(int val){
        this.stars=val;
    }
}
