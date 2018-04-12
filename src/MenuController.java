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

    //COMPILAR
    @FXML private TextField TFSubsistema;
    @FXML private Button BTNBuscarSubsistema;
    @FXML private Button BTNCompilarSubsistema;

    @FXML private CheckBox CKDebug;

    @FXML private TextField TFMacrosistema;
    @FXML private Button BTNBuscarMacrosistema;
    @FXML private Button BTNCompilarMacrosistema;

    //MOVER
    @FXML private TextField TFOrigemExe;
    @FXML private Button BTNOrigemExe;

    @FXML private TextField TFDestinoExe;
    @FXML private Button BTNDestinoExe;

    @FXML private Button BTNMoverExe;

    //EXECUTAR
    @FXML private TextField TFCaminhoExecucao;
    @FXML private Button BTNCaminhoExecucao;

    @FXML private TextField TFSetBanco;
    @FXML private ComboBox<?> CBBanco;
    @FXML private ComboBox<?> CBAgencia;

    @FXML private Button BTNExecutar;

    //CONFIG
    @FXML private TextField TFPadraoBusca;
    @FXML private Button BTNBuscaPadrao;

    //METODOS DE ACOES
    //COMPILAR
    @FXML void ActionBuscarSubsistema() {TFSubsistema.setText(model.buscaDiretorio(Campos.SUBSISTEMA, Campos.EXECUCAO));}

    @FXML void ActionCompilarSubsistema() {model.Compila(TFSubsistema, null);}

    @FXML void ActionDebug() {model.debug(CKDebug.isSelected());}

    @FXML void ActionBuscarMacrosistema() {TFMacrosistema.setText(model.buscaDiretorio(Campos.MACROSISTEMA, Campos.EXECUCAO));}

    @FXML void ActionCompilarMacrosistema() {model.Compila(TFMacrosistema, TFOrigemExe);}

    //MOVER
    @FXML void ActionBuscarOrigemExe() {TFOrigemExe.setText(model.buscaDiretorio(Campos.MOVERORIGEM, Campos.EXECUCAO));}

    @FXML void ActionBuscarDestinoExe() {TFDestinoExe.setText(model.buscaDiretorio(Campos.MOVERDESTINO, Campos.EXECUCAO));}

    @FXML void ActionMoverExe() {model.Mover(TFOrigemExe, TFDestinoExe, TFCaminhoExecucao);}

    //EXECUTAR
    @FXML void ActionBuscarCaminhoExecucao() {TFCaminhoExecucao.setText(model.buscaDiretorio(Campos.EXECUCAO, Campos.EXECUCAO));}

    @FXML void ActionExecutar() {model.executar(TFCaminhoExecucao, TFSetBanco, CBBanco, CBAgencia);}

    //CONFIGS
    @FXML void ActionBuscaPadrao() {TFPadraoBusca.setText(model.buscaDiretorio(Campos.PADRAOBUSCA, null));}

    @FXML void initialize() {
        TFSubsistema.setText(valores.getValorPadrao(Campos.SUBSISTEMA));
        TFMacrosistema.setText(valores.getValorPadrao(Campos.MACROSISTEMA));
        TFOrigemExe.setText(valores.getValorPadrao(Campos.MOVERORIGEM));
        TFDestinoExe.setText(valores.getValorPadrao(Campos.MOVERDESTINO));
        TFCaminhoExecucao.setText(valores.getValorPadrao(Campos.EXECUCAO));
        TFSetBanco.setText(valores.getValorPadrao(Campos.SETBANCO));
        TFPadraoBusca.setText(valores.getValorPadrao(Campos.PADRAOBUSCA));
        CBBanco.setPromptText(valores.getValorPadrao(Campos.BANCO));
        CBAgencia.setPromptText(valores.getValorPadrao(Campos.AGENCIA));
        CBBanco.setItems(valores.getValores(Campos.LISTABANCO));
        CBAgencia.setItems(valores.getValores(Campos.LISTAAGENCIA));

    }
}
