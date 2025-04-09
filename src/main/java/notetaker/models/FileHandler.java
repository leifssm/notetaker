package notetaker.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import notetaker.App;

public class FileHandler {
  // Get Paths

  private static @NotNull String getAbstractAbsolutePath(@NotNull String filePath) {
    URL url = App.class.getResource("storage/");
    if (url == null) {
      throw new RuntimeException("Storage not found: /resources/notetaker/storage");
    }
    boolean shouldAddSlash = filePath.matches("(?:.*/)?\\w+");
    return url.getFile() + filePath + (shouldAddSlash ? "/" : "");
  }

  private static @Nullable String getAbsolutePath(@NotNull String filePath) {
    URL url = App.class.getResource("storage/" + filePath);
    try {
      return url != null ? url.toURI().getPath() : null;
    } catch (URISyntaxException e) {
      return null;
    }
  }

  // File pathing

  private static boolean isValidFilePath(@Nullable String filePath) {
    return filePath != null && !filePath.matches(".*/");
  }

  public static @Nullable String getValidFilePath(@NotNull String filePath) {
    String path = getAbstractAbsolutePath(filePath);
    return isValidFilePath(path) ? path : null;
  }

  public static @Nullable String getExistingFilePath(@NotNull String filePath) {
    String path = getAbsolutePath(filePath);
    return isValidFilePath(path) ? path : null;
  }

  public static boolean fileExists(@NotNull String filePath) {
    return getExistingFilePath(filePath) != null;
  }

  // Directory pathing

  private static boolean isValidDirectoryPath(@Nullable String directoryPath) {
    return directoryPath != null && directoryPath.matches(".*/");
  }

  public static @Nullable String getValidDirectoryPath(@NotNull String directoryPath) {
    String path = getAbstractAbsolutePath(directoryPath);
    return isValidDirectoryPath(path) ? path : null;
  }

  public static @Nullable String getExistingDirectoryPath(@NotNull String directoryPath) {
    String path = getAbsolutePath(directoryPath);
    return isValidDirectoryPath(path) ? path : null;
  }

  public static boolean directoryExists(@NotNull String directoryPath) {
    return getExistingDirectoryPath(directoryPath) != null;
  }

  // File operations

  public static @NotNull String readFile(@NotNull String filePath) throws FileNotFoundException {
    String path = getExistingFilePath(filePath);
    if (path == null) {
      throw new FileNotFoundException("File not found: " + filePath);
    }

    try {
      File file = new File(path);
      return Files.readString(file.toPath());
    } catch (IOException e) {
      throw new FileNotFoundException("Could not read file: " + filePath);
    }
  }

  public static void appendToFile(@NotNull String filePath, @NotNull String content) throws FileNotFoundException {
    String path = getExistingFilePath(filePath);
    if (path == null) {
      throw new FileNotFoundException("File not found: " + filePath);
    }

    File file = new File(path);

    try {
      Files.write(
          file.toPath(),
          content.getBytes(),
          StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException("Could not append to file: " + filePath);
    }
  }

  public static boolean createFile(@NotNull String filePath) {
    String path = getValidFilePath(filePath);
    if (path == null) {
      throw new IllegalArgumentException("Invalid file path: " + filePath);
    }
    File file = new File(path);
    file.getParentFile().mkdirs();

    try {
      return file.createNewFile();
    } catch (IOException e) {
      return false;
    }
  }

  public static void setFile(@NotNull String filePath, @NotNull String content) {
    createFile(filePath);

    String path = getValidFilePath(filePath);
    if (path == null) {
      throw new IllegalArgumentException("Invalid file path: " + filePath);
    }
    File file = new File(path);

    try {
      PrintWriter writer = new PrintWriter(file);
      writer.print(content);
      writer.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException("Could not write to file: " + filePath);
    }
  }

  public static void deleteFile(@NotNull String filePath) {
    String path = getExistingFilePath(filePath);
    if (path == null) {
      throw new IllegalArgumentException("File not found: " + filePath);
    }

    File file = new File(path);
    file.delete();
  }

  public static @Nullable String renameFile(@NotNull String filePath, @NotNull String newName) {
    String path = getExistingFilePath(filePath);
    
    if (path == null) {
      throw new IllegalArgumentException("Invalid file path: " + filePath);
    }

    File newPath = new File(path).toPath().resolveSibling(newName).toFile();
    if (newPath.exists()) {
      return null;
    }

    boolean success = new File(path).renameTo(newPath);
    if (success) {
      return newPath.toURI().getPath();
    }
    return null;
  }

  // Directory operations

  public static void createDirectory(@NotNull String directoryPath) {
    String path = getValidDirectoryPath(directoryPath);
    if (path == null) {
      throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
    }

    File directory = new File(path);
    directory.mkdirs();
  }

  public static @NotNull List<@NotNull String> getFilesInDirectory(@NotNull String directoryPath)
      throws FileNotFoundException {
    String path = getExistingDirectoryPath(directoryPath);
    if (path == null) {
      throw new FileNotFoundException("Directory not found: " + directoryPath);
    }

    File directory = new File(path);
    return List.of(directory.list());
  }

  public static @NotNull List<@NotNull String> getFilesInDirectory(@NotNull String directoryPath,
      boolean createMissing) {
    if (createMissing) {
      createDirectory(directoryPath);
    }

    try {
      return getFilesInDirectory(directoryPath);
    } catch (FileNotFoundException e) {
      return List.of();
    }
  }

  // Resource operations

  public static @NotNull URL getImageResource(String filePath) {
    URL url = App.class.getResource("images/" + filePath);
    if (url == null) {
      throw new RuntimeException("Resource not found: /resources/notetaker/images/" + filePath);
    }
    return url;
  }

  public static @NotNull URL getFXMLResource(String filePath) {
    URL url = App.class.getResource("views/" + filePath);
    if (url == null) {
      throw new RuntimeException("Resource not found: /resources/notetaker/views/" + filePath);
    }
    return url;
  }
}
