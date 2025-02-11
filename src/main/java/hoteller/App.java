package hoteller;

import java.io.FileReader;
import java.io.IOException;

import hoteller.models.FilePathManager;
import hoteller.views.CustomFXMLLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    primaryStage.setTitle("Hoteller.no");
    Parent app = FXMLLoader.load(FilePathManager.getFXMLUrl("App.fxml"));
    Scene base = new Scene(app);
    primaryStage.setScene(base);
    primaryStage.show();
  }
}
