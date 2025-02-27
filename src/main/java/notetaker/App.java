package notetaker;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import notetaker.models.FileHandler;

public class App extends Application {
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Notetaker");
    Parent app = FXMLLoader.load(FileHandler.getFXMLResource("App.fxml"));
    Scene base = new Scene(app);
    primaryStage.setScene(base);
    primaryStage.show();
  }
}
