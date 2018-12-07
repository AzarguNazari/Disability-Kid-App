package Utilities;

import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Utilities {
    public static void FadeInTransition(Object pane){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode((Node) pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    public static void hideFadeOut(Object pane){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        ((Node)pane).setVisible(false);
        fadeTransition.setNode((Node) pane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();
    }
    
    public static final Predicate<String> isEmailCorrect = email -> Pattern.compile("[\\w._]+@\\w+(.\\w+)+").matcher(email).find();
}
