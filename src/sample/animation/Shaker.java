package sample.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shaker {
        TranslateTransition translateTransition;

    public Shaker(Node node) {
        translateTransition=new TranslateTransition(Duration.millis(50),node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(8f);
        translateTransition.setByZ(3f);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
    }

    public void shake(){
        translateTransition.play();
    }
}
