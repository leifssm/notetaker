package hoteller.controllers;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import hoteller.models.LoginHandler;
import hoteller.models.Router;
import hoteller.models.LoginHandler.LoginError;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
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
    try {
      loginHandler = new LoginHandler();
    } catch (FileNotFoundException e) {
      handleError(e);
    }
  }

  @FXML
  public void login() {
    String username = this.username.getText();
    String password = this.password.getText();
    try {
      loginHandler.login(username, password);
      Router.gotoRoute("/hotels");
    } catch (LoginError e) {
      handleError(e);
    }
  }

  private void handleError(Exception error) {
    feedback.setText(error.getMessage());
  }
}
