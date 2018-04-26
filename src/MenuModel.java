import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MenuModel {

    private Acesso valores = new Acesso();
    private String debug = "";
    private String pathToScipts = System.getProperty("user.dir") + "/src/Scripts/";

    // situacao - OK
    public boolean validaDiretorioSelecionado (String diretorio){
        if(diretorio == null){
            return false;
        }
        File file = new File(diretorio);
        return file.canRead();
    }

    // situacao - OK
    public String buscaArquivo(String diretorioBusca) {
        // estancia da janela
        Stage busca = new Stage();
        FileChooser fileChooser = new FileChooser();

        // detalhe da janela
        fileChooser.setTitle("EXECUTAVEL");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Executavel", "*.exe"));

        // busca apartir do valor definido
        if(validaDiretorioSelecionado(diretorioBusca)){
            fileChooser.setInitialDirectory(new File(diretorioBusca));
        }
        File selectedFile = fileChooser.showOpenDialog(busca);

        // retorna o caminho completo
        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        }

        return null;
    }

    // situacao - OK
    public String buscaDiretorio(String diretorioBusca){

        // estancia da janela
        Stage busca = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // detalhe da janela
        directoryChooser.setTitle("DIRETORIO");

        // busca apartir do valor definido
        if(validaDiretorioSelecionado(diretorioBusca)) {
            directoryChooser.setInitialDirectory(new File(diretorioBusca));
        }
        File selectedDiretory = directoryChooser.showDialog(busca);

        if (selectedDiretory != null) {
            return selectedDiretory.getAbsolutePath();
        }

        return null;
    }

    // situacao - OK
    public void Compila(TextField sistema){
        /*
        * Compila e busca o nome do EXE
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

    // situacao - OK
    public void debug(boolean comDebug){
        if (comDebug){
            debug = "-Ddebug";
        }else{
            debug = "";
        }
    }

    // situacao - OK (NECESSITA REFATORACAO)
    public boolean mover(String destino){

        String origem = valores.getValor(Campos.MACROSISTEMA);
        System.out.println(origem);
        String nomeExe;

        if(!validaDiretorioSelecionado(origem)){
            //origem invalida, busca manual pelo exe
            mensagem("Executavel não encontrado");
            origem = buscaArquivo(null);
            File exeManual = new File(origem);
            nomeExe = exeManual.getName();

        }else{
            //origem valida, busca automatica pelo exe
            File result = buscaNomeExe(origem);
            nomeExe = result.getName();
            System.out.println(nomeExe);
            if (!validaDiretorioSelecionado(result.getAbsolutePath())){
                mensagem("Verifique se foi gerado o exe corretamente");
                return false;
            }
            destino = result.getParent();
        }

        if (!validaDiretorioSelecionado(destino)){
            mensagem("Destino invalido!");
            return false;
        }else {
            destino +=  "\\" + nomeExe;
        }


        // define os caminhos para a copia
        Path caminhoOrigem = Paths.get(origem);
        Path caminhoDestino = Paths.get(destino);
        //Files.size()

        // inicia a copia
        try {
            Files.deleteIfExists(caminhoDestino);
            Files.copy(caminhoOrigem,
                        caminhoDestino);
            mensagem("COPIADO COM SUCESSO!");

        } catch(FileAlreadyExistsException e) {
            mensagem("Arquivo já existente");
            return false;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // situacao - OK (NECESSITA REFATORACAO)
    public static File buscaNomeExe (String caminho){
        ArrayList<String> arquivos = new ArrayList<>();
        String[] Arquivo = busca(caminho, arquivos).toArray(new String[0]);
        File nomeCaminho = new File(Arquivo[0]);
        return nomeCaminho;
    }

    // subordinado ao anterior
    private static ArrayList<String> busca(String diretorio, ArrayList<String> arquivos){
        auxbuscaNomeExe(diretorio, arquivos);
        return arquivos;
    }

    // subordinado ao anterior
    private static void auxbuscaNomeExe (String diretorio , ArrayList<String> arquivosExes){

        File diretorioBusca = new File(diretorio);
        File[] arquivos = diretorioBusca.listFiles();

        for (File arquivo : arquivos) {
            if (arquivo.isFile()) {
                if(arquivo.getName().endsWith(".exe")) {
                    arquivosExes.add(arquivo.getAbsolutePath());
                }
            } else if (arquivo.isDirectory()) {
                auxbuscaNomeExe(arquivo.getAbsolutePath(), arquivosExes);
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
}
