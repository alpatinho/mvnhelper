import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuController {

    private DataAcesso valores = new DataAcesso();
    private MenuModel model = new MenuModel();

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
       TFDestinoExe.setText(model.buscaDiretorio("DirDestinoExe"));
    }

    @FXML void ActionBuscarCaminhoExecucao() {
        TFCaminhoExecucao.setText(model.buscaDiretorio("DirExeExecucao"));
    }

    @FXML void ActionBuscarMacrosistema() {
        TFMacrosistema.setText(model.buscaDiretorio("DirMacrosistema"));
    }

    @FXML void ActionBuscarOrigemExe() {
        TFOrigemExe.setText(model.buscaDiretorio("DirOrigemExe"));
    }

    @FXML void ActionBuscarSubsistema() {
        TFSubsistema.setText(model.buscaDiretorio("DirSubsistema"));
    }

    @FXML void ActionCompilarMacrosistema() {

    }

    @FXML void ActionCompilarSubsistema() {

    }

    @FXML void ActionDebug() {
        model.debug(CKDebug.isSelected());
    }

    @FXML void ActionExecutar() {
        model.executar();
    }

    @FXML void ActionMoverExe() {
        model.Mover();
    }

    @FXML void ActionBuscaPadrao() {
        TFPadraoBusca.setText(model.buscaDiretorio("DirDestinoExe"));
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

    }
}
