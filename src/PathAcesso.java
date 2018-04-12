import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PathAcesso {

    public String buscaArquivo(Stage busca, Campos padraoBusca) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Busca de EXECUTAVEL");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Executável", "*.exe"));
        File selectedFile = fileChooser.showOpenDialog(busca);

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        }

        return null;
    }

    public String buscaDiretorio(Stage busca, Campos padraoBusca){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Busca de DIRETORIO");
        File selectedDiretory = directoryChooser.showDialog(busca);

        if (selectedDiretory != null) {
            return selectedDiretory.getAbsolutePath();
        }

        return null;
    }
}

