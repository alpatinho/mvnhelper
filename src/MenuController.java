import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class MenuController {

    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private MenuModel model = new MenuModel();
    private Busca busca = new Busca();

    // Painel principal e abas...
    @FXML private TabPane TPPrincipal;
    @FXML private Tab TBCompilacao;
    @FXML private Tab TBExecucao;
    @FXML private Tab TBDebug;
    @FXML private Tab TBConfig;

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
    @FXML private ComboBox<String> CBBanco;
    @FXML private ComboBox<String> CBAgencia;
    @FXML private Button BTNExecutar;
    @FXML private CheckBox CKHabilitarAutoLogin;
    @FXML private TextField TFLogin;
    @FXML private TextField TFSenha;

    // DEBUG
    @FXML private TextField TFDestinoFontes;
    @FXML private Button BTNBuscarDestinoFontes;
    @FXML private Button BTNCopiarCaminhoDestinoFontes;
    @FXML private Button BTNSalvarCaminhoDestinoFontes;
    @FXML private Label LBLConfirmacaoVisual;
    @FXML private TableView<ArquivoFonte> TVOrigemFonte;
    @FXML private TableColumn<ArquivoFonte, String> TCNomeFonte;
    @FXML private TableColumn<ArquivoFonte, String> TCCaminhoFonte;
    @FXML private Button BTNBuscarFontes;
    @FXML private Button BTNRemoverSelecionado;
    @FXML private Button BTNRemoverTodos;
    @FXML private Button BTNAtualizarFontes;

    // CONFIGURACOES
    @FXML private CheckBox CKSalvarEstadoCks;
    @FXML private Label LBLLogoAccTec;

    // ACOES
    // COMPILAR
    @FXML void ActionBuscarSubsistema() {
        TFSubsistema.setText(busca.caminho(TFSubsistema, Util.Campos.SUBSISTEMA, false));
    }

    @FXML void ActionCompilarSubsistema() {
        model.opcoesCompilacao(CKDebug.isSelected());
        model.compilar(TFSubsistema);
        acessoVariaveis.setValor(Util.Campos.SUBSISTEMA, TFSubsistema.getText());
    }

    @FXML void ActionBuscarMacrosistema() {
        TFMacrosistema.setText(busca.caminho(TFMacrosistema, Util.Campos.MACROSISTEMA, false));
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
        TFDestinoExe.setText(busca.caminho(TFDestinoExe, Util.Campos.DESTINOCOPIA, false));
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
        TFCaminhoExecucao.setText(busca.caminho(TFCaminhoExecucao, Util.Campos.EXECUCAO, true));
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
        TFDestinoFontes.setText(busca.caminho(TFDestinoFontes, Util.Campos.FONTES, false));
    }

    @FXML void ActionBuscarFontes(){
        model.addListaFontes(TFDestinoFontes.getText());
        TVOrigemFonte.setItems(model.getArquivosFontes());
    }

    @FXML void ActionCopiarCaminhoDestinoFontes(){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(TFDestinoFontes.getText()), null);
        LBLConfirmacaoVisual.setText("COPIADO");
    }

    @FXML void ActionSalvarCaminhoDestinoFontes(){
        model.salvaCaminhoFontes(TFCaminhoExecucao.getText(), TFDestinoFontes.getText(), CKSobreescrever.isSelected());
    }

    @FXML void ActionAtualizarFontesDestino(){
        model.atualizaFontes(TFDestinoFontes.getText());
    }

    @FXML void ActionRemoverSelecionado(){
        model.getArquivosFontes().remove(TVOrigemFonte.getSelectionModel().getSelectedItem());
    }

    @FXML void ActionRemoverTodos(){
        model.getArquivosFontes().removeAll(model.getArquivosFontes());
    }

    void DragOver(final DragEvent drag){
        final Dragboard db = drag.getDragboard();
        TVOrigemFonte.setStyle("-fx-border-color: red;"
                + "-fx-background-color: #C6C6C6;"
                + "-fx-border-style: solid;");
        drag.acceptTransferModes(TransferMode.COPY);
        drag.consume();
    }

    void DragDrop(final DragEvent drop){
        final Dragboard db = drop.getDragboard();
        if(db.hasFiles()) model.addListaFontesDrop(db.getFiles());
        TVOrigemFonte.setItems(model.getArquivosFontes());
        drop.consume();
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
        TCCaminhoFonte.setCellValueFactory((valor) -> valor.getValue().caminhoArquivoProperty());
        TCNomeFonte.setCellValueFactory((valor) -> valor.getValue().nomeArquivoProperty());
        TVOrigemFonte.setOnDragOver(this::DragOver);
        TVOrigemFonte.setOnDragDropped(this::DragDrop);
        TVOrigemFonte.setOnDragExited(event -> TVOrigemFonte.setStyle("-fx-border-color: #C8C8C8;"));
    }
}
