import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuModel {

    private DataAcesso valores = new DataAcesso();
    private PathAcesso diretorios = new PathAcesso();
    private Stage busca = new Stage();
    private String debug = "";
    private String nomeExe = "";
    private Image logoAcc = new Image(getClass().getResourceAsStream("./Img/Acc_Logo.png"));
    private String pathToScipts = System.getProperty("user.dir") + "Data/Scripts/";
    private String pathToExe = "\\target\\classes\\win32-bcc5.x-xhb0.99.x";

    public String buscaArquivo(Campos dir, Campos padraoBusca){
        String caminho = diretorios.buscaArquivo(busca, padraoBusca);
        if (caminho != null) {
            valores.setValorPadrao(dir, caminho);
        }
        return caminho;
    }

    public String buscaDiretorio(Campos dir, Campos padraoBusca){
        String caminho = diretorios.buscaDiretorio(busca, padraoBusca);
        if (caminho != null) {
            valores.setValorPadrao(dir, caminho);
        }
        return caminho;
    }

    public void Compila(TextField sistema, TextField origem){
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

            //Captura e salva o nome do exe define valor de origem
            if(origem != null) {
                File folder = new File(valores.getValorPadrao(Campos.MACROSISTEMA) + pathToExe);
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        if (listOfFiles[i].getName().endsWith(".exe"))
                            valores.setValorPadrao(Campos.NOMEEXE, listOfFiles[i].getName()+ "\\");
                        origem.setText(valores.getValorPadrao(Campos.SUBSISTEMA) + nomeExe);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void debug(boolean comDebug){
        if (comDebug){
            debug = "-Ddebug";
        }else{
            debug = "";
        }
    }

    public void Mover(TextField origem, TextField destino, TextField execucao){
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    pathToScipts + "copia.bat",
                    origem.getText(),
                    destino.getText(),
                    destino.getText() + valores.getValorPadrao(Campos.NOMEEXE),
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Concatena o destino com o nome do exe para execucao rapida
        execucao.setText(destino.getText()+ valores.getValorPadrao(Campos.NOMEEXE));
    }

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
            valores.setValorPadrao(Campos.BANCO, banco.getPromptText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (agencia.getValue() != null){
            agencia.setPromptText(agencia.getValue().toString());
            valores.setValorPadrao(Campos.AGENCIA, agencia.getPromptText());
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
        valores.setValorPadrao(Campos.EXECUCAO, execucao.getText());
        valores.setValorPadrao(Campos.SETBANCO, setbanco.getText());
        valores.setValorPadrao(Campos.BANCO, banco.getPromptText());
        valores.setValorPadrao(Campos.AGENCIA, agencia.getPromptText());
    }
}
