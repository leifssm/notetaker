package notetaker.views;

import java.net.URL;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import notetaker.controllers.BaseController;
import notetaker.models.Globals;

public class CustomFXMLLoader {
  static public @NotNull Parent loadFXML(@NotNull String fxml, Globals globals) {
    URL url = CustomFXMLLoader.class.getResource(fxml);
    if (url == null) {
      throw new IllegalArgumentException("Resource not found: " + fxml);
    }
    try {
      FXMLLoader loader = new FXMLLoader(url);
      Parent scene = loader.load();
      
      BaseController controller = loader.getController();
      if (controller != null) {
        controller.setGlobals(globals);
      } else {
        System.err.println("Controller not found for: " + fxml);
      }
      
      return scene;
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to load resource: " + fxml, e);
    }
  }
}
