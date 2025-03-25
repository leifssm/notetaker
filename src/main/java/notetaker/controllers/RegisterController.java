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

public class RegisterController implements Initializable, BaseController {
  private Globals globals;

  @FXML
  private TextField username;

  @FXML
  private TextField password;

  @FXML
  private TextField repeatPassword;

  @FXML
  private Label feedback;

  private LoginHandler loginHandler;

  @FXML
  public void initialize(URL location, ResourceBundle resources) {
    loginHandler = new LoginHandler("users.txt");
  }

  public void setGlobals(Globals globals) {
    this.globals = globals;
  }

  @FXML
  public void register() {
    String username = this.username.getText();
    String password = this.password.getText();
    String repeatPassword = this.repeatPassword.getText();

    if (!password.equals(repeatPassword)) {
      handleError("Passwords do not match");
      return;
    }
    try {
      loginHandler.register(username, password);
      globals.user.set(username);
      globals.router.gotoRoute("/notes");
    } catch (LoginError e) {
      handleError(e);
    }
  }

  @FXML
  public void gotoLogin() {
    globals.router.gotoRoute("/login");
  }

  private void handleError(Exception error) {
    handleError(error.getMessage());
  }

  private void handleError(String error) {
    feedback.setText(error);
  }
}
