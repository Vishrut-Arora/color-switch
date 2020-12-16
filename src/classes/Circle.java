package classes;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

public class Circle extends Obstacle{
    @FXML
    private transient final Arc arc1, arc2, arc3, arc4;

    public Circle(double x,double y)  {
        super(x,y);
        arc1 = new Arc();
        arc1.setCenterX(x);
        arc1.setCenterY(y);
        arc1.setRadiusX(120);
        arc1.setRadiusY(120);
        arc1.setStartAngle(90);
        arc1.setLength(80);
        arc1.setType(ArcType.OPEN);
        arc1.setStrokeWidth(20);
        arc1.setFill(null);
        arc1.setStroke(Color.CYAN);

        arc2 = new Arc();
        arc2.setCenterX(x);
        arc2.setCenterY(y);
        arc2.setRadiusX(120);
        arc2.setRadiusY(120);
        arc2.setStartAngle(180);
        arc2.setLength(80);
        arc2.setType(ArcType.OPEN);
        arc2.setStrokeWidth(20);
        arc2.setFill(null);
        arc2.setStroke(Color.DEEPPINK);

        arc3 = new Arc();
        arc3.setCenterX(x);
        arc3.setCenterY(y);
        arc3.setRadiusX(120);
        arc3.setRadiusY(120);
        arc3.setStartAngle(270);
        arc3.setLength(80);
        arc3.setType(ArcType.OPEN);
        arc3.setStrokeWidth(20);
        arc3.setFill(null);
        arc3.setStroke(Color.DARKVIOLET);

        arc4 = new Arc();
        arc4.setCenterX(x);
        arc4.setCenterY(y);
        arc4.setRadiusX(120);
        arc4.setRadiusY(120);
        arc4.setStartAngle(360);
        arc4.setLength(80);
        arc4.setType(ArcType.OPEN);
        arc4.setStrokeWidth(20);
        arc4.setFill(null);
        arc4.setStroke(Color.YELLOW);

        Group temp = new Group(arc1, arc2, arc3, arc4);

        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        int dir[] = {360, -360};
        rotate.setByAngle(dir[new Random().nextInt(2)]);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setDuration(Duration.millis(4000));
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setNode(temp);
        rotate.play();
        root.getChildren().addAll(temp);
    }

    public boolean onCollide(Player player) {
        boolean check=true;
        Shape b=player.getBall().getCircle();
        if(b.getFill()==arc1.getStroke()){
            check=OnCollideUtil(b,arc2,arc3,arc4);
        }
        if(b.getFill()==arc2.getStroke()){
            check=OnCollideUtil(b,arc1,arc3,arc4);
        }
        if(b.getFill()==arc3.getStroke()){
            check=OnCollideUtil(b,arc1,arc2,arc4);
        }
        if(b.getFill()==arc4.getStroke()) {
            check=OnCollideUtil(b,arc1,arc2,arc3);
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
