package notetaker.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import notetaker.models.CustomFXMLLoader;
import notetaker.models.Globals;
import org.jetbrains.annotations.NotNull;

public class AppController implements Initializable, BaseController {
  private final Globals globals = new Globals();

  @FXML
  private BorderPane app;

  public void setGlobals(@NotNull Globals globals) {
    throw new RuntimeException("Can't set globals on the AppController");
  }

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    globals.router
        .viewProperty()
        .subscribe(app::setCenter);
    globals.router
        .routeProperty()
        .subscribe(route -> System.out.println("Navigating to: " + route));
    globals.router.addRoute("/login", () -> CustomFXMLLoader.loadFXML("login.fxml", globals));
    globals.router.addRoute("/register", () -> CustomFXMLLoader.loadFXML("register.fxml", globals));
    globals.router.addRoute("/notes", () -> CustomFXMLLoader.loadFXML("notes.fxml", globals));
    globals.router.gotoRoute("/login");
  }
}
