import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class MenuController {

    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private MenuModel model = new MenuModel();
    private Busca busca = new Busca();

    // ABAS... ainda nao precisei
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
    @FXML private Button BTNExecutarComDebug;
    @FXML private CheckBox CKHabilitarAutoLogin;
    @FXML private TextField TFLogin;
    @FXML private TextField TFSenha;

    // DEBUG
    @FXML private TextField TFDestinoFontes;
    @FXML private Button BTNBuscarDestinoFontes;
    @FXML private Button BTNCopiarCaminhoDestinoFontes;
    @FXML private Button BTNSalvarCaminhoDestinoFontes;
    @FXML private Label LBLConfirmacaoVisual;
    @FXML private TableView<?> TBOrigemFonte;
    @FXML private TableColumn<?, ?> TCNomeFonte;
    @FXML private TableColumn<?, ?> TCCaminhoFonte;
    @FXML private Button BTNBuscarFontes;
    @FXML private Button BTNLimparTabelaFontes;
    @FXML private Button BTNAtualizarFontesDEBUG;

    // CONFIGURACOES
    @FXML private CheckBox CKSalvarEstadoCks;
    @FXML private Label LBLLogoAccTec;

    // ACOES
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
        model.copiarEXE(CKSobreescrever.isSelected());
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
        if(CKHabilitarAutoLogin.isSelected()){
            model.autoLogIn(TFLogin.getText(), TFSenha.getText(), CBAgencia.getPromptText());
        }
    }
    @FXML void ActionExecutarComDebug(){// TODO

    }
    @FXML void ActionHabilitarAutoLogin(){
        if(CKHabilitarAutoLogin.isSelected()){
            TFLogin.setEditable(false);
            TFSenha.setEditable(false);
            acessoVariaveis.setValor(Util.Campos.LOGIN, TFLogin.getText());
            acessoVariaveis.setValor(Util.Campos.SENHA, TFSenha.getText());
        }else{
            TFLogin.setEditable(true);
            TFSenha.setEditable(true);
            TFLogin.setText(null);
            TFSenha.setText(null);
            acessoVariaveis.setValor(Util.Campos.LOGIN, TFLogin.getText());
            acessoVariaveis.setValor(Util.Campos.SENHA, TFSenha.getText());

        }
        ActionSalvarEstadoCks();
    }

    // DEBUG
    @FXML void ActionBuscarDestinoFontes(){
        TFDestinoFontes.setText(busca.caminho(TFDestinoFontes.getText(), false));
        acessoVariaveis.setValor(Util.Campos.FONTES, TFDestinoFontes.getText());
    }
    @FXML void ActionBuscarFontes(){// TODO
        busca.multiArquivos(TFDestinoFontes.getText());
    }
    @FXML void ActionCopiarCaminhoDestinoFontes(){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(TFDestinoFontes.getText()), null);
        LBLConfirmacaoVisual.setText("COPIADO");
    }
    @FXML void ActionSalvarCaminhoDestinoFontes(){
        model.salvaCaminhoFontes(TFCaminhoExecucao.getText(), TFDestinoFontes.getText(), CKSobreescrever.isSelected());
    }
    @FXML void ActionLimparTabelaFontes(){// TODO

    }
    @FXML void ActionAtualizarFontesDestino(){// TODO

    }

    // CONFIGURACOES
    @FXML void ActionSalvarEstadoCks() {
        if(CKSalvarEstadoCks.isSelected()){
            acessoVariaveis.setValor(Util.Campos.SALVARCK, "TRUE");
            acessoVariaveis.setValor(Util.Campos.DEBUG, CKDebug.isSelected() ? "TRUE" : "");
            acessoVariaveis.setValor(Util.Campos.SOBREESCREVER, CKSobreescrever.isSelected() ? "TRUE" : "");
            acessoVariaveis.setValor(Util.Campos.AUTOLOGIN, CKHabilitarAutoLogin.isSelected() ? "TRUE" : "");
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
            if (acessoVariaveis.getValor(Util.Campos.AUTOLOGIN).equals("TRUE")) CKHabilitarAutoLogin.setSelected(true);
        }
    }

    // INICIALIZACAO
    @FXML void initialize() {
        if(acessoVariaveis.getValor(Util.Campos.SALVARCK).equals("TRUE")) {
            CKSalvarEstadoCks.setSelected(true);
            inicializaEstadoCks();
            TFLogin.setText(acessoVariaveis.getValor(Util.Campos.LOGIN));
            TFSenha.setText(acessoVariaveis.getValor(Util.Campos.SENHA));
            ActionHabilitarAutoLogin();
        }
        LBLLogoAccTec.setGraphic(new ImageView(new Image("file:"+Util.ConfigPath.LOGO_ACC.getCaminho())));
        TFSubsistema.setText(acessoVariaveis.getValor(Util.Campos.SUBSISTEMA));
        TFMacrosistema.setText(acessoVariaveis.getValor(Util.Campos.MACROSISTEMA));
        TFDestinoExe.setText(acessoVariaveis.getValor(Util.Campos.DESTINOCOPIA));
        TFCaminhoExecucao.setText(acessoVariaveis.getValor(Util.Campos.EXECUCAO));
        TFSetBanco.setText(acessoVariaveis.getValor(Util.Campos.SETBANCO));
        CBBanco.setPromptText(acessoVariaveis.getValor(Util.Campos.BANCO));
        CBAgencia.setPromptText(acessoVariaveis.getValor(Util.Campos.AGENCIA));
        CBBanco.setItems(acessoVariaveis.getListaValores(Util.ConfigPath.LISTA_BANCOS.getCaminho()));
        CBAgencia.setItems(acessoVariaveis.getListaValores(Util.ConfigPath.LISTA_AGENCIAS.getCaminho()));
        TFDestinoFontes.setText(acessoVariaveis.getValor(Util.Campos.FONTES));
    }
}
