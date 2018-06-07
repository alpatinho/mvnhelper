import Core.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.input.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

class AbaDebug {


    private javafx.collections.ObservableList<ArquivoFonte> listaArquivosFontes = FXCollections.observableArrayList();
    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();
    private Busca busca = new Busca();
    private Util util = new Util();

    ObservableList<ArquivoFonte> getArquivosFontes(){
        return listaArquivosFontes;
    }

    void salvaCaminhoFontes(String caminhoExecucao, String caminhoFontes, boolean sobreescrever){
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

    void buscaListaFontes(String caminho, TableView<ArquivoFonte> tabela){
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

    void atualizaFontesDestino(String destinoFontes){
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

    void dragTabelaFontes(final DragEvent drag){
        drag.acceptTransferModes(TransferMode.COPY);
        drag.consume();
    }

    void dropTabelaFontes(final DragEvent drop, TableView<ArquivoFonte> tabela){
        final Dragboard db = drop.getDragboard();
        if(db.hasFiles()) addListaFontes(db.getFiles(), tabela);
        drop.consume();
    }

    void doubleClickFonte(MouseEvent event, TableView<ArquivoFonte> tabela) {
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1)
            exibeArquivoExplorer(tabela.getItems().get(tabela.getSelectionModel().getFocusedIndex()).getCaminhoArquivo());
    }

}
