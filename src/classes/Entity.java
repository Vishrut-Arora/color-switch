package classes;

import javafx.scene.Group;
import java.io.Serializable;

public abstract class Entity implements Serializable {
    private static final long serialVersionUID=110;
    private double x,y;
    private int color;
    protected transient Group root;

    public Entity(double x,double y) {
        this.x=x;
        this.y=y;
        root = new Group();
    }
    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public void setY(double val) {
        this.y=val;
    }

    public final Group getGroup() {
        return root;
    }

    public abstract boolean onCollide(Player player);
}
