import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

//classe funcional, porem ineficiente, por enquanto ta bom...
public class AcessoVariaveis {

    private static Properties prop = new Properties();
    private Util util = new Util();

    public String getValor(Util.Campos campo) {
        String valor = "";
        try {
            prop.load(new FileInputStream(Util.VARIAVEIS_LOCAIS));
            valor = prop.getProperty(campo.toString());
        } catch (IOException e) {
            util.exibeMensagem(Util.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        } catch (Exception err){
            return err.getMessage();
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
            util.exibeMensagem(Util.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        } catch (Exception err){
            err.getMessage();
        }
    }

    public ObservableList getListaValores(String lista) {
        ArrayList<String> valores = new ArrayList<>();
        ObservableList<String> valoresFinal = FXCollections.observableList(valores);

        try (Scanner leitura = new Scanner(new File(lista))) {
            leitura.useDelimiter("\r\n|\n");
            while (leitura.hasNext()) {
                valores.add(leitura.next());
            }
        } catch (IOException e) {
            util.exibeMensagem(Util.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        } catch (Exception err){
            err.getMessage();
        }
        return valoresFinal;
    }

}