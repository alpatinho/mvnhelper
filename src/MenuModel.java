import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class MenuModel {

    private Acesso valores = new Acesso();
    private String debug = "";
    private String pathToScipts = System.getProperty("user.dir") + "/src/Scripts/";

    public enum tipoArquivo{
        EXE(".exe");

        public String tipo;

        tipoArquivo(String t) {
            tipo = t;
        }

    }

    //situacao - OK
    public void buscaValorTela (TextField campo, boolean arquivo){
        String valorFinal = "";
        String valorBusca = null;
        if(converteFileValido(campo.getText(), null) == null){

        }
        if (arquivo){
            if(buscaArquivo(campo.getText()) != null){
                valorFinal = buscaArquivo(campo.getText()).getAbsolutePath();
            }
        }else {
            if(buscaDiretorio(campo.getText()) != null){
                valorFinal = buscaDiretorio(campo.getText()).getAbsolutePath();
            }
        }
        campo.setText(valorFinal);
    }
    // situacao - OK
    public File converteFileValido(String diretorio, String mensagemErro){
        String mensagemPadrao = "Diretório Invalido";
        if (mensagemErro == null){
            mensagemErro = mensagemPadrao;
        }
        if(diretorio == null){
            mensagem(mensagemErro);
            return null;
        }
        File file = new File(diretorio);
        if (!file.canRead()){
            mensagem(mensagemErro);
            return null;
        }else {
            return file;
        }
    }
    // situacao - OK

    public File buscaArquivo(String diretorioBusca) {
        // estancia da janela
        Stage busca = new Stage();
        FileChooser fileChooser = new FileChooser();

        // detalhe da janela
        fileChooser.setTitle("EXECUTAVEL");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Executavel", tipoArquivo.EXE.tipo));

        // buscaCaminhoExe apartir do valor definido
        if (converteFileValido(diretorioBusca, null) != null) {
            fileChooser.setInitialDirectory(new File(diretorioBusca));
        }

        File selectedFile = fileChooser.showOpenDialog(busca);

        // retorna o caminho completo
        if (selectedFile != null) {
            return selectedFile;
        }

        return null;
    }

    // situacao - OK
    public File buscaDiretorio(String diretorioBusca){

        // estancia da janela
        Stage busca = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // detalhe da janela
        directoryChooser.setTitle("DIRETORIO");

        // buscaCaminhoExe apartir do valor definido

        if (converteFileValido(diretorioBusca, null) != null) {
            directoryChooser.setInitialDirectory(new File(diretorioBusca));
        }

        File selectedDiretory = directoryChooser.showDialog(busca);

        if (selectedDiretory != null) {
            return selectedDiretory;
        }

        return null;
    }

    // situacao - OK - Aguarda implementacao novas features
    public void Compila(TextField sistema){
        /*
        * Compila e buscaCaminhoExe o nome do EXE
        * define o nome na configuracao do nome
        */

        try {
            Runtime.getRuntime().exec(
                new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    pathToScipts + "compila.bat",
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

    // situacao - ERROS
    public boolean mover(){

        File origem = converteFileValido(valores.getValor(Campos.MACROSISTEMA), "Diretório de origem Invalido");
        File destino = converteFileValido(valores.getValor(Campos.MOVERDESTINO), "Diretorio de destino Invalido");
        String origemFinal;
        String destinoFinal;
        String nomeExe;

        //verifica a origem e captura o nome do exe
        if(origem == null){
            return false;
        }else {
            //procura o exe dentro da origem, se nao encontrar solicida manualmente
            if(buscaCaminhoExe(origem.getAbsolutePath(), tipoArquivo.EXE.tipo) == null){
                mensagem("Exe não encontrado");
                nomeExe = buscaArquivo(origem.getAbsolutePath()).getName();
            }else {
                nomeExe = buscaCaminhoExe(origem.getAbsolutePath(), tipoArquivo.EXE.tipo).getName();
            }
            origemFinal = origem.getAbsolutePath() + "\\" + nomeExe;;
        }

        if(destino == null){
            return false;
        }else {
            destino = converteFileValido(valores.getValor(Campos.MOVERDESTINO), null);
            destinoFinal = destino.getAbsolutePath() + "\\" + nomeExe;
        }

        valores.setValor(Campos.NOMEEXE, nomeExe);

        // define os caminhos para a copia
        Path caminhoOrigem = Paths.get(origemFinal);
        Path caminhoDestino = Paths.get(destinoFinal);
        //Files.size()

        // inicia a copia
        try {
            Files.deleteIfExists(caminhoDestino);
            Files.copy(caminhoOrigem,
                        caminhoDestino);
            mensagem("COPIADO COM SUCESSO!");

        } catch(Exception e) {
            mensagem("Erro Trizonho");
            return false;
        }
        valores.setValor(Campos.EXECUCAO, destino.getAbsolutePath());
        return true;
    }

    // situacao - OK
    public File buscaCaminhoExe(String origemBusca, String tipoArquivosBusca){
        ArrayList<File> arquivosEncontrados = new ArrayList<>();
        auxBuscaCaminhoArquivo(converteFileValido(origemBusca, "Origem da buscaCaminhoExe por arquivo Invalida"), arquivosEncontrados, tipoArquivosBusca);
        for(File arquivo: arquivosEncontrados)
            if (arquivo != null) {
                return arquivo;
            }
        return null;
    }

    // auxiliar do metodo anterior
    private void auxBuscaCaminhoArquivo(File diretorioBusca , ArrayList<File> arquivosEncontrados, String tipoArquivosBusca){

        File[] arquivos = diretorioBusca.listFiles();
        if(arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    if (arquivo.getName().endsWith(tipoArquivosBusca)) {
                        arquivosEncontrados.add(arquivo);
                    }
                } else if (arquivo.isDirectory()) {
                    auxBuscaCaminhoArquivo(arquivo, arquivosEncontrados, tipoArquivosBusca);
                }
            }
        }
    }

    // situacao - INCOMPLETO
    public void executar(TextField execucao, TextField setbanco, ComboBox banco, ComboBox agencia){

        //Captura do path do exe soh o diretorio
        String DirExeExecucao[];
        String DirExecucao = "";
        DirExeExecucao = execucao.getText().replace("\\", "\\#").split("#");
        for (int i = 0; i < DirExeExecucao.length - 1; i++) {
            DirExecucao += DirExeExecucao[i];
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (banco.getValue() != null){
            banco.setPromptText(banco.getValue().toString());
            valores.setValor(Campos.BANCO, banco.getPromptText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (agencia.getValue() != null){
            agencia.setPromptText(agencia.getValue().toString());
            valores.setValor(Campos.AGENCIA, agencia.getPromptText());
        }

        //efetua a execucao do sistema
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    pathToScipts + "executa.bat",
                    setbanco.getText(),
                    banco.getPromptText(),
                    DirExecucao,
                    execucao.getText(),
                    agencia.getPromptText()

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //define os novos valores pardrao para a execucao
        valores.setValor(Campos.EXECUCAO, execucao.getText());
        valores.setValor(Campos.SETBANCO, setbanco.getText());
        valores.setValor(Campos.BANCO, banco.getPromptText());
        valores.setValor(Campos.AGENCIA, agencia.getPromptText());
    }

    // situacao - OK
    public void mensagem(String detalhe){
        Alert aviso = new Alert(Alert.AlertType.WARNING);
        aviso.setTitle("AVISO!");
        aviso.setHeaderText(detalhe);
        aviso.showAndWait();
    }

    // situacao - CONVERTER NO METODO OPCOES
    public void debug(boolean comDebug){
        if (comDebug){
            debug = "-Ddebug";
        }else{
            debug = "";
        }
    }
}
