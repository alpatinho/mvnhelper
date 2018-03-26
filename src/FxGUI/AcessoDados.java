package FxGUI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AcessoDados {

    private static Properties conf = new Properties();
    private static String confPatch = "FxGUI/Data/DefaultValues.conf";

    public String getValorPadrao(String campo){
        String valor;
        try{
            conf.load(new FileInputStream(confPatch));
            valor = conf.getProperty(campo);
        }catch (IOException e){
            return e.getMessage();
        }
        return valor;
    }

    public void setValorPadrao(String campo, String valor){
        try{
            conf.setProperty(campo, valor);
            conf.store(new FileOutputStream(confPatch), null);
        }catch (IOException e){
            e.getMessage();
        }
    }
}