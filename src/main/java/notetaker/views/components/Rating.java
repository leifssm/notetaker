package notetaker.views.components;

import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

public class Rating extends HBox {
  public Rating(int startingRating) {
    super();
    for (int i = 0; i < 5; i++) {
      Circle circle = new Circle(5);
      getChildren().add(circle);
    }
  }

}
