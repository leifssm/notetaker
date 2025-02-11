package hoteller.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import org.jetbrains.annotations.NotNull;

public class FileReader {
  private final String filename;
  private final File file;
  public FileReader(@NotNull String filename) throws FileNotFoundException {
    this.filename = filename;
    URL url = FilePathManager.getFileUrl(filename);
    System.out.println(url);
    file = new File(url.getFile());
  }

  public String read() throws FileReaderError {
    try {
      return Files.readString(file.toPath());
    } catch (Exception e) {
      throw new FileReaderError("Could not find file: " + filename);
    }
  }

  public void write(@NotNull String content) throws FileReaderError {
    try {
      PrintWriter writer = new PrintWriter(file);
      writer.print(content);
      writer.close();
    } catch (IOException e) {
      throw new FileReaderError("Could not overwrite file: " + filename);
    }
  }

  public void append(@NotNull String content) throws FileReaderError {
    try {
      Files.write(
        file.toPath(),
        content.getBytes(),
        StandardOpenOption.APPEND
      );
    } catch (IOException e) {
      throw new FileReaderError("Could not append to file: " + filename);
    }
  }
}
