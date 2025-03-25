package notetaker;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import notetaker.models.CustomFXMLLoader;
import org.jetbrains.annotations.NotNull;

public class App extends Application {
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(@NotNull Stage primaryStage) throws IOException {
    primaryStage.setTitle("Notetaker");
    primaryStage.setScene(new Scene(CustomFXMLLoader.loadFXML("App.fxml")));
    primaryStage.show();
  }
}
