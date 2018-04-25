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

//classe funcional porem ineficiente, por enquanto ta bom...
public class Acesso {

    private static Properties conf = new Properties();
    private static String confPatch = "./src/Data/";

    public String getValor(Campos campo) {
        String valor;
        try {
            conf.load(new FileInputStream(confPatch + "DefaultValues.conf"));
            valor = conf.getProperty(campo.toString());
        } catch (IOException e) {
            return e.getMessage();
        }
        return valor;
    }

    public void setValor(Campos campo, String valor) {
        try {
            conf.setProperty(campo.toString(), valor);
            conf.store(new FileOutputStream(confPatch + "DefaultValues.conf"), null);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public ObservableList getListaValores(Campos lista) {
        ArrayList<String> valores = new ArrayList<>();
        ObservableList<String> valoresFinal = FXCollections.observableList(valores);
        Path local = Paths.get(confPatch + lista.toString() + ".conf");
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