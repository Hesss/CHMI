package sample.journal;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JournalPage extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("journal.fxml"));
        primaryStage.getIcons().add(new Image("@../../images/journal.png"));
        primaryStage.setTitle("Journal");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showWindow() throws Exception {
        start(stage);
    }

}
