import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuController {

    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private MenuModel model = new MenuModel();
    private Busca busca = new Busca();

    @FXML private TabPane TPPrincipal;

    // COMPILAR
    @FXML private TextField TFSubsistema;
    @FXML private Button BTNBuscarSubsistema;
    @FXML private Button BTNCompilarSubsistema;

    @FXML private TextField TFMacrosistema;
    @FXML private Button BTNBuscarMacrosistema;
    @FXML private Button BTNCompilarMacrosistema;

    // OPCOES_COMPILACAO
    @FXML private CheckBox CKDebug;


    // COPIAR
    @FXML private TextField TFDestinoExe;
    @FXML private Button BTNDestinoCopia;
    @FXML private Button BTNCopiarExe;

    @FXML private CheckBox CKSobreescrever;


    // EXECUTAR
    @FXML private TextField TFCaminhoExecucao;
    @FXML private Button BTNCaminhoExecucao;

    @FXML private TextField TFSetBanco;
    @FXML private ComboBox<?> CBBanco;
    @FXML private ComboBox<?> CBAgencia;

    @FXML private Button BTNExecutar;


    // CONFIGURACOES
    @FXML private CheckBox CKSalvarEstadoCks;
    @FXML private Label LBLLogoAccTec;

    // METODOS DE ACOES
    // COMPILAR
    @FXML void ActionBuscarSubsistema() {
        TFSubsistema.setText(busca.caminho(TFSubsistema.getText(), false));
        acessoVariaveis.setValor(Util.Campos.SUBSISTEMA, TFSubsistema.getText());
    }

    @FXML void ActionCompilarSubsistema() {
        model.opcoesCompilacao(CKDebug.isSelected());
        model.compilar(TFSubsistema);
        acessoVariaveis.setValor(Util.Campos.SUBSISTEMA, TFSubsistema.getText());
    }

    @FXML void ActionBuscarMacrosistema() {
        TFMacrosistema.setText(busca.caminho(TFMacrosistema.getText(), false));
        acessoVariaveis.setValor(Util.Campos.MACROSISTEMA, TFMacrosistema.getText());
    }

    @FXML void ActionCompilarMacrosistema() {
        model.opcoesCompilacao(CKDebug.isSelected());
        model.compilar(TFMacrosistema);
        acessoVariaveis.setValor(Util.Campos.MACROSISTEMA, TFMacrosistema.getText());
    }

    @FXML void ActionDebug() {
        ActionSalvarEstadoCks();
    }

    // COPIAR
    @FXML void ActionBuscarDestinoExe() {
        TFDestinoExe.setText(busca.caminho(TFDestinoExe.getText(), false));
        acessoVariaveis.setValor(Util.Campos.DESTINOCOPIA, TFDestinoExe.getText());
        TFCaminhoExecucao.setText(acessoVariaveis.getValor(Util.Campos.EXECUCAO));
    }

    @FXML void ActionCopiarExe() {
        acessoVariaveis.setValor(Util.Campos.MACROSISTEMA, TFMacrosistema.getText());
        acessoVariaveis.setValor(Util.Campos.DESTINOCOPIA, TFDestinoExe.getText());
        model.copiar(CKSobreescrever.isSelected());
        TFCaminhoExecucao.setText(acessoVariaveis.getValor(Util.Campos.EXECUCAO));
    }


    // EXECUTAR
    @FXML void ActionBuscarCaminhoExecucao() {
        TFCaminhoExecucao.setText(busca.caminho(TFCaminhoExecucao.getText(), true));
        acessoVariaveis.setValor(Util.Campos.EXECUCAO, TFCaminhoExecucao.getText());
    }

    @FXML void ActionExecutar() {
        model.executar(TFCaminhoExecucao, TFSetBanco, CBBanco, CBAgencia);
        acessoVariaveis.setValor(Util.Campos.EXECUCAO, TFCaminhoExecucao.getText());
        acessoVariaveis.setValor(Util.Campos.SETBANCO, TFSetBanco.getText());
        acessoVariaveis.setValor(Util.Campos.BANCO, CBBanco.getPromptText());
        acessoVariaveis.setValor(Util.Campos.AGENCIA, CBAgencia.getPromptText());
    }

    // CONFIGURACOES
    @FXML void ActionSalvarEstadoCks() {
        if(CKSalvarEstadoCks.isSelected()){
            acessoVariaveis.setValor(Util.Campos.SALVARCK, "TRUE");
            acessoVariaveis.setValor(Util.Campos.DEBUG, CKDebug.isSelected() ? "TRUE" : "");
            acessoVariaveis.setValor(Util.Campos.SOBREESCREVER, CKSobreescrever.isSelected() ? "TRUE" : "");
        }else {
            acessoVariaveis.setValor(Util.Campos.SALVARCK, "");
        }
    }

    @FXML void ActionSobreescrever(){
        ActionSalvarEstadoCks();
    }

    private void inicializaEstadoCks(){
        if(CKSalvarEstadoCks.isSelected()){
            if(acessoVariaveis.getValor(Util.Campos.SOBREESCREVER).equals("TRUE")) CKSobreescrever.setSelected(true);
            if (acessoVariaveis.getValor(Util.Campos.DEBUG).equals("TRUE")) CKDebug.setSelected(true);
        }
    }

    @FXML void initialize() {
        if(acessoVariaveis.getValor(Util.Campos.SALVARCK).equals("TRUE")) {
            CKSalvarEstadoCks.setSelected(true);
            inicializaEstadoCks();
        }
        LBLLogoAccTec.setGraphic(new ImageView(new Image("file:"+Util.LOGO_ACC)));

        TFSubsistema.setText(acessoVariaveis.getValor(Util.Campos.SUBSISTEMA));
        TFMacrosistema.setText(acessoVariaveis.getValor(Util.Campos.MACROSISTEMA));

        TFDestinoExe.setText(acessoVariaveis.getValor(Util.Campos.DESTINOCOPIA));

        TFCaminhoExecucao.setText(acessoVariaveis.getValor(Util.Campos.EXECUCAO));
        TFSetBanco.setText(acessoVariaveis.getValor(Util.Campos.SETBANCO));
        CBBanco.setPromptText(acessoVariaveis.getValor(Util.Campos.BANCO));
        CBAgencia.setPromptText(acessoVariaveis.getValor(Util.Campos.AGENCIA));
        CBBanco.setItems(acessoVariaveis.getListaValores(Util.LISTA_BANCOS));
        CBAgencia.setItems(acessoVariaveis.getListaValores(Util.LISTA_AGENCIAS));


    }
}
