package notetaker.controllers;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jetbrains.annotations.NotNull;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import notetaker.models.FileHandler;
import notetaker.models.Globals;
import notetaker.models.Router;

public class NotesController implements BaseController {
  private Globals globals;

  @FXML
  private Label userLabel;

  @FXML
  private TextArea content;

  @FXML
  private VBox notelist;

  private void initialize() {
    globals.user.subscribe(user -> userLabel.setText(user));
    renderNotes();
  }

  public void setGlobals(Globals globals) {
    this.globals = globals;
    initialize();
  }

  @FXML
  public void logOut() {
    globals.user.set(null);
    Router.gotoRoute("/login");
  }

  @FXML
  public void newNote() {
    
  }

  private List<String> getNotes() {
    return FileHandler.getFilesInDirectory("notes/" + this.globals.user.get() + "/",true);
  }

  private void renderNotes() {
    List<String> notes = getNotes();
    notelist.getChildren().clear();
    for (String note : notes) {
      Button button = new Button(note);
      button.setOnMouseClicked(event -> {
        String notePath = "notes/" + globals.user.get() + "/" + note + ".txt";
        try {
          String fileContent = FileHandler.readFile(notePath);
          content.setText(fileContent);
        } catch (FileNotFoundException e) {
          System.err.println("Could not find file: " + notePath);
          e.printStackTrace();
          renderNotes();
        }
      });
      notelist.getChildren().add(button);
    }
  }
}
