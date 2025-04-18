package notetaker.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import notetaker.models.Globals;
import notetaker.models.LoginHandler;
import notetaker.models.LoginHandler.LoginError;
import org.jetbrains.annotations.NotNull;

public class LoginController implements Initializable, BaseController {
  private Globals globals;

  @FXML
  private TextField username;

  @FXML
  private TextField password;

  @FXML
  private Label feedback;

  @FXML
  private LoginHandler loginHandler;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    loginHandler = new LoginHandler("users.txt");
  }

  public void setGlobals(@NotNull Globals globals) {
    this.globals = globals;
  }

  @FXML
  public void login() {
    String username = this.username.getText();
    String password = this.password.getText();
    try {
      loginHandler.login(username, password);
      globals.user.set(username);
      globals.router.gotoRoute("/notes");
    } catch (LoginError e) {
      handleError(e);
    }
  }

  @FXML
  public void gotoRegister() {
    globals.router.gotoRoute("/register");
  }

  private void handleError(Exception error) {
    handleError(error.getMessage());
  }

  private void handleError(String error) {
    feedback.setText(error);
  }
}
