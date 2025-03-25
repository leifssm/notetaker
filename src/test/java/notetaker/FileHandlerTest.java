package notetaker;

import static org.junit.jupiter.api.Assertions.*;

import notetaker.models.FileHandler;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class FileHandlerTest {
  private static final String testFileFolder = "testFiles/";

  private boolean fileExists(String filePath) {
    return FileHandler.fileExists(testFileFolder + filePath);
  }
  @Test
  public void testReadFile() throws FileNotFoundException {
    String content = FileHandler.readFile(testFileFolder + "read.txt");
    assertEquals("testcontent\r\nwowie", content);
  }

  @Test
  public void testCreateAndDeleteFile() {
    assertFalse(fileExists("create.txt"), "File should not exist at init");

    FileHandler.createFile(testFileFolder + "create.txt");

    assertTrue(fileExists("create.txt"), "File should exist after creation");

    FileHandler.deleteFile(testFileFolder + "create.txt");

    assertFalse(fileExists("create.txt"), "File should not exist after deletion");
  }

  @Test
  public void testRenameFile() {
    assertFalse(fileExists("rename.txt"), "File should not exist at init");

    FileHandler.createFile(testFileFolder + "rename.txt");

    assertTrue(fileExists("rename.txt"), "File should exist at creation");

    FileHandler.renameFile(testFileFolder + "rename.txt", "renamed.txt");

    assertFalse(fileExists("rename.txt"), "File should not exist after renaming");
    assertTrue(fileExists("renamed.txt"), "File should be renamed to 'renamed.txt'");

    FileHandler.deleteFile(testFileFolder + "renamed.txt");

    assertFalse(fileExists("renamed.txt"), "File should not exist after deletion");

  }

  @Test
  public void testSetFile() throws FileNotFoundException {
    String content = "testcontent\r\nwowie";

    assertFalse(fileExists("set.txt"));
    FileHandler.setFile(testFileFolder + "set.txt", content);

    String readContent = FileHandler.readFile(testFileFolder + "set.txt");
    assertEquals(content, readContent);

    FileHandler.deleteFile(testFileFolder + "set.txt");
    assertFalse(fileExists("set.txt"));
  }
}
