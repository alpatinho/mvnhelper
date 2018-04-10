package FxGUI;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PathAcesso {

    public String start(Stage busca) {

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(busca);

        if (file != null) {
            return file.getAbsolutePath();
        }

        return null;
    }
}

