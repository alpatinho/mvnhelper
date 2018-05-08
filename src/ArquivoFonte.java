import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

class ArquivoFonte {

    private StringProperty nomeArquivo;
    private StringProperty caminhoArquivo;

    ArquivoFonte(){
        this(null);
    }

    ArquivoFonte(String caminhoArquivo) {
        this.caminhoArquivo = new SimpleStringProperty(caminhoArquivo);
        this.nomeArquivo= new SimpleStringProperty(new File(caminhoArquivo).getName());
    }

    String getNomeArquivo() {
        return nomeArquivo.get();
    }

    void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo.set(nomeArquivo);
    }

    StringProperty nomeArquivoProperty() {
        return nomeArquivo;
    }

    String getCaminhoArquivo() {
        return caminhoArquivo.get();
    }

    void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo.set(caminhoArquivo);
    }

    StringProperty caminhoArquivoProperty() {
        return caminhoArquivo;
    }
}
