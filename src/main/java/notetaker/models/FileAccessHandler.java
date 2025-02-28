package notetaker.models;

import java.io.File;
import java.io.FileNotFoundException;

import org.jetbrains.annotations.NotNull;

public class FileAccessHandler {
  private @NotNull String fileContent = "";
  private @NotNull String cachedContent = "";

  private @NotNull String path;

  public FileAccessHandler(@NotNull String path) throws FileNotFoundException {
    fileContent = FileHandler.readFile(path);
    cachedContent = fileContent;
    this.path = path;
  }

  public boolean hasChanged() {
    return !fileContent.equals(cachedContent);
  }

  public void save() {
    FileHandler.setFile(path, cachedContent);
    fileContent = cachedContent;
  }

  public @NotNull String getContent() {
    return cachedContent;
  }

  public void setContent(@NotNull String content) {
    cachedContent = content;
  }

  public @NotNull String getPath() {
    return path;
  }

  public @NotNull String getName() {
    return new File(path).getName();
  }

  public boolean isValidFileName(@NotNull String name) {
    return name.matches("[a-zA-Z0-9 \\-_]+\\.txt");
  }

  public boolean rename(@NotNull String newName) {
    if (!isValidFileName(newName)) {
      return false;
    }
    File oldFile = new File(path);
    if (oldFile.getName().equals(newName)) {
      return false;
    }
    save();
    String newPath = FileHandler.renameFile(path, newName);
    if (newPath == null) {
      return false;
    }
    path = newPath;
    return true;
  }
}
