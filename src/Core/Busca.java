package Core;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Busca {

    private Util util = new Util();
    private AcessoVariaveis acessoVariaveis = new AcessoVariaveis();

    public String caminho(String caminho, boolean arquivo){
        if(arquivo){
            try {
                return auxArquivo(caminho).getAbsolutePath();
            }catch(NullPointerException e){
                return null;
            }
        }else {
            try {
                return auxDiretorio(caminho).getAbsolutePath();
            }catch(NullPointerException e){
                return null;
            }
        }
    }

    public String caminho(TextField caminho, Enums.Campos campo, boolean arquivo){
        if(arquivo){
            try {
                String aux = auxArquivo(caminho.getText()).getAbsolutePath();
                acessoVariaveis.setValor(campo, caminho.getText());
                return aux;
            }catch(NullPointerException e){
                return null;
            }
        }else {
            try {
                String aux = auxDiretorio(caminho.getText()).getAbsolutePath();
                acessoVariaveis.setValor(campo, caminho.getText());
                return aux;
            }catch(NullPointerException e){
                return null;
            }
        }
    }

    private File auxArquivo(String diretorioBusca) {
        // estancia da janela
        Stage busca = new Stage();
        FileChooser fileChooser = new FileChooser();

        // detalhe da janela
        fileChooser.setTitle(Enums.TipoArquivo.EXE.getDescricao());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Enums.TipoArquivo.EXE.getDescricao(), Enums.TipoArquivo.EXE.getExtensao()));

        // caminhoExe apartir do valor definido
        if (util.stringToFile(diretorioBusca) != null) {
            fileChooser.setInitialDirectory(new File(util.stringToFile(diretorioBusca).getParent()));
        }

        return fileChooser.showOpenDialog(busca);
    }

    private File auxDiretorio(String diretorioBusca){
        // estancia da janela
        Stage busca = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // detalhe da janela
        directoryChooser.setTitle("DIRETORIO");

        // caminhoExe apartir do valor definido
        if (util.stringToFile(diretorioBusca) != null) {
            directoryChooser.setInitialDirectory(new File(diretorioBusca));
        }

        return directoryChooser.showDialog(busca);

    }

    public List<File> multiArquivos(String caminho){
        try {
            return auxMultiplosArquivos(caminho);
        }catch(NullPointerException e){
            return null;
        }
    }

    private List<File> auxMultiplosArquivos(String diretorioBusca){
        // estancia da janela
        Stage busca = new Stage();
        FileChooser fileChooser = new FileChooser();

        // detalhe da janela
        fileChooser.setTitle(Enums.TipoArquivo.PRG.getDescricao());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(Enums.TipoArquivo.PRG.getDescricao(), Enums.TipoArquivo.PRG.getExtensao()),
                new FileChooser.ExtensionFilter(Enums.TipoArquivo.ANY.getDescricao(), Enums.TipoArquivo.ANY.getExtensao()));

        // caminhoExe apartir do valor definido
        if (util.stringToFile(diretorioBusca) != null) {
            fileChooser.setInitialDirectory(new File(util.stringToFile(diretorioBusca).getParent()));
        }

        return fileChooser.showOpenMultipleDialog(busca);
    }

    public File caminhoExe(File origemBusca){
        ArrayList<File> arquivosEncontrados = new ArrayList<>();
        auxCaminhoExe(origemBusca, arquivosEncontrados);
        for (File arquivo : arquivosEncontrados) {
            if (arquivo != null) {
                return arquivo;
            }
        }
        return null;
    }

    private void auxCaminhoExe(File diretorioBusca , ArrayList<File> arquivosEncontrados){

        File[] arquivos = diretorioBusca.listFiles();
        if(arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    if (arquivo.getName().endsWith(".exe")) {
                        arquivosEncontrados.add(arquivo);
                        return;
                    }
                } else if (arquivo.isDirectory()) {
                    auxCaminhoExe(arquivo, arquivosEncontrados);
                }
            }
        }
    }

    String mapeamento(String Sigla){
        return null;
    }

}
