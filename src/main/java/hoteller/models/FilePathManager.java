package hoteller.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;

import org.jetbrains.annotations.NotNull;

import hoteller.App;

public class FilePathManager {
  private static @NotNull URL getResource(String filePath) throws FileNotFoundException {
    URL url = App.class.getResource(filePath);
    if (url == null) {
      throw new FileNotFoundException("Resource not found: /resources/hoteller/" + filePath);
    }
    return url;
  }

  public static @NotNull URL getImageUrl(String filePath) throws FileNotFoundException {
    return getResource("images/" + filePath);
  }
  public static @NotNull URL getFXMLUrl(String filePath) throws FileNotFoundException {
    return getResource("views/" + filePath);
  }
  public static @NotNull URL getFileUrl(String filePath) throws FileNotFoundException {
    return getResource("storage/" + filePath);
  }
}
