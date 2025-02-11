package hoteller.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import hoteller.models.Router;
import hoteller.views.CustomFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class AppController implements Initializable {
  @FXML
  private BorderPane app;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    Router
      .viewProperty()
      .subscribe(view -> app.setCenter(view));
    Router.addRoute("/login", CustomFXMLLoader.loadFXML("login.fxml"));
    Router.addRoute("/hotels", CustomFXMLLoader.loadFXML("login.fxml"));
    Router.gotoRoute("/login");
  }
}
