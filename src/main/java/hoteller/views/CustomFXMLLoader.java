package hoteller.views;

import java.net.URL;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class CustomFXMLLoader {
  static public @NotNull Parent loadFXML(@NotNull String fxml)  {
    URL url = CustomFXMLLoader.class.getResource(fxml);
    if (url == null) {
      throw new IllegalArgumentException("Resource not found: " + fxml);
    }
    try {
      return FXMLLoader.load(url);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to load resource: " + fxml, e);
    }
  }
}
