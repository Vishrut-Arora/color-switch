package classes;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Obstacle extends Entity {
    private static final long serialVersionUID=109;

    public Obstacle(double x,double y) {
        super(x,y);
    }

    @Override
    public boolean onCollide(Player player) {
        new Thread(() -> {
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("../resources/soundtracks/obstacleHit.mp3").toString()));
            mediaPlayer.play();
        }).start();
        return true;
    }


}
