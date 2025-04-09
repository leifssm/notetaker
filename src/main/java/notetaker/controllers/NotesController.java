package notetaker.controllers;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import notetaker.models.*;
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

  private @NotNull NoteHandler noteHandler = new NoteHandler();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    content.textProperty().subscribe(this::contentChanged);
    fileName.focusedProperty().addListener((observable, oldValue, focused) -> {
      if (!focused) {
        renameNote(fileName.getText());
      }
    });
  }

  public void setGlobals(@NotNull Globals globals) {
    this.globals = globals;
    globals.user.subscribe(this.userLabel::setText);
    setNote(null);
    renderNotes();
  }

  @FXML
  public void logOut() {
    globals.user.set(null);
    globals.router.gotoRoute("/login");
  }

  @FXML
  public void newNote() {
    String fileName = noteHandler.createNewNote(globals.user.get());
    if (fileName != null) {
      setNote(fileName);
      renderNotes();
    }
  }

  public void setNote(@Nullable String name) {
    if (name == null) {
      noteHandler.closeNote();
      content.setText("Select a note to edit");
      content.setDisable(true);
      fileName.setText("Select a note to edit");
      fileName.setDisable(true);
      setSaveable(false);
      return;
    }


    try {
      noteHandler.openNote(globals.user.get(), name);
      content.setText(noteHandler.readCurrentNoteContent());

      content.setDisable(false);
      fileName.setText(name);
      fileName.setDisable(false);
      setSaveable(false);
    } catch (FileNotFoundException e) {
      System.err.println("Could not find file: " + name);
      renderNotes();
    }
  }

  public void deleteNote(String fileName) {
    boolean deletedCurrentOpenNote = noteHandler.deleteNote(globals.user.get(), fileName);

    if (deletedCurrentOpenNote) setNote(null);

    renderNotes();
  }

  public void renameNote(String newName) {
    String name = noteHandler.renameCurrentNote(newName);
    fileName.setText(name);
    if (name.equals(newName)) return;

    renderNotes();
    setNote(newName);
  }

  public void contentChanged() {
    boolean hasChanged = noteHandler.notifyContentChanged(content.getText());
    setSaveable(hasChanged);
  }

  @FXML
  public void save() {
    noteHandler.saveCurrentFile();
    setSaveable(false);
  }

  private void setSaveable(boolean saveable) {
    saveButton.setDisable(!saveable);
    saveButton.setText(saveable ? "Save 📝" : "Saved 💾");
    saveButton.setBackground(saveable ? Background.fill(Color.LIGHTGREEN) : Background.EMPTY);
  }

  private void renderNotes() {
    List<String> notes = noteHandler.getNotes(globals.user.get());
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
