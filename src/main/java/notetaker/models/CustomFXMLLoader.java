package notetaker.models;

import java.net.URL;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import notetaker.controllers.BaseController;
import org.jetbrains.annotations.Nullable;

public class CustomFXMLLoader {
  static public @NotNull Parent loadFXML(@NotNull String fxml, @Nullable Globals globals) {
    URL url = FileHandler.getFXMLResource(fxml);
    if (url == null) {
      throw new IllegalArgumentException("Resource not found: " + fxml);
    }
    try {
      FXMLLoader loader = new FXMLLoader(url);
      Parent scene = loader.load();
      
      BaseController controller = loader.getController();
      if (controller == null) {
        System.err.println("Controller not found for: " + fxml);
      }
      if (controller != null && globals != null) {
        controller.setGlobals(globals);
      }

      return scene;
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to load resource: " + fxml, e);
    }
  }

  static public @NotNull Parent loadFXML(@NotNull String fxml) {
    return loadFXML(fxml, null);
  }
}
