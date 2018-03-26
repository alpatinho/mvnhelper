package FxGUI;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AcessoDiretorios extends Application {

    private FileChooser fileChooser = new FileChooser();

    @Override
    public void start(Stage chooser) {

        TextField textField = new TextField();

        Button button1 = new Button("Select One File");

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.clear();
                File file = fileChooser.showOpenDialog(chooser);
                if (file != null) {
                    List<File> files = Arrays.asList(file);
                    printLog(textField, files);
                }
            }
        });

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);

        root.getChildren().addAll(textField, button1);

        Scene scene = new Scene(root, 400, 75);

        chooser.setScene(scene);
        chooser.show();
    }

    private void printLog(TextField textField, List<File> files) {
        if (files == null || files.isEmpty()) {
            return;
        }
        for (File file : files) {
            textField.setText(file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}

