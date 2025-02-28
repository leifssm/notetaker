package notetaker.controllers;

import java.io.FileNotFoundException;
import java.net.FileNameMap;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import notetaker.models.FileAccessHandler;
import notetaker.models.FileHandler;
import notetaker.models.FileNamer;
import notetaker.models.Globals;
import notetaker.models.Router;
import notetaker.views.components.NoteItem;

public class NotesController implements BaseController, Initializable {
  private Globals globals;

  @FXML
  private Label userLabel;

  @FXML
  private TextArea content;

  @FXML
  private VBox notelist;

  @FXML
  private TextField fileName;

  @FXML
  private Button saveButton;

  private @Nullable FileAccessHandler fileAccessHandler;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    content.textProperty().subscribe(this::contentChanged);
    fileName.focusedProperty().addListener((observable, oldValue, focused) -> {
      if (!focused) {
        System.out.println("lost focus");
        renameNote(fileName.getText());
      }
    });
  }

  public void setGlobals(Globals globals) {
    this.globals = globals;
    globals.user.subscribe(this.userLabel::setText);
    setNote(null);
    renderNotes();
  }

  private @NotNull String getLocation(@Nullable String note) {
    if (note == null) {
      return "notes/" + globals.user.get() + "/";
    }
    return "notes/" + globals.user.get() + "/" + note;
  } 

  @FXML
  public void logOut() {
    globals.user.set(null);
    Router.gotoRoute("/login");
  }

  @FXML
  public void newNote() {
    String noteName = FileNamer.getValidName("New Note.txt", getNotes());
    if (noteName == null) {
      throw new RuntimeException("Could not create new note, error should not happen xd");
    }
    boolean success = FileHandler.createFile(getLocation(noteName));
    if (success) {
      setNote(noteName);
      renderNotes();
    }
  }

  public void setNote(@Nullable String name) {
    if (name == null) {
      fileAccessHandler = null;
      content.setText("Select a note to edit");
      fileName.setText("Select a note to edit");
      fileName.setDisable(true);
      setSaveable(false);
      return;
    }

    String notePath = getLocation(name);
    try {
      fileAccessHandler = new FileAccessHandler(notePath);
      content.setText(fileAccessHandler.getContent());
      fileName.setText(name);
      fileName.setDisable(false);
      setSaveable(false);
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file: " + notePath);
      e.printStackTrace();
      renderNotes();
    }
  }

  public void deleteNote(String name) {
    FileHandler.deleteFile(getLocation(name));
    if (fileAccessHandler != null && fileAccessHandler.getPath().equals(getLocation(name))) {
      fileAccessHandler = null;
      setNote(null);
    }
    renderNotes();
  }

  private void renameNote(String newName) {
    if (fileAccessHandler == null) {
      return;
    }
    boolean success = fileAccessHandler.rename(newName);
    System.out.println(success);
    if (success) {
      renderNotes();
      setNote(newName);
      fileName.setText(newName);
    } else {
      fileName.setText(fileAccessHandler.getName());
    }
  }

  public void contentChanged() {
    if (fileAccessHandler != null) {
      fileAccessHandler.setContent(content.getText());
      setSaveable(fileAccessHandler.hasChanged());
    } else {
      setSaveable(false);
    }
  }

  @FXML
  public void save() {
    setSaveable(false);
    if (fileAccessHandler != null) {
      fileAccessHandler.save();
    }
  }

  private void setSaveable(boolean saveable) {
    saveButton.setDisable(!saveable);
    saveButton.setText(saveable ? "Save 📝" : "Saved 💾");
    saveButton.setBackground(saveable ? Background.fill(Color.LIGHTGREEN) : Background.EMPTY);
  }

  private List<String> getNotes() {
    return FileHandler.getFilesInDirectory(getLocation(null), true);
  }

  private void renderNotes() {
    List<String> notes = getNotes();
    notelist
      .getChildren()
      .setAll(
        notes
          .stream()
          .map((note) -> new NoteItem(
            note,
            () -> setNote(note),
            () -> deleteNote(note)
          ))
          .toList()
      );
    
  }
}
