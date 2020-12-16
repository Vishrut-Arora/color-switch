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

public class CircleByCircle extends Obstacle{
    @FXML
    private transient final Arc arc1, arc2, arc3, arc4, arc5, arc6,arc7,arc8;

    public CircleByCircle(double x,double y)  {
        super(x,y);

        arc1 = new Arc();
        arc1.setCenterX(x-84);
        arc1.setCenterY(y-10);
        arc1.setRadiusX(80);
        arc1.setRadiusY(80);
        arc1.setStartAngle(90);
        arc1.setLength(80);
        arc1.setType(ArcType.OPEN);
        arc1.setStrokeWidth(14);
        arc1.setFill(null);
        arc1.setStroke(Color.CYAN);

        arc2 = new Arc();
        arc2.setCenterX(x-84);
        arc2.setCenterY(y-10);
        arc2.setRadiusX(80);
        arc2.setRadiusY(80);
        arc2.setStartAngle(180);
        arc2.setLength(80);
        arc2.setType(ArcType.OPEN);
        arc2.setStrokeWidth(14);
        arc2.setFill(null);
        arc2.setStroke(Color.DEEPPINK);

        arc3 = new Arc();
        arc3.setCenterX(x-84);
        arc3.setCenterY(y-10);
        arc3.setRadiusX(80);
        arc3.setRadiusY(80);
        arc3.setStartAngle(270);
        arc3.setLength(80);
        arc3.setType(ArcType.OPEN);
        arc3.setStrokeWidth(14);
        arc3.setFill(null);
        arc3.setStroke(Color.DARKVIOLET);

        arc4 = new Arc();
        arc4.setCenterX(x-86);
        arc4.setCenterY(y-10);
        arc4.setRadiusX(80);
        arc4.setRadiusY(80);
        arc4.setStartAngle(360);
        arc4.setLength(80);
        arc4.setType(ArcType.OPEN);
        arc4.setStrokeWidth(14);
        arc4.setFill(null);
        arc4.setStroke(Color.YELLOW);

        arc5 = new Arc();
        arc5.setCenterX(x+86);
        arc5.setCenterY(y-10);
        arc5.setRadiusX(80);
        arc5.setRadiusY(80);
        arc5.setStartAngle(100);
        arc5.setLength(80);
        arc5.setType(ArcType.OPEN);
        arc5.setStrokeWidth(14);
        arc5.setFill(null);
        arc5.setStroke(Color.YELLOW);

        arc6 = new Arc();
        arc6.setCenterX(x+86);
        arc6.setCenterY(y-10);
        arc6.setRadiusX(80);
        arc6.setRadiusY(80);
        arc6.setStartAngle(190);
        arc6.setLength(80);
        arc6.setType(ArcType.OPEN);
        arc6.setStrokeWidth(14);
        arc6.setFill(null);
        arc6.setStroke(Color.DARKVIOLET);

        arc7 = new Arc();
        arc7.setCenterX(x+86);
        arc7.setCenterY(y-10);
        arc7.setRadiusX(80);
        arc7.setRadiusY(80);
        arc7.setStartAngle(280);
        arc7.setLength(80);
        arc7.setType(ArcType.OPEN);
        arc7.setStrokeWidth(14);
        arc7.setFill(null);
        arc7.setStroke(Color.DEEPPINK);

        arc8 = new Arc();
        arc8.setCenterX(x+86);
        arc8.setCenterY(y-10);
        arc8.setRadiusX(80);
        arc8.setRadiusY(80);
        arc8.setStartAngle(370);
        arc8.setLength(80);
        arc8.setType(ArcType.OPEN);
        arc8.setStrokeWidth(14);
        arc8.setFill(null);
        arc8.setStroke(Color.CYAN);

        RotateTransition rotate1 = new RotateTransition();
        RotateTransition rotate2 = new RotateTransition();

        rotate1.setAxis(Rotate.Z_AXIS);
        rotate2.setAxis(Rotate.Z_AXIS);

        rotate1.setByAngle(-360);
        rotate1.setCycleCount(RotateTransition.INDEFINITE);
        rotate1.setDuration(Duration.millis(5000));
        rotate1.setInterpolator(Interpolator.LINEAR);

        rotate2.setByAngle(360);
        rotate2.setCycleCount(RotateTransition.INDEFINITE);
        rotate2.setDuration(Duration.millis(5000));
        rotate2.setInterpolator(Interpolator.LINEAR);

        Group temp = new Group(arc1,arc2,arc3,arc4);
        Group temp2 = new Group(arc5,arc6,arc7,arc8);
        rotate1.setNode(temp);
        rotate2.setNode(temp2);
        rotate1.play();
        rotate2.play();
        root.getChildren().addAll(temp, temp2);
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
        if(b.getFill()==arc5.getStroke()){
            check=OnCollideUtil(b,arc6,arc7,arc8);
        }
        if(b.getFill()==arc6.getStroke()){
            check=OnCollideUtil(b,arc5,arc7,arc8);
        }
        if(b.getFill()==arc7.getStroke()){
            check=OnCollideUtil(b,arc5,arc6,arc8);
        }
        if(b.getFill()==arc8.getStroke()) {
            check=OnCollideUtil(b,arc5,arc6,arc7);
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
