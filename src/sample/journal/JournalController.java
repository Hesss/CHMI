package sample.journal;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import sample.Journal;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JournalController {
    @FXML
    TextArea journalArea;

    @FXML
    void refresh() throws Exception {
        Path path = Paths.get("src/sample/demo.txt");
        String str = Files.readString(path, StandardCharsets.UTF_8);
        Journal.download();
        journalArea.setText(str);
    }

    @FXML
    void delete() throws IOException {
        Journal.removeList();
        Path path = Paths.get("src/sample/demo.txt");
        PrintWriter writer = new PrintWriter("src/sample/demo.txt");
        writer.print("");
        String str = Files.readString(path, StandardCharsets.UTF_8);
        journalArea.setText(str);
    }
}
