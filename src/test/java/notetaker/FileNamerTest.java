package notetaker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import notetaker.models.FileNamer;

public class FileNamerTest {
  private List<String> existingFiles;
  private FileNamer fileNamer;

  @BeforeEach
  public void setUp() {
    existingFiles = List.of("test.txt", "test(1).txt", "test(2).txt", "test(3).txt");
    fileNamer = new FileNamer();
  }

  private void testInvalidFilename(String filename) {
    assertNull(
        fileNamer.getValidName(filename, existingFiles),
        "Filename is not valid and should return null: " + filename);
  }

  @Test
  public void testInvalidFilenames() {
    testInvalidFilename("test");
    testInvalidFilename("test.");
    testInvalidFilename("test.txt.");
    testInvalidFilename("test..txt");
  }

  @Test void testNullFilename() {
    assertThrows(Exception.class, () -> testInvalidFilename(null), "Filename is null and should return null");
  }
}
