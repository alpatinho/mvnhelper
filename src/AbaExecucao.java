import Core.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class AbaExecucao {

    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private Util util = new Util();
    private Busca busca = new Busca();

    void executar(TextField execucao, TextField setbanco, ComboBox banco, ComboBox agencia) {
        File diretorioExe;
        if (util.stringToFile(execucao.getText()) == null){
            try {
                util.exibeMensagem(Enums.Mensagens.ERRO_CAMINHO_EXECUCAO, false);
                diretorioExe = util.stringToFile(busca.caminho(execucao.getText(), true));
                acessoVariaveis.setValor(Enums.Campos.EXECUCAO, diretorioExe.getAbsolutePath());
            }catch (Exception err){
                util.exibeMensagem(Enums.Mensagens.OPERACAO_CANCELADA, true);
                return;
            }
            return;
        }else {
            diretorioExe = util.stringToFile(execucao.getText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (banco.getValue() != null) {
            banco.setPromptText(banco.getValue().toString());
            acessoVariaveis.setValor(Enums.Campos.BANCO, banco.getPromptText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (agencia.getValue() != null) {
            agencia.setPromptText(agencia.getValue().toString());
            acessoVariaveis.setValor(Enums.Campos.AGENCIA, agencia.getPromptText());
        }

        //efetua a execucao do sistema
        if (setbanco.getText().equalsIgnoreCase("MANUAL")) {
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
                        setbanco.getText(),
                        banco.getPromptText(),
                        diretorioExe.getParent(), // caminho sem o nome do exe
                        diretorioExe.getAbsolutePath(), // caminho com o exe
                        "AG" + agencia.getPromptText()

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        acessoVariaveis.setValor(Enums.Campos.EXECUCAO, execucao.getText());
        acessoVariaveis.setValor(Enums.Campos.SETBANCO, setbanco.getText());
        acessoVariaveis.setValor(Enums.Campos.BANCO, banco.getPromptText());
        acessoVariaveis.setValor(Enums.Campos.AGENCIA, agencia.getPromptText());
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

    void checkAutoLogin(CheckBox autoLogin, TextField login, TextField senha){
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

    void autoLogIn(String login, String senha, String agencia) {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
