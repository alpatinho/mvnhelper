import Abas.AbaCompilacao;
import Abas.AbaDebug;
import Abas.AbaExecucao;
import Core.AcessoVariaveis;
import Core.ArquivoFonte;
import Core.Busca;
import Core.Enums;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {

    private AbaCompilacao abaCompilacao = new AbaCompilacao();
    private AbaExecucao abaExecucao = new AbaExecucao();
    private AbaDebug abaDebug = new AbaDebug();
    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private Busca busca = new Busca();

    //
    @FXML private Label LBLVersion;
    @FXML private Label LBLVersionDate;
    @FXML private Label LBLLogoAccTec;
    @FXML private TextField TFSubsistema;
    @FXML private TextField TFMacrosistema;
    @FXML private TextField TFCaminhoExecucao;
    @FXML private TextField TFDestinoExe;
    @FXML private TextField TFSetBanco;
    @FXML private TextField TFLogin;
    @FXML private TextField TFSenha;
    @FXML private CheckBox CKDebug;
    @FXML private CheckBox CKSobreescrever;
    @FXML private CheckBox CKSalvarEstadoCks;
    @FXML private CheckBox CKHabilitarAutoLogin;
    @FXML private ComboBox<String> CBBanco;
    @FXML private ComboBox<String> CBAgencia;
    @FXML private TableView<ArquivoFonte> TVOrigemFonte;
    @FXML private TableColumn<ArquivoFonte, String> TCNomeFonte;
    @FXML private TableColumn<ArquivoFonte, String> TCCaminhoFonte;

    @FXML void ActionBuscarSubsistema() {TFSubsistema.setText(busca.caminho(TFSubsistema, Enums.Campos.SUBSISTEMA, false));}
    @FXML void ActionBuscarMacrosistema() {TFMacrosistema.setText(busca.caminho(TFMacrosistema, Enums.Campos.MACROSISTEMA, false));}
    @FXML void ActionBuscarDestinoExe() {TFDestinoExe.setText(busca.caminho(TFDestinoExe, Enums.Campos.DESTINOCOPIA, false));}
    @FXML void ActionBuscarCaminhoExecucao() {TFCaminhoExecucao.setText(busca.caminho(TFCaminhoExecucao, Enums.Campos.EXECUCAO, true));}
    @FXML void ActionBuscarFontes(){}

    @FXML void ActionCompilarSubsistema() {
        abaCompilacao.opcoesCompilacao(CKDebug.isSelected());
        abaCompilacao.compilar(TFSubsistema, Enums.Campos.SUBSISTEMA);
    }

    @FXML void ActionCompilarMacrosistema() {
        abaCompilacao.opcoesCompilacao(CKDebug.isSelected());
        abaCompilacao.compilar(TFMacrosistema, Enums.Campos.MACROSISTEMA);
    }

    @FXML void ActionCopiarExe() {
        acessoVariaveis.setValor(Enums.Campos.MACROSISTEMA, TFMacrosistema.getText());
        acessoVariaveis.setValor(Enums.Campos.DESTINOCOPIA, TFDestinoExe.getText());
        abaCompilacao.copiarEXE(TFCaminhoExecucao, CKSobreescrever.isSelected());
    }

    @FXML void ActionDebug() {ActionSalvarEstadoCks();}

    @FXML void ActionExecutar() {
        abaExecucao.executar(TFCaminhoExecucao, TFSetBanco, CBBanco, CBAgencia);
        if(CKHabilitarAutoLogin.isSelected()) abaExecucao.autoLogIn(TFLogin.getText(), TFSenha.getText(), CBAgencia.getPromptText());
    }

    @FXML void ActionAtualizarExecutar(){
        ActionAtualizarFontesDestino();
        ActionExecutar();
    }

    @FXML void ActionHabilitarAutoLogin(){
        abaExecucao.checkAutoLogin(CKHabilitarAutoLogin, TFLogin, TFSenha);
        ActionSalvarEstadoCks();
    }

    private void tabelaFontes(){
        TCCaminhoFonte.setCellValueFactory((valor) -> valor.getValue().caminhoArquivoProperty());
        TCNomeFonte.setCellValueFactory((valor) -> valor.getValue().nomeArquivoProperty());
        TVOrigemFonte.setOnDragOver((event) -> {
            abaDebug.dragTabelaFontes(event);
            TVOrigemFonte.setStyle("-fx-border-color: red; -fx-background-color: #D6D6D6;");
        });
        TVOrigemFonte.setOnDragDropped((event) -> abaDebug.dropTabelaFontes(event, TVOrigemFonte));
        TVOrigemFonte.setOnDragExited(event -> TVOrigemFonte.setStyle("-fx-border-color: #C8C8C8;"));
        TVOrigemFonte.setOnMouseClicked((event) -> abaDebug.doubleClickFonte(event, TVOrigemFonte));
    }

    @FXML void ActionAtualizarFontesDestino(){abaDebug.atualizaFontesDestino(TFDestinoExe.getText());}

    @FXML void ActionRemoverSelecionado(){abaDebug.getArquivosFontes().remove(TVOrigemFonte.getSelectionModel().getSelectedItem());}

    @FXML void ActionRemoverTodos(){abaDebug.getArquivosFontes().removeAll(abaDebug.getArquivosFontes());}

    private void inicializaEstadoCks(){
        if(acessoVariaveis.getValor(Enums.Campos.SALVARCK).equals("TRUE")) {
            CKSalvarEstadoCks.setSelected(true);
            if(acessoVariaveis.getValor(Enums.Campos.SOBREESCREVER).equals("TRUE")) CKSobreescrever.setSelected(true);
            if (acessoVariaveis.getValor(Enums.Campos.DEBUG).equals("TRUE")) CKDebug.setSelected(true);
            if (acessoVariaveis.getValor(Enums.Campos.AUTOLOGIN).equals("TRUE")){CKHabilitarAutoLogin.setSelected(true);
                TFLogin.setText(acessoVariaveis.getValor(Enums.Campos.LOGIN));
                TFSenha.setText(acessoVariaveis.getValor(Enums.Campos.SENHA));
                ActionHabilitarAutoLogin();
            }
        }
    }

    @FXML void ActionSalvarEstadoCks() {
        if (CKSalvarEstadoCks.isSelected()) {
            acessoVariaveis.setValor(Enums.Campos.SALVARCK, "TRUE");
            acessoVariaveis.setValor(Enums.Campos.DEBUG, CKDebug.isSelected() ? "TRUE" : "");
            acessoVariaveis.setValor(Enums.Campos.SOBREESCREVER, CKSobreescrever.isSelected() ? "TRUE" : "");
            acessoVariaveis.setValor(Enums.Campos.AUTOLOGIN, CKHabilitarAutoLogin.isSelected() ? "TRUE" : "");
        } else acessoVariaveis.setValor(Enums.Campos.SALVARCK, "");
    }

    @FXML void ActionSobreescrever(){ActionSalvarEstadoCks();}

    // INICIALIZACAO
    @FXML void initialize() {
        inicializaEstadoCks();
        LBLLogoAccTec.setGraphic(new ImageView(new Image("file:"+ Enums.ConfigPath.LOGO_ACC.getCaminho())));
        TFSubsistema.setText(acessoVariaveis.getValor(Enums.Campos.SUBSISTEMA));
        TFMacrosistema.setText(acessoVariaveis.getValor(Enums.Campos.MACROSISTEMA));
        TFDestinoExe.setText(acessoVariaveis.getValor(Enums.Campos.DESTINOCOPIA));
        TFCaminhoExecucao.setText(acessoVariaveis.getValor(Enums.Campos.EXECUCAO));
        TFSetBanco.setText(acessoVariaveis.getValor(Enums.Campos.SETBANCO));
        CBBanco.setPromptText(acessoVariaveis.getValor(Enums.Campos.BANCO));
        CBAgencia.setPromptText(acessoVariaveis.getValor(Enums.Campos.AGENCIA));
        CBBanco.setItems(acessoVariaveis.getListaValores(Enums.ConfigPath.LISTA_BANCOS.getCaminho()));
        CBAgencia.setItems(acessoVariaveis.getListaValores(Enums.ConfigPath.LISTA_AGENCIAS.getCaminho()));
        LBLVersion.setText(Enums.Mensagens.MVN_HELPER_VERSION.getTitulo());
        LBLVersionDate.setText(Enums.Mensagens.MVN_HELPER_VERSION.getDetalhe());
        tabelaFontes();
    }
}
