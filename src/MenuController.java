import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuController {

    private DataAcesso valores = new DataAcesso();
    private PathAcesso diretorios = new PathAcesso();
    private Stage busca = new Stage();
    private String debug = "";
    private String nomeExe = "";
    private Image logoAcc = new Image(getClass().getResourceAsStream("./Img/Acc_Logo.png"));

    @FXML private TabPane TPPrincipal;

    @FXML private TextField TFSubsistema;

    @FXML private CheckBox CKDebug;

    @FXML private Button BTNBuscarSubsistema;

    @FXML private Button BTNBuscarMacrosistema;

    @FXML private Button BTNCompilarSubsistema;

    @FXML private Button BTNCompilarMacrosistema;

    @FXML private TextField TFMacrosistema;

    @FXML private Button BTNDestinoExe;

    @FXML private TextField TFDestinoExe;

    @FXML private TextField TFOrigemExe;

    @FXML private Button BTNOrigemExe;

    @FXML private Button BTNMoverExe;

    @FXML private TextField TFCaminhoExecucao;

    @FXML private Button BTNCaminhoExecucao;

    @FXML private Button BTNExecutar;

    @FXML private ComboBox<?> CBAgencia;

    @FXML private ComboBox<?> CBBanco;

    @FXML private TextField TFSetBanco;

    @FXML private Label AccLogo;

    @FXML private Button BTNAdicionarBanco;

    @FXML private Button AdicionarAgencia;

    @FXML private TextField TFPadraoBusca;

    @FXML private Button BTNBuscaPadrao;

    @FXML void ActionBuscarDestinoExe() {
        String caminho = diretorios.buscaDiretorio(busca);
        if (caminho != null) {
            TFDestinoExe.setText(caminho);
            valores.setValorPadrao("DirDestinoExe", caminho);
        }
    }

    @FXML void ActionBuscarCaminhoExecucao() {
        String caminho = diretorios.buscaArquivo(busca);
        if (caminho != null) {
            TFCaminhoExecucao.setText(caminho);
            valores.setValorPadrao("DirExeExecucao", caminho);
        }
    }

    @FXML void ActionBuscarMacrosistema() {
        String caminho = diretorios.buscaDiretorio(busca);
        if (caminho != null) {
            TFMacrosistema.setText(caminho);
            valores.setValorPadrao("DirMacrosistema", caminho);
        }
    }

    @FXML void ActionBuscarOrigemExe() {
        String caminho = diretorios.buscaArquivo(busca);
        if (caminho != null) {
            TFOrigemExe.setText(caminho);
            valores.setValorPadrao("DirOrigemExe", caminho);
        }
    }

    @FXML void ActionBuscarSubsistema() {
        String caminho = diretorios.buscaDiretorio(busca);
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
                    System.getProperty("user.dir") + "./src/Scripts/compila.bat",
                    TFMacrosistema.getText(),
                    "mvn",
                    "clean",
                    "install",
                    debug
            });

            //Captura o nome do exe
            File folder = new File(valores.getValorPadrao("DirMacrosistema")+"\\target\\classes\\win32-bcc5.x-xhb0.99.x");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().endsWith(".exe"))
                        nomeExe = listOfFiles[i].getName();
                    TFOrigemExe.setText(valores.getValorPadrao("DirOrigemExe")+"\\"+nomeExe);
                }
            }


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
                System.getProperty("user.dir") + "./src/Scripts/compila.bat",
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
        if (CKDebug.isSelected()){
            debug = "-Ddebug";
        }else {
            debug = "";
        }

    }

    @FXML void ActionExecutar() {

        //Captura do path do exe soh o diretorio
        String DirExeExecucao[];
        String DirExecucao = "";
        DirExeExecucao = TFCaminhoExecucao.getText().replace("\\", "\\#").split("#");
        for (int i = 0; i < DirExeExecucao.length - 1; i++) {
            DirExecucao += DirExeExecucao[i];
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (CBBanco.getValue() != null){
            CBBanco.setPromptText((String) CBBanco.getValue());
            valores.setValorPadrao("Bancos", CBBanco.getPromptText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (CBAgencia.getValue() != null){
            CBAgencia.setPromptText((String) CBAgencia.getValue());
            valores.setValorPadrao("Agencias", CBAgencia.getPromptText());
        }

        //efetua a execucao do sistema
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/Scripts/executa.bat",
                    TFSetBanco.getText(),
                    CBBanco.getPromptText(),
                    DirExecucao,
                    TFCaminhoExecucao.getText(),
                    CBAgencia.getPromptText()

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //define os novos valores pardrao para a execucao
        valores.setValorPadrao("DirExeExecucao", TFCaminhoExecucao.getText());
        valores.setValorPadrao("DefaultSetBanco", TFSetBanco.getText());
        valores.setValorPadrao("DefaultBanco", CBBanco.getPromptText());
        valores.setValorPadrao("DefaultAgencia", CBAgencia.getPromptText());
    }

    @FXML void ActionMoverExe() {

        //efetua a copia do exe para a pasta destino
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/Scripts/copia.bat",
                    TFOrigemExe.getText(),
                    TFDestinoExe.getText()
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Concatena o destino com o nome do exe para execucao rapida
        TFCaminhoExecucao.setText(TFDestinoExe.getText()+"\\"+nomeExe);
    }

    @FXML void ActionBuscaPadrao() {
        String caminho = diretorios.buscaDiretorio(busca);
        if (caminho != null) {
            TFPadraoBusca.setText(caminho);
            valores.setValorPadrao("DirDestinoExe", caminho);
        }
    }

    @FXML void ActionBuscaBanco() {

    }

    @FXML void ActionBuscarAgencia() {

    }


    @FXML void initialize() {
        TFSubsistema.setText(valores.getValorPadrao("DirSubsistema"));
        TFMacrosistema.setText(valores.getValorPadrao("DirMacrosistema"));
        TFOrigemExe.setText(valores.getValorPadrao("DirOrigemExe"));
        TFDestinoExe.setText(valores.getValorPadrao("DirDestinoExe"));
        TFCaminhoExecucao.setText(valores.getValorPadrao("DirExeExecucao"));
        TFSetBanco.setText(valores.getValorPadrao("DefaultSetBanco"));
        TFPadraoBusca.setText(valores.getValorPadrao("DirPadraoBusca"));
        CBBanco.setPromptText(valores.getValorPadrao("DefaultBanco"));
        CBAgencia.setPromptText(valores.getValorPadrao("DefaultAgencia"));
        CBBanco.setItems(valores.getValores("Bancos"));
        CBAgencia.setItems(valores.getValores("Agencias"));
        AccLogo.setGraphic(new ImageView(logoAcc));

    }
}
