import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    // situacao - OK - Aguarda implementacao novas features
    public void compilar(TextField sistema) {
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

    // situacao - TESTAR EFICACIA
    public boolean copiar(boolean sobreescrever) {

        File origem;
        File destino;
        String origemFinal;
        String destinoFinal;
        String nomeExe;

        // VERIFICA A ORIGEM E ATRIBUI O NOME DO EXE
        // [AQUI] verifica se o campo Macrosistem/OrigemExe das variaveis Locais Invalido
        if(util.stringToFile(acessoVariaveis.getValor(Util.Campos.MACROSISTEMA)) == null){
            util.exibeMensagem(Util.Mensagens.ORIGEM_INVALIDA, false);
            // [AQUI] solicita selecao manual do exe, caso invalida retorna erro
            try{
                origem = util.stringToFile(busca.caminho(null, true));
                nomeExe = origem.getName();
                origemFinal = origem.getAbsolutePath();
            }catch (NullPointerException e){
                util.exibeMensagem(Util.Mensagens.EXE_INVALIDO, true);
                return false;
            }
        // [AQUI] atribui na origem o campo Macrosistema
        }else{
            origem = util.stringToFile(acessoVariaveis.getValor(Util.Campos.MACROSISTEMA));
            // [AQUI] tenta buscar o exe automaticamente, caso contrario pede busca manual
            try{
                File caminhoExe = busca.caminhoExe(origem, Util.TipoArquivo.EXE.EXTENSAO);
                nomeExe = caminhoExe.getName();
                origemFinal = caminhoExe.getAbsolutePath();
            }catch (NullPointerException e){
                // [AQUI] verifica o retorno da busca manual, caso invalido retona erro
                util.exibeMensagem(Util.Mensagens.EXE_NAO_ENCONTRADO, false);
                try{
                    File caminhoExe = busca.caminhoExe(origem, Util.TipoArquivo.EXE.EXTENSAO);
                    nomeExe = caminhoExe.getName();
                    origemFinal = caminhoExe.getAbsolutePath();
                }catch (NullPointerException err){
                    util.exibeMensagem(Util.Mensagens.EXE_INVALIDO, true);
                    return false;
                }
            }
        }

        // VERIFICA O DESTINO
        // [AQUI] verifica se o campo Destino das variaveis Locais eh Invalido
        if(util.stringToFile(acessoVariaveis.getValor(Util.Campos.DESTINOCOPIA)) == null){
            util.exibeMensagem(Util.Mensagens.DESTINO_INVALIDO, true);
            return false;
        }else {
            // [AQUI] caso valido atribui no campo destino o valor
            destino = util.stringToFile(acessoVariaveis.getValor(Util.Campos.DESTINOCOPIA));
            destinoFinal = destino.getAbsolutePath() + "\\" + nomeExe;
        }

        acessoVariaveis.setValor(Util.Campos.NOMEEXE, nomeExe);

        // Define os caminhos para a copia
        Path caminhoOrigem = Paths.get(origemFinal);
        Path caminhoDestino = Paths.get(destinoFinal);

        // Inicia a copia
        try {
            if(sobreescrever){
                Files.deleteIfExists(caminhoDestino);
            }else {
                if(Files.exists(caminhoDestino)){
                    if (util.exibeEscolha(Util.Mensagens.SOBREESCREVER_EXE)){
                        Files.deleteIfExists(caminhoDestino);
                    }else {
                        util.exibeMensagem(Util.Mensagens.COPIA_CANCELADA, false);
                        return false;
                    }
                }
            }
            Files.copy(caminhoOrigem,
                       caminhoDestino);
            util.exibeMensagem(Util.Mensagens.EXE_COPIADO_SUCESSO, false);

        } catch (Exception e) {
            util.exibeMensagem(Util.Mensagens.ERRO_COPIAR, true);
            return false;
        }
        acessoVariaveis.setValor(Util.Campos.EXECUCAO, destino.getAbsolutePath());
        return true;
    }

    // situacao - TESTAR EFICACIA
    public void executar(TextField execucao, TextField setbanco, ComboBox banco, ComboBox agencia) {

        //Captura do path do exe soh o diretorio
        String DirExecucao = "";

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
                    DirExecucao,
                    execucao.getText(),
                    agencia.getPromptText()

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //define os novos acessoVariaveis pardrao para a execucao
        acessoVariaveis.setValor(Util.Campos.EXECUCAO, execucao.getText());
        acessoVariaveis.setValor(Util.Campos.SETBANCO, setbanco.getText());
        acessoVariaveis.setValor(Util.Campos.BANCO, banco.getPromptText());
        acessoVariaveis.setValor(Util.Campos.AGENCIA, agencia.getPromptText());
    }

    // situacao - OK
    public void opcoesCompilacao(boolean debug) {
        if (debug) {
            this.debug = Util.OpcoesCompilacao.DEBUG.paramentro;
        }
    }
}

