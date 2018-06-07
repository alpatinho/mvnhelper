import Core.AcessoVariaveis;
import Core.Busca;
import Core.Enums;
import Core.Util;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

class AbaCompilacao {
    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private Util util = new Util();
    private Busca busca = new Busca();
    private String debug = "";

    void compilar(TextField sistema, Enums.Campos campo) {
        try {
            Runtime.getRuntime().exec(
                    new String[]{
                            "cmd.exe",
                            "/c",
                            "start",
                            Enums.ConfigPath.SCRIPT_COMPILACAO.getCaminho(),
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
        acessoVariaveis.setValor(campo, sistema.getText());

    }

    void opcoesCompilacao(boolean debug) {
        if (debug) {
            this.debug = Enums.opcoesExtras.DEBUG.paramentro;
        }
    }

    void opcaoCommit(String caminhoComponente, String opcao){
        try {
            Runtime.getRuntime().exec(
                new String[]{
                        "cmd.exe",
                        "/c",
                        "start",
                        Enums.ConfigPath.SCRIPT_OPCAO_COMMIT.getCaminho(),
                        caminhoComponente,
                        opcao
                }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void copiarEXE(TextField execucao, boolean sobreescrever) {

        File origem;
        File destino;

        // VERIFICA A ORIGEM E ATRIBUI O NOME DO EXE
        // [FLUXO 1] verifica se o campo Macrosistem/OrigemExe das variaveis eh Locais Invalido
        if(util.stringToFile(acessoVariaveis.getValor(Enums.Campos.MACROSISTEMA)) == null){
            util.exibeMensagem(Enums.Mensagens.EXE_ORIGEM_INVALIDA, false);
            // [AQUI] solicita selecao manual do exe, caso invalida retorna erro
            if(util.stringToFile(busca.caminho(null,true))!= null){
                origem = util.stringToFile(busca.caminho(null,true));
            }else{
                util.exibeMensagem(Enums.Mensagens.ARQUIVO_INVALIDO, true);
                return;
            }
            // [FLUXO 2] se o campo for valido Macrosistem/OrigemExe atribui a origem o valor completo
        }else{
            origem = util.stringToFile(acessoVariaveis.getValor(Enums.Campos.MACROSISTEMA));
            // [AQUI] tenta buscar o exe automaticamente, caso contrario pede busca manual
            try{
                origem = busca.caminhoExe(origem);
            }catch (NullPointerException e){
                util.exibeMensagem(Enums.Mensagens.ARQUIVO_NAO_ENCONTRADO, false);
                try{
                    origem = util.stringToFile(busca.caminho(origem.getAbsolutePath(),  true));
                }catch (NullPointerException err){
                    util.exibeMensagem(Enums.Mensagens.ARQUIVO_INVALIDO, true);
                    util.exibeMensagem(Enums.Mensagens.OPERACAO_CANCELADA, true);
                    return;
                }
            }
        }

        // VERIFICA O DESTINO
        // [AQUI] verifica se o campo Destino das variaveis Locais eh Invalido
        if(util.stringToFile(acessoVariaveis.getValor(Enums.Campos.DESTINOCOPIA)) == null){
            util.exibeMensagem(Enums.Mensagens.DESTINO_INVALIDO, true);
            return;
        }else {
            // [AQUI] caso valido atribui no campo destino o valor
            destino = util.stringToFile(acessoVariaveis.getValor(Enums.Campos.DESTINOCOPIA));
        }

        // Define os caminhos para a copia
        Path caminhoOrigem = Paths.get(origem.getAbsolutePath());
        Path caminhoDestino = Paths.get(destino.getAbsolutePath());
        String nomeArquivoDestino = util.copia(caminhoOrigem, caminhoDestino, sobreescrever);
        if(nomeArquivoDestino != null){
            util.exibeMensagem(Enums.Mensagens.OPERACAO_SUCESSO, false);
            execucao.setText(nomeArquivoDestino);
            acessoVariaveis.setValor(Enums.Campos.EXECUCAO, nomeArquivoDestino);
        }
    }

}
