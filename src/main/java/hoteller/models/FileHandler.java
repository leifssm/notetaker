package hoteller.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import hoteller.App;

public class FileHandler {
  public static void main(String[] args) throws FileNotFoundException {
    // String path = "users.txt";
    String path = "notes/gunnar/test/test.txt";
    // System.out.println("force : " + getAbstractAbsolutePath(path));
    // System.out.println("soft  : " + getAbsolutePath(path));
    // System.out.println("isDir : " + directoryExists(path));
    // System.out.println("isFile: " + fileExists(path));
    appendToFile(path, "Hello, World!");
    System.out.println(readFile(path));
  }

  // Get Paths

  private static @NotNull String getAbstractAbsolutePath(@NotNull String path) {
    URL url = App.class.getResource("storage/");
    if (url == null) {
      throw new RuntimeException("Storage not found: /resources/hoteller/storage");
    }
    boolean shouldAddSlash = path.matches("(?:.*\\/)?\\w+");
    return url.getFile() + path + (shouldAddSlash ? "/" : "");
  }

  private static @Nullable String getAbsolutePath(@NotNull String path) {
    URL url = App.class.getResource("storage/" + path);
    return url != null ? url.getFile() : null;
  }

  // File pathing

  private static boolean isValidFilePath(@Nullable String path) {
    return path != null && !path.matches(".*/");
  }

  public static @Nullable String getValidFilePath(@NotNull String filename) {
    String path = getAbstractAbsolutePath(filename);
    return isValidFilePath(path) ? path : null;
  }

  public static @Nullable String getExistingFilePath(@NotNull String filename) {
    String path = getAbsolutePath(filename);
    return isValidFilePath(path) ? path : null;
  }

  public static boolean fileExists(@NotNull String filename) {
    return getExistingFilePath(filename) != null;
  }

  // Directory pathing

  private static boolean isValidDirectoryPath(@Nullable String path) {
    return path != null && path.matches(".*/");
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

  public static @NotNull String readFile(@NotNull String filename) throws FileNotFoundException {
    String path = getExistingFilePath(filename);
    if (path == null) {
      throw new FileNotFoundException("File not found: " + filename);
    }

    File file = new File(path);
    try {
      return Files.readString(file.toPath());
    } catch (IOException e) {
      throw new FileNotFoundException("Could not read file: " + filename);
    }
  }

  public static void appendToFile(@NotNull String filename, @NotNull String content) throws FileNotFoundException {
    String path = getExistingFilePath(filename);
    if (path == null) {
      throw new FileNotFoundException("File not found: " + filename);
    }

    File file = new File(path);

    try {
      Files.write(
        file.toPath(),
        content.getBytes(),
        StandardOpenOption.APPEND
      );
    } catch (IOException e) {
      throw new RuntimeException("Could not append to file: " + filename);
    }
  }

  public static boolean createFile(@NotNull String filename) {
    String path = getValidFilePath(filename);
    if (path == null) {
      throw new IllegalArgumentException("Invalid file path: " + filename);
    }
    File file = new File(path);
    file.getParentFile().mkdirs();

    try {
      return file.createNewFile();
    } catch (IOException e) {
      return false;
    }
  }

  public static void createFile(@NotNull String filename, @NotNull String content) {
    createFile(filename);

    String path = getValidFilePath(filename);
    File file = new File(path);

    try {
      PrintWriter writer = new PrintWriter(file);
      writer.print(content);
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException("Could not write to file: " + filename);
    }
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

  public static @NotNull List<@NotNull String> getFilesInDirectory(@NotNull String directoryPath) throws FileNotFoundException {
    String path = getExistingDirectoryPath(directoryPath);
    if (path == null) {
      throw new FileNotFoundException("Directory not found: " + directoryPath);
    }

    File directory = new File(path);
    return List.of(directory.list());
  }

  public static @NotNull List<@NotNull String> getFilesInDirectory(@NotNull String directoryPath, boolean createMissing) {
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
      throw new RuntimeException("Resource not found: /resources/hoteller/images/" + filePath);
    }
    return url;
  }

  public static @NotNull URL getFXMLResource(String filePath) {
    URL url = App.class.getResource("views/" + filePath);
    if (url == null) {
      throw new RuntimeException("Resource not found: /resources/hoteller/views/" + filePath);
    }
    return url;
  }
}
