package Abas;

import Core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AbaCompilacao {

    private ObservableList<ArquivoFonte> listaArquivosFontes = FXCollections.observableArrayList();
    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private Util util = new Util();
    private Busca busca = new Busca();
    private String opcoesCompilacao = "";

    public void compilar(TextField sistema, Enums.Campos campo, ArrayList<Enums.opcoesExtras> opcoesCompilacao) {
        StringBuilder opcao = new StringBuilder();
        for (Enums.opcoesExtras extra: opcoesCompilacao) {
            opcao.append(extra.paramentro);
        }
        this.opcoesCompilacao = opcao.toString();
        compilar(sistema, campo);
    }

    void compilar(TextField sistema, Enums.Campos campo) {
        try {
            Runtime.getRuntime().exec("cmd.exe /c start " +
                Enums.ConfigPath.SCRIPT_COMPILACAO.getCaminho() + sistema.getText() +
                " mvn clean install " + opcoesCompilacao);
        } catch (IOException e) {
            e.printStackTrace();
        }
        acessoVariaveis.setValor(campo, sistema.getText());
    }

    public void copiarEXE(TextField execucao, boolean sobreescrever) {
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

    public ObservableList<ArquivoFonte> getArquivosFontes(){
        return listaArquivosFontes;
    }

    public void addListaFontes(List<File> arquivos, TableView<ArquivoFonte> tabela){
        arquivos.stream()
                .filter(File::isFile)
                .filter((fonte) -> fonte.getName().endsWith(".prg"))
                .forEach((fonte) -> listaArquivosFontes.add(new ArquivoFonte(fonte.getAbsolutePath())));
        tabela.setItems(getArquivosFontes());
    }


    public void salvaCaminhoFontes(String caminhoExecucao, String caminhoFontes, boolean sobreescrever){
        if(util.stringToFile(caminhoFontes) == null){
            util.exibeMensagem(Enums.Mensagens.ORIGEM_INVALIDA, false);
            return;
        } else if(util.stringToFile(caminhoExecucao) == null){
            util.exibeMensagem(Enums.Mensagens.ERRO_SALVAR_CAMINHO, false);
            return;
        }
        String caminhoArquivoNovo = util.copia(Paths.get(Enums.ConfigPath.PATH_FONTES.getCaminho()), Paths.get(caminhoExecucao).getParent(), sobreescrever);
        if(caminhoArquivoNovo != null){
            util.exibeMensagem(Enums.Mensagens.OPERACAO_SUCESSO, false);
            acessoVariaveis.setPathFontes(caminhoArquivoNovo, caminhoFontes);
        }
    }

    private void exibeArquivoExplorer(String caminho){
        if(util.stringToFile(caminho) != null) {
            try {
                Runtime.getRuntime().exec("explorer.exe /select," + caminho);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            util.exibeMensagem(Enums.Mensagens.ERRO_TRIZONHO, true);
        }
    }

    public void atualizaFontesDestino(String destinoFontes){
        try {
            util.stringToFile(destinoFontes);
        }catch (NullPointerException e){
            util.exibeMensagem(Enums.Mensagens.DESTINO_INVALIDO, false);
            return;
        }
        if(listaArquivosFontes.size() == 0){
            util.exibeMensagem(Enums.Mensagens.SEM_ARQUIVOS, false);
        }else {
            listaArquivosFontes.forEach((fonte) -> util.copia(Paths.get(fonte.getCaminhoArquivo()), Paths.get(destinoFontes), true));
            util.exibeMensagem(Enums.Mensagens.OPERACAO_CONCLUIDA, false);
        }
    }

    public void dragTabelaFontes(final DragEvent drag){
        drag.acceptTransferModes(TransferMode.COPY);
        drag.consume();
    }

    public void dropTabelaFontes(final DragEvent drop, TableView<ArquivoFonte> tabela){
        final Dragboard db = drop.getDragboard();
        if(db.hasFiles()) addListaFontes(db.getFiles(), tabela);
        drop.consume();
    }

    public void doubleClickFonte(MouseEvent event, TableView<ArquivoFonte> tabela) {
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1)
            exibeArquivoExplorer(tabela.getItems().get(tabela.getSelectionModel().getFocusedIndex()).getCaminhoArquivo());
    }

}
