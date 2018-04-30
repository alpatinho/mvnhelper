import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

//classe funcional, porem ineficiente, por enquanto ta bom...
public class AcessoVariaveis {

    private static Properties prop = new Properties();

    public String getValor(Util.Campos campo) {
        String valor;
        try {
            prop.load(new FileInputStream(Util.VARIAVEIS_LOCAIS));
            valor = prop.getProperty(campo.toString());
        } catch (IOException e) {
            return e.getMessage();
        }
        return valor;
    }

    public void setValor(Util.Campos campo, String valor) {
        if(valor == null){
            valor = "";
        }
        try {
            prop.setProperty(campo.toString(), valor);
            prop.store(new FileOutputStream(Util.VARIAVEIS_LOCAIS), null);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public ObservableList getListaValores(String lista) {
        ArrayList<String> valores = new ArrayList<>();
        ObservableList<String> valoresFinal = FXCollections.observableList(valores);
        Path local = Paths.get(lista);
        try (Scanner sc = new Scanner(Files.newBufferedReader(local, Charset.defaultCharset()))) {
            sc.useDelimiter("\r\n|\n");
            while (sc.hasNext()) {
                valores.add(sc.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valoresFinal;
    }
}