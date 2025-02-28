package notetaker.models;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileNamer {
  public static @Nullable String getValidName(@NotNull String name, @NotNull List<@NotNull String> takenNames) {
    if (!name.matches("[a-zA-Z0-9 \\-_()]+\\.[a-zA-Z0-9]+")) {
      return "nuhuh " + name;
    }
    if (!takenNames.contains(name)) {
      return name;
    }
    Matcher matcher = Pattern.compile("(.*?)(?:\\((\\d+)\\))?\\.([a-zA-Z0-9]*)").matcher(name);
    if (!matcher.matches() || matcher.group(3).isEmpty()) {
      return null;
    }
    if (matcher.group(2) == null) {
      return FileNamer.getValidName(matcher.group(1) + "(1)." + matcher.group(3), takenNames);
    }
    int from = Integer.parseInt(matcher.group(2));
    for (int i = from; i <= 9; i++) {
      String newName = matcher.group(1) + "(" + i + ")." + matcher.group(3);
      if (!takenNames.contains(newName)) {
        return newName;
      }
    }
    return getValidName(matcher.group(1) + "(9)(1)." + matcher.group(3), takenNames);
  }
  public static void main(String[] args) {
    System.out.println(FileNamer.getValidName("bbbb.", List.of("test.txt", "test(1).txt", "test(2).txt")));
    System.out.println(FileNamer.getValidName("bbbb.txt", List.of("test.txt", "test(1).txt", "test(2).txt")));
    System.out.println(FileNamer.getValidName("bbbb(1).txt", List.of("test.txt", "test(1).txt", "test(2).txt")));
    System.out.println(FileNamer.getValidName("bbbb(2).txt", List.of("test.txt", "test(1).txt", "test(2).txt")));
    System.out.println(FileNamer.getValidName("test.txt", List.of("test.txt", "test(1).txt", "test(2).txt", "test(3).txt")));
    System.out.println(FileNamer.getValidName("test.txt", List.of("test.txt", "test(1).txt", "test(2).txt", "test(3).txt", "test(4).txt")));
    System.out.println(FileNamer.getValidName("test(8).txt", List.of("test.txt", "test(8).txt")));
    System.out.println(FileNamer.getValidName("test(8).txt", List.of("test.txt", "test(8).txt", "test(9).txt")));
    System.out.println(FileNamer.getValidName("test(8).txt", List.of("test.txt", "test(8).txt", "test(9).txt", "test(9)(1).txt")));
  }
}
