package FxGUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.swing.*;

public class MenuController {

    private DataAcesso valores = new DataAcesso();
    private PathAcesso dir = new PathAcesso();
    private Stage busca = new Stage();

    @FXML private ResourceBundle resources;

    @FXML private URL location;

    @FXML private TabPane TPPrincipal;

    @FXML private TextField TFSubsistema;

    @FXML private CheckBox CKBDebugSubsistema;

    @FXML private Button BtnBuscarSubsistema;

    @FXML private Button BtnBuscarMacrosistema;

    @FXML private Button BtnCompilarSubsistema;

    @FXML private Button BtnCompilarMacrosistema;

    @FXML private CheckBox CKBDebugMacrosistema;

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

    @FXML void ActionAgencia(ActionEvent event) {

    }

    @FXML void ActionBanco(ActionEvent event) {

    }

    @FXML void ActionBuscarDestinoExe(ActionEvent event) {
        String caminho = dir.start(busca);
        if (caminho != null) {
            TFDestinoExe.setText(caminho);
            valores.setValorPadrao("DirDestinoExe", caminho);
        }
    }

    @FXML void ActionBuscarExeExecucao(ActionEvent event) {
        String caminho = dir.start(busca);
        if (caminho != null) {
            TFOrigemExeExecucao.setText(caminho);
            valores.setValorPadrao("DirExeExecucao", caminho);
        }
    }

    @FXML void ActionBuscarMacrosistema(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionBuscarOrigemExe(ActionEvent event) {
        String caminho = dir.start(busca);
        if (caminho != null) {
            TFOrigemExe.setText(caminho);
            valores.setValorPadrao("DirOrigemExe", caminho);
        }
    }

    @FXML void ActionBuscarSubsistema(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionCompilarMacrosistema(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionCompilarSubsistema(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionDebugMacrosistema(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionDebugSubsistema(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionExecutar(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionMacrosistema(ActionEvent event) {

    }

    @FXML void ActionMoverExe(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Opcao em contrucao!");
    }

    @FXML void ActionOrigemExe(ActionEvent event) {

    }

    @FXML void ActionOrigemExeExecucao(ActionEvent event) {

    }

    @FXML void ActionSubsistema(ActionEvent event) {

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
