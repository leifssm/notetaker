package notetaker.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import notetaker.models.Globals;
import notetaker.models.Router;
import notetaker.views.CustomFXMLLoader;

public class AppController implements Initializable, BaseController {
  private Globals globals = new Globals();

  @FXML
  private BorderPane app;

  public void setGlobals(Globals globals) {
    throw new RuntimeException("Can't set globals on the AppController");
  }

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    Router
        .viewProperty()
        .subscribe(view -> app.setCenter(view));
    Router
        .routeProperty()
        .subscribe(route -> {
          System.out.println("Navigating to: " + route);
        });
    Router.addRoute("/login", () -> CustomFXMLLoader.loadFXML("login.fxml", globals));
    Router.addRoute("/register", () -> CustomFXMLLoader.loadFXML("register.fxml", globals));
    Router.addRoute("/notes", () -> CustomFXMLLoader.loadFXML("notes.fxml", globals));
    Router.gotoRoute("/login");
  }
}
