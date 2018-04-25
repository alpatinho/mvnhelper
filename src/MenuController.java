import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MenuController {

    private Acesso valores = new Acesso();
    private MenuModel model = new MenuModel();

    @FXML private TabPane TPPrincipal;

    //COMPILAR
    @FXML private TextField TFSubsistema;
    @FXML private Button BTNBuscarSubsistema;
    @FXML private Button BTNCompilarSubsistema;

    @FXML private TextField TFMacrosistema;
    @FXML private Button BTNBuscarMacrosistema;
    @FXML private Button BTNCompilarMacrosistema;

    //OPCOES
    @FXML private CheckBox CKDebug;

    //MOVER
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


    //METODOS DE ACOES

    //COMPILAR
    @FXML void ActionBuscarSubsistema() {
        TFSubsistema.setText(model.buscaDiretorio(TFSubsistema.getText()));
        valores.setValor(Campos.SUBSISTEMA, TFSubsistema.getText());
    }

    @FXML void ActionCompilarSubsistema() {
        model.Compila(TFSubsistema);
        valores.setValor(Campos.SUBSISTEMA, TFSubsistema.getText());
    }

    @FXML void ActionBuscarMacrosistema() {
        TFMacrosistema.setText(model.buscaDiretorio(TFMacrosistema.getText()));
        valores.setValor(Campos.MACROSISTEMA, TFMacrosistema.getText());
    }

    @FXML void ActionCompilarMacrosistema() {
        model.Compila(TFMacrosistema);
        valores.setValor(Campos.MACROSISTEMA, TFMacrosistema.getText());
    }

    @FXML void ActionDebug() {
        model.debug(CKDebug.isSelected());
        System.out.println(model.buscaNomeExeCompilacao(TFMacrosistema.getText()));
    }

    //MOVER
    @FXML void ActionBuscarDestinoExe() {
        TFDestinoExe.setText(model.buscaDiretorio(TFDestinoExe.getText()));
        valores.setValor(Campos.MOVERDESTINO, TFDestinoExe.getText());
    }

    @FXML void ActionMoverExe() {
        model.mover(TFDestinoExe.getText());
        valores.setValor(Campos.MOVERDESTINO, TFDestinoExe.getText());
    }


    //EXECUTAR
    @FXML void ActionBuscarCaminhoExecucao() {
        TFCaminhoExecucao.setText(model.buscaDiretorio(TFCaminhoExecucao.getText()));
        valores.setValor(Campos.EXECUCAO, TFCaminhoExecucao.getText());
    }

    @FXML void ActionExecutar() {
        model.executar(TFCaminhoExecucao, TFSetBanco, CBBanco, CBAgencia);
        valores.setValor(Campos.EXECUCAO, TFCaminhoExecucao.getText());
        valores.setValor(Campos.SETBANCO, TFSetBanco.getText());
        valores.setValor(Campos.BANCO, CBBanco.getPromptText());
        valores.setValor(Campos.AGENCIA, CBAgencia.getPromptText());
    }


    @FXML void initialize() {
        TFSubsistema.setText(valores.getValor(Campos.SUBSISTEMA));
        TFMacrosistema.setText(valores.getValor(Campos.MACROSISTEMA));
        TFDestinoExe.setText(valores.getValor(Campos.MOVERDESTINO));
        TFCaminhoExecucao.setText(valores.getValor(Campos.EXECUCAO));
        TFSetBanco.setText(valores.getValor(Campos.SETBANCO));
        CBBanco.setPromptText(valores.getValor(Campos.BANCO));
        CBAgencia.setPromptText(valores.getValor(Campos.AGENCIA));
        CBBanco.setItems(valores.getListaValores(Campos.LISTABANCO));
        CBAgencia.setItems(valores.getListaValores(Campos.LISTAAGENCIA));

    }
}
