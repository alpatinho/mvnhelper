package Abas;

import Core.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AbaExecucao {

    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private Util util = new Util();
    private Busca busca = new Busca();

    public void executar(String execucao, String setbanco, ComboBox banco, ComboBox agencia) {
        File diretorioExe;
        if (util.stringToFile(execucao) == null){
            try {
                util.exibeMensagem(Enums.Mensagens.ERRO_CAMINHO_EXECUCAO, false);
                diretorioExe = util.stringToFile(busca.caminho(execucao, true));
                acessoVariaveis.setValor(Enums.Campos.EXECUCAO, diretorioExe.getAbsolutePath());
            }catch (Exception err){
                util.exibeMensagem(Enums.Mensagens.OPERACAO_CANCELADA, true);
                return;
            }
            return;
        }else {
            diretorioExe = util.stringToFile(execucao);
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (banco.getValue() != null) {
            banco.setPromptText(banco.getValue().toString());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (agencia.getValue() != null) {
            agencia.setPromptText(agencia.getValue().toString());
        }

        //efetua a execucao do sistema
        if (setbanco.equalsIgnoreCase("MANUAL")) {
            util.exibeMensagem(Enums.Mensagens.EXECUCAO_MANUAL, false);
            if(diretorioExe.getAbsolutePath().contains(":")) {
                String caminhoMapeado = acessoVariaveis.tradutorMapeamento(diretorioExe.getAbsolutePath().substring(0, 1));
                String caminhoCompleto = caminhoMapeado.concat(diretorioExe.getAbsolutePath().substring(2));
                executarManual(banco.getPromptText(), caminhoCompleto, agencia.getPromptText());
            }
        } else {
            try {
                Runtime.getRuntime().exec(new String[]{
                        "cmd.exe",
                        "/c",
                        "start",
                        Enums.ConfigPath.SCRIPT_EXECUCAO.getCaminho(),
                        setbanco,
                        banco.getPromptText(),
                        diretorioExe.getParent(), // caminho sem o nome do exe
                        diretorioExe.getAbsolutePath(), // caminho com o exe
                        "AG" + agencia.getPromptText()

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void executarManual(String banco, String caminho, String agencia){
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    Enums.ConfigPath.SCRIPT_MANUAL.getCaminho(),
                    banco.toUpperCase(),
                    caminho,
                    "AG" + agencia
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkAutoLogin(CheckBox autoLogin, TextField login, TextField senha){
        if(autoLogin.isSelected()){
            login.setDisable(true);
            senha.setDisable(true);
            acessoVariaveis.setValor(Enums.Campos.LOGIN, login.getText());
            acessoVariaveis.setValor(Enums.Campos.SENHA, senha.getText());
        }else{
            login.setDisable(false);
            senha.setDisable(false);
            login.setText(null);
            senha.setText(null);
            acessoVariaveis.setValor(Enums.Campos.LOGIN, login.getText());
            acessoVariaveis.setValor(Enums.Campos.SENHA, senha.getText());

        }
    }

    public void autoLogIn(String login, String senha, String agencia) {
        if(util.exibeEscolha(Enums.Mensagens.EFETUAR_AUTO_LOGIN)){
            try{
                AutoLogger autoLogger = new AutoLogger();
                autoLogger.login(login, senha, agencia);
            }catch (AWTException e){
                util.exibeMensagem(Enums.Mensagens.ERRO_AUTOLOGGER, false);
            }
        }
    }
}
