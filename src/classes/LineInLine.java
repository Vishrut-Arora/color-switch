package classes;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.scene.shape.Line;

public class LineInLine extends Obstacle{
    @FXML
    private transient final Line line1, line2, line3, line4, line5, line6, line7, line8;

    public LineInLine(double x,double y) {
        super(x,y);
        line1 =new Line();
        line2 =new Line();
        line3 =new Line();
        line4 =new Line();
        line5 =new Line();
        line6 =new Line();
        line7 =new Line();
        line8 =new Line();

        line1.setStartX(95);
        line1.setStartY(y);
        line1.setEndX(0);
        line1.setEndY(y);
        line1.setStroke(Color.CYAN);
        line1.setStrokeWidth(15);

        line2.setStartX(190);
        line2.setStartY(y);
        line2.setEndX(95);
        line2.setEndY(y);
        line2.setStroke(Color.DEEPPINK);
        line2.setStrokeWidth(15);

        line3.setStartX(285);
        line3.setStartY(y);
        line3.setEndX(190);
        line3.setEndY(y);
        line3.setStroke(Color.DARKVIOLET);
        line3.setStrokeWidth(15);

        line4.setStartX(380);
        line4.setStartY(y);
        line4.setEndX(285);
        line4.setEndY(y);
        line4.setStroke(Color.YELLOW);
        line4.setStrokeWidth(15);

        line5.setStartX(0);
        line5.setStartY(y);
        line5.setEndX(-95);
        line5.setEndY(y);
        line5.setStroke(Color.YELLOW);
        line5.setStrokeWidth(15);

        line6.setStartX(-95);
        line6.setStartY(y);
        line6.setEndX(-190);
        line6.setEndY(y);
        line6.setStroke(Color.DARKVIOLET);
        line6.setStrokeWidth(15);

        line7.setStartX(-190);
        line7.setStartY(y);
        line7.setEndX(-285);
        line7.setEndY(y);
        line7.setStroke(Color.DEEPPINK);
        line7.setStrokeWidth(15);

        line8.setStartX(-285);
        line8.setStartY(y);
        line8.setEndX(-380);
        line8.setEndY(y);
        line8.setStroke(Color.CYAN);
        line8.setStrokeWidth(15);

        Group temp = new Group(line1,line2,line3,line4,line5,line6,line7,line8);
        TranslateTransition translate=new TranslateTransition();
        translate.setInterpolator(Interpolator.LINEAR);
        translate.setByX(395);
        translate.setDuration(Duration.millis(3000));
        translate.setCycleCount(Timeline.INDEFINITE);
        translate.setNode(temp);
        translate.play();
        root.getChildren().addAll(temp);
    }

    @Override
    public boolean onCollide(Player player) {
        boolean check=true;
        Shape b=player.getBall().getCircle();

        if(b.getFill()==line1.getStroke()){
            check=OnCollideUtil(b,line2,line3,line4,line5,line6,line7);
        }
        if(b.getFill()==line2.getStroke()){
            check=OnCollideUtil(b,line1,line3,line4,line5,line6,line8);
        }
        if(b.getFill()==line3.getStroke()){
            check=OnCollideUtil(b,line1,line2,line4,line5,line7,line8);
        }
        if(b.getFill()==line4.getStroke()) {
            check=OnCollideUtil(b,line1,line2,line3,line6,line7,line8);
        }
        if(check)
            super.onCollide(player);
        return check;
    }

    private boolean OnCollideUtil(Shape main,Shape a,Shape b, Shape c,Shape d,Shape e,Shape f){
        Shape i1 = Shape.intersect(main,a);
        Shape i2= Shape.intersect(main,b);
        Shape i3 = Shape.intersect(main,c);
        Shape i4 = Shape.intersect(main,d);
        Shape i5= Shape.intersect(main,e);
        Shape i6 = Shape.intersect(main,f);
        if(i1.getBoundsInParent().isEmpty() && i2.getBoundsInParent().isEmpty() && i3.getBoundsInParent().isEmpty() && i4.getBoundsInParent().isEmpty() && i5.getBoundsInParent().isEmpty() && i6.getBoundsInParent().isEmpty()){
            return false;
        }
        return true;
    }
}
