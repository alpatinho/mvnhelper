package Core;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Util {

    public File stringToFile(String diretorio){
        if(diretorio == null || diretorio.equals("")){
            return null;
        }

        File file = new File(diretorio);
        if (file.canRead()){
            return file;
        }else {
            return null;
        }
    }

    public String copia(Path origem, Path destino, boolean sobreescrever){
        String arquivo = origem.getFileName().toString();
        destino = Paths.get(destino + "\\" + arquivo);
        if(origem.equals(destino)){
            exibeMensagem(Enums.Mensagens.ORIGEM_DESTINO_IGUAIS, false);
        }

        try {
            if(sobreescrever){
                Files.copy(origem, destino, REPLACE_EXISTING);
            }else {
                if(Files.exists(destino)){
                    if (exibeEscolha(Enums.Mensagens.SOBREESCREVER)){
                        Files.copy(origem, destino, REPLACE_EXISTING);
                    }else {
                        exibeMensagem(Enums.Mensagens.OPERACAO_CANCELADA, true);
                        return null;
                    }
                }
                Files.copy(origem, destino);
            }
        } catch (NoSuchFileException e) {
            exibeMensagem(Enums.Mensagens.ARQUIVO_NAO_ENCONTRADO, true);
        } catch (Exception err) {
            exibeMensagem(Enums.Mensagens.ERRO_COPIAR, true);
            err.printStackTrace();
        }
        return destino.toString();
    }

    public void exibeMensagem(Enums.Mensagens mensagem, boolean erroFatal){
        Alert boxMensagem;
        if(erroFatal){
            boxMensagem = new Alert(Alert.AlertType.ERROR);
            boxMensagem.setContentText("Erro!");
        }else {
            boxMensagem = new Alert(Alert.AlertType.INFORMATION);
            boxMensagem.setContentText("Aviso!");
        }
        boxMensagem.setHeaderText(mensagem.getTitulo());
        boxMensagem.setContentText(mensagem.getDetalhe());
        boxMensagem.showAndWait();
    }

    public boolean exibeEscolha(Enums.Mensagens mensagens){
        Alert escolha = new Alert(Alert.AlertType.CONFIRMATION);
        new ButtonType("SIM", ButtonBar.ButtonData.OK_DONE);
        new ButtonType("NAO", ButtonBar.ButtonData.CANCEL_CLOSE);

        escolha.setTitle(mensagens.toString());
        escolha.setHeaderText(mensagens.getTitulo());
        escolha.setContentText(mensagens.getDetalhe());
        Optional<ButtonType> result = escolha.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void getMap(){
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(new String[]{ "cmd.exe", "/c", "net", "use", ">", "map.prop"});
            runtime.runFinalization();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
