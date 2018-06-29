package Core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

public class ArquivoFonte {

    private StringProperty nomeArquivo;
    private StringProperty caminhoArquivo;

    public ArquivoFonte(String caminhoArquivo) {
        this.caminhoArquivo = new SimpleStringProperty(caminhoArquivo);
        this.nomeArquivo= new SimpleStringProperty(new File(caminhoArquivo).getName());
    }

    public StringProperty nomeArquivoProperty() {
        return nomeArquivo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo.get();
    }

    public StringProperty caminhoArquivoProperty() {
        return caminhoArquivo;
    }
}
