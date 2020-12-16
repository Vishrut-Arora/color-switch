package classes;


import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.shape.Line;

import java.util.Random;

public class Cross extends Obstacle{
    @FXML
    private transient final Line line1, line2, line3, line4;

    public Cross(double x,double y) {
        super(x,y);
        line1 =new Line();
        line2 =new Line();
        line3 =new Line();
        line4 =new Line();

        line1.setStartX(100);
        line1.setStartY(y);
        line1.setEndX(200);
        line1.setEndY(y);
        line1.setStroke(Color.CYAN);
        line1.setStrokeWidth(20);

        line2.setStartX(100);
        line2.setStartY(y);
        line2.setEndX(0);
        line2.setEndY(y);
        line2.setStroke(Color.DEEPPINK);
        line2.setStrokeWidth(20);

        line3.setStartX(100);
        line3.setStartY(y-100);
        line3.setEndX(100);
        line3.setEndY(y);
        line3.setStroke(Color.DARKVIOLET);
        line3.setStrokeWidth(20);

        line4.setStartX(100);
        line4.setStartY(y);
        line4.setEndX(100);
        line4.setEndY(y+100);
        line4.setStroke(Color.YELLOW);
        line4.setStrokeWidth(20);

        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        int dir[] = {360, -360};
        rotate.setByAngle(dir[new Random().nextInt(2)]);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setDuration(Duration.millis(4000));
        rotate.setInterpolator(Interpolator.LINEAR);
        Group temp = new Group(line1,line2,line3,line4);
        rotate.setNode(temp);
        rotate.play();
        root.getChildren().addAll(temp);
    }

    @Override
    public boolean onCollide(Player player) {
        boolean check=true;
        Shape b=player.getBall().getCircle();
        if(b.getFill()==line1.getStroke()){
            check=OnCollideUtil(b,line2,line3,line4);
        }
        if(b.getFill()==line2.getStroke()){
            check=OnCollideUtil(b,line1,line3,line4);
        }
        if(b.getFill()==line3.getStroke()){
            check=OnCollideUtil(b,line1,line2,line4);
        }
        if(b.getFill()==line4.getStroke()) {
            check=OnCollideUtil(b,line1,line2,line3);
        }
        if(check)
            super.onCollide(player);
        return check;
    }

    private boolean OnCollideUtil(Shape main,Shape a,Shape b, Shape c){
        Shape i1 = Shape.intersect(main,a);
        Shape i2= Shape.intersect(main,b);
        Shape i3 = Shape.intersect(main,c);
        if(i1.getBoundsInParent().isEmpty() && i2.getBoundsInParent().isEmpty() && i3.getBoundsInParent().isEmpty()){
            return false;
        }
        return true;
    }
}
