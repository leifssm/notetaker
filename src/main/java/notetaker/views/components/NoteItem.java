package notetaker.views.components;

import org.jetbrains.annotations.NotNull;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class NoteItem extends BorderPane {
  public NoteItem(@NotNull String label, @NotNull Runnable onClick, @NotNull Runnable onDelete) {
    super();
    setPrefWidth(130);

    Button main = new Button();
    main.setMnemonicParsing(false);
    main.setText(label);
    main.setPrefWidth(200);
    main.setOnMouseClicked((event) -> onClick.run());
    main.setCursor(Cursor.HAND);
    main.setAlignment(Pos.BASELINE_LEFT);

    Button delete = new Button("🗑️");
    delete.setBackground(Background.fill(Color.RED));
    delete.setOnMouseClicked((event) -> onDelete.run());
    delete.setCursor(Cursor.HAND);

    setCenter(main);
    setRight(delete);
  }
}
