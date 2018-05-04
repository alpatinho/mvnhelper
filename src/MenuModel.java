import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class MenuModel {

    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private Busca busca = new Busca();
    private Util util = new Util();
    private String debug = "";

    void compilar(TextField sistema) {
        try {
            Runtime.getRuntime().exec(
                    new String[]{
                            "cmd.exe",
                            "/c",
                            "start",
                            Util.SCRIPT_COMPILACAO,
                            sistema.getText(),
                            "mvn",
                            "clean",
                            "install",
                            debug
                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void copiarEXE(boolean sobreescrever) {

        File origem;
        File destino;

        // VERIFICA A ORIGEM E ATRIBUI O NOME DO EXE
        // [FLUXO 1] verifica se o campo Macrosistem/OrigemExe das variaveis eh Locais Invalido
        if(util.stringToFile(acessoVariaveis.getValor(Util.Campos.MACROSISTEMA)) == null){
            util.exibeMensagem(Util.Mensagens.ORIGEM_INVALIDA, false);
            // [AQUI] solicita selecao manual do exe, caso invalida retorna erro
            try{
                origem = util.stringToFile(busca.caminho(null, true));
            }catch (NullPointerException e){
                util.exibeMensagem(Util.Mensagens.ARQUIVO_INVALIDO, true);
                return;
            }
        // [FLUXO 2] se o campo for valido Macrosistem/OrigemExe atribui a origem o valor completo
        }else{
            origem = util.stringToFile(acessoVariaveis.getValor(Util.Campos.MACROSISTEMA));
            // [AQUI] tenta buscar o exe automaticamente, caso contrario pede busca manual
            try{
                origem = busca.caminhoExe(origem);
            }catch (NullPointerException e){
                util.exibeMensagem(Util.Mensagens.ARQUIVO_NAO_ENCONTRADO, false);
                try{
                    origem = util.stringToFile(busca.caminho(origem.getAbsolutePath(), true));
                }catch (NullPointerException err){
                    util.exibeMensagem(Util.Mensagens.ARQUIVO_INVALIDO, true);
                    util.exibeMensagem(Util.Mensagens.OPERACAO_CANCELADA, true);
                    return;
                }
            }
        }

        // VERIFICA O DESTINO
        // [AQUI] verifica se o campo Destino das variaveis Locais eh Invalido
        if(util.stringToFile(acessoVariaveis.getValor(Util.Campos.DESTINOCOPIA)) == null){
            util.exibeMensagem(Util.Mensagens.DESTINO_INVALIDO, true);
            return;
        }else {
            // [AQUI] caso valido atribui no campo destino o valor
            destino = util.stringToFile(acessoVariaveis.getValor(Util.Campos.DESTINOCOPIA));
        }

        // Define os caminhos para a copia
        Path caminhoOrigem = Paths.get(origem.getAbsolutePath());
        Path caminhoDestino = Paths.get(destino.getAbsolutePath());
        String nomeArquivoDestino = copia(caminhoOrigem, caminhoDestino, sobreescrever);

        acessoVariaveis.setValor(Util.Campos.EXECUCAO, nomeArquivoDestino);
    }

    private String copia(Path origem, Path destino, boolean sobreescrever){
        String arquivo = origem.getFileName().toString();
        destino = Paths.get(destino + "\\" + arquivo);

        try {
            if(sobreescrever){
                Files.deleteIfExists(destino);
            }else {
                if(Files.exists(destino)){
                    if (util.exibeEscolha(Util.Mensagens.SOBREESCREVER)){
                        Files.deleteIfExists(destino);
                    }else {
                        util.exibeMensagem(Util.Mensagens.OPERACAO_CANCELADA, true);
                        return null;
                    }
                }
            }
            Files.copy(origem, destino);
            util.exibeMensagem(Util.Mensagens.OPERACAO_SUCESSO, false);

        } catch (Exception e) {
            util.exibeMensagem(Util.Mensagens.ERRO_COPIAR, true);
        }
        return destino.toString();
    }

    void executar(TextField execucao, TextField setbanco, ComboBox banco, ComboBox agencia) {
        File diretorioExe;
        if (util.stringToFile(execucao.getText()) == null){
            try {
                util.exibeMensagem(Util.Mensagens.ERRO_CAMINHO_EXECUCAO, false);
                diretorioExe = util.stringToFile(busca.caminho(execucao.getText(), true));
                acessoVariaveis.setValor(Util.Campos.EXECUCAO, diretorioExe.getAbsolutePath());
            }catch (Exception err){
                util.exibeMensagem(Util.Mensagens.OPERACAO_CANCELADA, true);
                return;
            }
            return;
        }else {
            diretorioExe = util.stringToFile(execucao.getText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (banco.getValue() != null) {
            banco.setPromptText(banco.getValue().toString());
            acessoVariaveis.setValor(Util.Campos.BANCO, banco.getPromptText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (agencia.getValue() != null) {
            agencia.setPromptText(agencia.getValue().toString());
            acessoVariaveis.setValor(Util.Campos.AGENCIA, agencia.getPromptText());
        }

        //efetua a execucao do sistema
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    Util.SCRIPT_EXECUCAO,
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

    void autoLogIn(String login, String senha, String agencia) {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(util.exibeEscolha(Util.Mensagens.EFETUAR_AUTO_LOGIN)){
            try{
                AutoLogger autoLogger = new AutoLogger();
                autoLogger.login(login, senha, agencia);
            }catch (AWTException e){
                util.exibeMensagem(Util.Mensagens.ERRO_AUTOLOGGER, false);
            }
        }
    }

    void opcoesCompilacao(boolean debug) {
        if (debug) {
            this.debug = Util.OpcoesCompilacao.DEBUG.paramentro;
        }
    }

    void salvaCaminhoFontes(String caminhoExecucao, String caminhoFontes, boolean sobreescrever){

        if((util.stringToFile(caminhoExecucao) == null) || (util.stringToFile(caminhoFontes) == null)){
            util.exibeMensagem(Util.Mensagens.ERRO_SALVAR_CAMINHO_FONTES, false);
            return;
        }
        String caminhoArquivoNovo = copia(Paths.get(Util.PATH_FONTES), Paths.get(caminhoExecucao).getParent(), sobreescrever);
        acessoVariaveis.setPathFontes(caminhoArquivoNovo, caminhoFontes);
    }
}

