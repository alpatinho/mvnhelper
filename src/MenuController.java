import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class MenuController {

    private DataAcesso valores = new DataAcesso();
    private PathAcesso dir = new PathAcesso();
    private Stage busca = new Stage();
    private String debug = "";

    @FXML private ResourceBundle resources;

    @FXML private URL location;

    @FXML private TabPane TPPrincipal;

    @FXML private TextField TFSubsistema;

    @FXML private CheckBox CKBDebug;

    @FXML private Button BtnBuscarSubsistema;

    @FXML private Button BtnBuscarMacrosistema;

    @FXML private Button BtnCompilarSubsistema;

    @FXML private Button BtnCompilarMacrosistema;

    @FXML private TextField TFMacrosistema;

    @FXML private Button BtnDestinoExe;

    @FXML private TextField TFDestinoExe;

    @FXML private TextField TFOrigemExe;

    @FXML private Button BtnBuscarOrigemExe;

    @FXML private Button BtnMoverExe;

    @FXML private TextField TFOrigemExeExecucao;

    @FXML private Button BtnBuscarExeExecucao;

    @FXML private Button BtnExecutar;

    @FXML private ComboBox<?> CBAgencia;

    @FXML private ComboBox<?> CBBanco;

    @FXML private TextField TFSetBanco;

    @FXML void ActionAgencia() {

    }

    @FXML void ActionBanco() {

    }

    @FXML void ActionBuscarDestinoExe() {
        String caminho = dir.buscaDiretorio(busca);
        if (caminho != null) {
            TFDestinoExe.setText(caminho);
            valores.setValorPadrao("DirDestinoExe", caminho);
        }
    }

    @FXML void ActionBuscarExeExecucao() {
        String caminho = dir.buscaArquivo(busca);
        if (caminho != null) {
            TFOrigemExeExecucao.setText(caminho);
            valores.setValorPadrao("DirExeExecucao", caminho);
        }
    }

    @FXML void ActionBuscarMacrosistema() {
        String caminho = dir.buscaDiretorio(busca);
        if (caminho != null) {
            TFMacrosistema.setText(caminho);
            valores.setValorPadrao("DirMacrosistema", caminho);
        }
    }

    @FXML void ActionBuscarOrigemExe() {
        String caminho = dir.buscaArquivo(busca);
        if (caminho != null) {
            TFOrigemExe.setText(caminho);
            valores.setValorPadrao("DirOrigemExe", caminho);
        }
    }

    @FXML void ActionBuscarSubsistema() {
        String caminho = dir.buscaDiretorio(busca);
        if (caminho != null) {
            TFSubsistema.setText(caminho);
            valores.setValorPadrao("DirSubsistema", caminho);
        }
    }

    @FXML void ActionCompilarMacrosistema() {
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/scripts/compila.bat",
                    TFMacrosistema.getText(),
                    "mvn",
                    "clean",
                    "install",
                    debug
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void ActionCompilarSubsistema() {
        try {
            Runtime.getRuntime().exec(new String[]{
                "cmd.exe",
                "/c",
                "start",
                System.getProperty("user.dir") + "./src/scripts/compila.bat",
                TFSubsistema.getText(),
                "mvn",
                "clean",
                "install",
                debug
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void ActionDebug() {
        if (CKBDebug.isSelected()){
            debug = "-Ddebug";
        }else {
            debug = "";
        }

        //testes

        //

    }

    @FXML void ActionExecutar() {
        String array[];
        String DirExecucao = "";
        array = TFOrigemExeExecucao.getText().replace("\\", "\\#").split("#");
        for (int i = 0; i < array.length - 1; i++) {
            DirExecucao += array[i];
        }
        if (CBBanco.getValue() != null){
            CBBanco.setPromptText((String) CBBanco.getValue());
            valores.setValorPadrao("Bancos", CBBanco.getPromptText());
        }
        if (CBAgencia.getValue() != null){
            CBAgencia.setPromptText((String) CBAgencia.getValue());
            valores.setValorPadrao("Agencias", CBAgencia.getPromptText());
        }
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/scripts/executa.bat",
                    TFSetBanco.getText(),
                    CBBanco.getPromptText(),
                    DirExecucao,
                    TFOrigemExeExecucao.getText(),
                    CBAgencia.getPromptText()

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void ActionMoverExe() {
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/scripts/copia.bat",
                    TFOrigemExe.getText(),
                    TFDestinoExe.getText()
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void initialize() {
        TFSubsistema.setText(valores.getValorPadrao("DirSubsistema"));
        TFMacrosistema.setText(valores.getValorPadrao("DirMacrosistema"));
        TFOrigemExe.setText(valores.getValorPadrao("DirOrigemExe"));
        TFDestinoExe.setText(valores.getValorPadrao("DirDestinoExe"));
        TFOrigemExeExecucao.setText(valores.getValorPadrao("DirExeExecucao"));
        TFSetBanco.setText(valores.getValorPadrao("DefaultSetBanco"));
        CBBanco.setPromptText(valores.getValorPadrao("DefaultBanco"));
        CBAgencia.setPromptText(valores.getValorPadrao("DefaultAgencia"));
        CBBanco.setItems(valores.getValores("Bancos"));
        CBAgencia.setItems(valores.getValores("Agencias"));

    }
}
