package FxGUI;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class Controller {

    private Properties conf = new Properties();
    private String confPatch = "FxGUI/Data/DefaultValues.conf";

    private AcessoDados valores = new AcessoDados();

    @FXML private ResourceBundle resources;

    @FXML private URL location;

    @FXML private TabPane TPPrincipal;

    @FXML private TextField TFSubsistema;

    @FXML private CheckBox CKBDebugSubsistema;

    @FXML private Button ButtonBuscarSubsistema;

    @FXML private Button ButtonBuscarMacrosistema;

    @FXML private Button ButtonCompilarSubsistema;

    @FXML private Button ButtonCompilarMacrosistema;

    @FXML private CheckBox CKBDebugMacrosistema;

    @FXML private TextField TFMacrosistema;

    @FXML private Button ButtonDestinoExe;

    @FXML private TextField TFDestinoExe;

    @FXML private TextField TFOrigemExe;

    @FXML private Button ButtonBuscarOrigemExe;

    @FXML private Button ButtonMoverExe;

    @FXML private TextField TFOrigemExeExecucao;

    @FXML private Button ButtonBuscarExeExecucao;

    @FXML private Button ButtonExecutar;

    @FXML private ComboBox<?> CBAgencia;

    @FXML private ComboBox<?> CBBanco;

    @FXML private TextField TFSetBanco;

//
//    TFMacrosistema.setText(valores.getValorPadrao("DirMacrosistema"));
//    TFOrigemExe.setText(valores.getValorPadrao("DirOrigemExe"));
//    TFDestinoExe.setText(valores.getValorPadrao("DirDestinoExe"));
//    TFOrigemExeExecucao.setText(valores.getValorPadrao("DirCaminhoExe"));
//    TFSetBanco.setText(valores.getValorPadrao("SetBanco"));
//    CBBanco.setPromptText(valores.getValorPadrao("DefaultBanco"));
//    CBAgencia.setPromptText(valores.getValorPadrao("DefaultAgencia"));


    @FXML void ActionAgencia(ActionEvent event) {

    }

    @FXML void ActionBoxBanco(ActionEvent event) {

    }

    @FXML void ActionBuscarExeExecucao(ActionEvent event) {

    }

    @FXML void ActionBuscarMacrosistema(ActionEvent event) {

    }

    @FXML void ActionBuscarOrigemExe(ActionEvent event) {

    }

    @FXML void ActionBuscarSubsistema(ActionEvent event) {////////////////////////////////////////////////////
        TFSubsistema.setText("teste de valor");
    }

    @FXML void ActionCompilarMacrosistema(ActionEvent event) {

    }

    @FXML void ActionCompilarSubsistema(ActionEvent event) {

    }

    @FXML void ActionDebugMacrosistema(ActionEvent event) {

    }

    @FXML void ActionDebugSubsistema(ActionEvent event) {

    }

    @FXML void ActionDestinoExe(ActionEvent event) {

    }

    @FXML void ActionExecutar(ActionEvent event) {

    }

    @FXML void ActionMacrosistema(ActionEvent event) {

    }

    @FXML void ActionMoverExe(ActionEvent event) {

    }

    @FXML void ActionOrigemExe(ActionEvent event) {

    }

    @FXML void ActionOrigemExeExecucao(ActionEvent event) {

    }

    @FXML void ActionSubsistema(ActionEvent event) {

    }

    @FXML void initialize() {

        TFSubsistema.setText("Valor inicializado");
        //TFSubsistema.setText();
        String x = valores.getValorPadrao("DirSubsistema");



    }
}
