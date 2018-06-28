package Abas;

import Core.ArquivoFonte;
import Core.Busca;
import Core.Enums;
import Core.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.input.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class AbaDebug {


    private javafx.collections.ObservableList<ArquivoFonte> listaArquivosFontes = FXCollections.observableArrayList();
    private Busca busca = new Busca();
    private Util util = new Util();

    public ObservableList<ArquivoFonte> getArquivosFontes(){
        return listaArquivosFontes;
    }

    public void buscaListaFontes(String caminho, TableView<ArquivoFonte> tabela){
        try {
            List<File> arquivos = busca.multiArquivos(caminho);
            addListaFontes(arquivos, tabela);
        }catch (Exception e){
            util.exibeMensagem(Enums.Mensagens.ARQUIVO_INVALIDO, false);
        }
    }

    private void addListaFontes(List<File> arquivos, TableView<ArquivoFonte> tabela){
        arquivos.stream()
                .filter(File::isFile)
                .filter((fonte) -> fonte.getName().endsWith(".prg"))
                .forEach((fonte) -> listaArquivosFontes.add(new ArquivoFonte(fonte.getAbsolutePath())));
        tabela.setItems(getArquivosFontes());
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
