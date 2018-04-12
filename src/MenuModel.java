import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuModel {

    private PathAcesso diretorios = new PathAcesso();
    private Stage busca = new Stage();
    private String debug = "";
    private String nomeExe = "";
    private Image logoAcc = new Image(getClass().getResourceAsStream("./Img/Acc_Logo.png"));

    public String buscaDiretorio(String dir){
        /*String caminho = diretorios.buscaDiretorio(busca);
        if (caminho != null) {
            valores.setValorPadrao(dir, caminho);
        }
        return caminho;*/
        return null;
    }

    //diferencias uma compilacao da outra
    public void Compila(){
        /*try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/Scripts/compila.bat",
                    TFMacrosistema.getText(),
                    "mvn",
                    "clean",
                    "install",
                    debug
            });

            //Captura o nome do exe
            File folder = new File(valores.getValorPadrao("DirMacrosistema")+"\\target\\classes\\win32-bcc5.x-xhb0.99.x");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().endsWith(".exe"))
                        nomeExe = listOfFiles[i].getName();
                    TFOrigemExe.setText(valores.getValorPadrao("DirOrigemExe")+"\\"+nomeExe);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/Scripts/compila.bat",
                    TFSubsistema.getText(),
                    "mvn",
                    "clean",
                    "install",
                    debug
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/




    }

    public void debug(boolean comDebug){
        if (comDebug){
            debug = "-Ddebug";
        }else{
            debug = "";
        }
    }

    //parametros...
    public void Mover(){
        //efetua a copia do exe para a pasta destino
        /*try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/Scripts/copia.bat",
                    TFOrigemExe.getText(),
                    TFDestinoExe.getText()
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Concatena o destino com o nome do exe para execucao rapida
        TFCaminhoExecucao.setText(TFDestinoExe.getText()+"\\"+nomeExe);*/
    }

    //parametros...
    public void executar(){
       /* //Captura do path do exe soh o diretorio
        String DirExeExecucao[];
        String DirExecucao = "";
        DirExeExecucao = TFCaminhoExecucao.getText().replace("\\", "\\#").split("#");
        for (int i = 0; i < DirExeExecucao.length - 1; i++) {
            DirExecucao += DirExeExecucao[i];
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (CBBanco.getValue() != null){
            CBBanco.setPromptText((String) CBBanco.getValue());
            valores.setValorPadrao("Bancos", CBBanco.getPromptText());
        }

        //coloca o texto selecionado como PromptText pra execucao
        if (CBAgencia.getValue() != null){
            CBAgencia.setPromptText((String) CBAgencia.getValue());
            valores.setValorPadrao("Agencias", CBAgencia.getPromptText());
        }

        //efetua a execucao do sistema
        try {
            Runtime.getRuntime().exec(new String[]{
                    "cmd.exe",
                    "/c",
                    "start",
                    System.getProperty("user.dir") + "./src/Scripts/executa.bat",
                    TFSetBanco.getText(),
                    CBBanco.getPromptText(),
                    DirExecucao,
                    TFCaminhoExecucao.getText(),
                    CBAgencia.getPromptText()

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //define os novos valores pardrao para a execucao
        valores.setValorPadrao("DirExeExecucao", TFCaminhoExecucao.getText());
        valores.setValorPadrao("DefaultSetBanco", TFSetBanco.getText());
        valores.setValorPadrao("DefaultBanco", CBBanco.getPromptText());
        valores.setValorPadrao("DefaultAgencia", CBAgencia.getPromptText());*/
    }
}
