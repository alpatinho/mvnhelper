package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configs {

    public static Properties prop = new Properties();

    public void saveProp(String campo, String valor) {
        try {
            prop.setProperty(campo, valor);
            prop.store(new FileOutputStream("FxGUI/Data/DefaultValues.conf"), null);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public String getProp(String campo) {
        String valor = "";
        try {
            prop.load(new FileInputStream("FxGUI/Data/DefaultValues.conf"));
            valor = prop.getProperty(campo);
        } catch (IOException e) {
            e.getMessage();
        }
        
        return valor;
    }


}
