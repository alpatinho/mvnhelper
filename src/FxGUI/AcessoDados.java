package FxGUI;

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

public class AcessoDados {

    private static Properties conf = new Properties();
    private static String dataPatch = "./src/FxGUI/Data/";

    public String getValorPadrao(String campo) {
        String valor;
        try {
            conf.load(new FileInputStream(dataPatch + "DefaultValues.conf"));
            valor = conf.getProperty(campo);
        } catch (IOException e) {
            return e.getMessage();
        }
        return valor;
    }

    public void setValorPadrao(String campo, String valor) {
        try {
            conf.setProperty(campo, valor);
            conf.store(new FileOutputStream(dataPatch + "DefaultValues.conf"), null);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public ArrayList getValores(String arquivo) {
        ArrayList<String> valores = new ArrayList<>();

        Path local = Paths.get(dataPatch + arquivo + ".txt");
        try (Scanner sc = new Scanner(Files.newBufferedReader(local, Charset.defaultCharset()))) {
            sc.useDelimiter("\r\n|\n");
            while (sc.hasNext()) {
                valores.add(sc.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return valores;
    }
}