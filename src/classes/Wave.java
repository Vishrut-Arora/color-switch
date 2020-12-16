package classes;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class Wave extends Obstacle{
    @FXML
    private transient Line line1, line2, line3, line4, line5, line6, line7, line8;

    public Wave(double x,double y) {
        super(x,y);
        line1 =new Line();
        line2 =new Line();
        line3 =new Line();
        line4 =new Line();
        line5 =new Line();
        line6 =new Line();
        line7 =new Line();
        line8 =new Line();

        line1.setStartX(16);
        line1.setStartY(y-130);
        line1.setEndX(16);
        line1.setEndY(y-10);
        line1.setStroke(Color.CYAN);
        line1.setStrokeWidth(15);

        line2.setStartX(66);
        line2.setStartY(y-135);
        line2.setEndX(66);
        line2.setEndY(y-5);
        line2.setStroke(Color.DEEPPINK);
        line2.setStrokeWidth(15);

        line3.setStartX(116);
        line3.setStartY(y-140);
        line3.setEndX(116);
        line3.setEndY(y);
        line3.setStroke(Color.DARKVIOLET);
        line3.setStrokeWidth(15);

        line4.setStartX(166);
        line4.setStartY(y-100);
        line4.setEndX(166);
        line4.setEndY(y-40);
        line4.setStroke(Color.YELLOW);
        line4.setStrokeWidth(15);

        line5.setStartX(216);
        line5.setStartY(y-145);
        line5.setEndX(216);
        line5.setEndY(y);
        line5.setStroke(Color.YELLOW);
        line5.setStrokeWidth(15);

        line6.setStartX(266);
        line6.setStartY(y-105);
        line6.setEndX(266);
        line6.setEndY(y-35);
        line6.setStroke(Color.DARKVIOLET);
        line6.setStrokeWidth(15);

        line7.setStartX(316);
        line7.setStartY(y-115);
        line7.setEndX(316);
        line7.setEndY(y-25);
        line7.setStroke(Color.DEEPPINK);
        line7.setStrokeWidth(15);

        line8.setStartX(366);
        line8.setStartY(y-105);
        line8.setEndX(366);
        line8.setEndY(y-35);
        line8.setStroke(Color.CYAN);
        line8.setStrokeWidth(15);

        Group temp = new Group(line2,line4,line6,line8);
        Group temp2=new Group(line1,line3,line5,line7);

        TranslateTransition translate=new TranslateTransition();
        translate.setInterpolator(Interpolator.LINEAR);
        translate.setByX(-250);
        translate.setDuration(Duration.millis(5000));
        translate.setCycleCount(Timeline.INDEFINITE);
        translate.setNode(temp);
        translate.setAutoReverse(true);
        translate.play();

        TranslateTransition translate2=new TranslateTransition();
        translate2.setInterpolator(Interpolator.LINEAR);

        translate2.setByX(200);
        translate2.setDuration(Duration.millis(5000));
        translate2.setCycleCount(Timeline.INDEFINITE);
        translate2.setNode(temp2);
        translate2.setAutoReverse(true);
        translate2.play();
        root.getChildren().addAll(temp, temp2);
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
