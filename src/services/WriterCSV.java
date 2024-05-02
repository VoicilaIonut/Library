package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterCSV {

  private static WriterCSV instance = null;

  private WriterCSV() {}

  public static synchronized WriterCSV getInstance() {
    if (instance == null) instance = new WriterCSV();
    return instance;
  }

  public void WriteAction(String action) {
    String timestamp = String.valueOf(System.currentTimeMillis());
    String path = "actions.csv";
    try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path, true))) {
      buffer.write(action + "," + timestamp + "\n");
      buffer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
