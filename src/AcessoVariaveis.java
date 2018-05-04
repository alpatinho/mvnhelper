import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//classe funcional, porem ineficiente, por enquanto ta bom...
class AcessoVariaveis {

    private static Properties prop = new Properties();
    private Util util = new Util();

    String getValor(Util.Campos campo) {
        String valor = "";
        try {
            prop.load(new FileInputStream(Util.ConfigPath.VARIAVEIS_LOCAIS.getCaminho()));
            valor = prop.getProperty(campo.toString());
        } catch (IOException e) {
            util.exibeMensagem(Util.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        }
        return valor;
    }

    void setValor(Util.Campos campo, String valor) {
        if(valor == null){
            valor = "";
        }
        try {
            prop.setProperty(campo.toString(), valor);
            prop.store(new FileOutputStream(Util.ConfigPath.VARIAVEIS_LOCAIS.getCaminho()), null);
        } catch (IOException e) {
            util.exibeMensagem(Util.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        }
    }

    ObservableList getListaValores(String lista) {
        ObservableList<String> valoresFinal;

        try (Stream<String> stream = Files.lines(Paths.get(lista))) {
             valoresFinal = stream.collect(Collectors.toCollection(FXCollections::observableArrayList));
             return valoresFinal;

        } catch (IOException e) {
            util.exibeMensagem(Util.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        }
        return null;
    }

    void setPathFontes(String caminhoArquivo, String caminhoFontes){
        try {
            Stream<String> lines = Files.lines(Paths.get(caminhoArquivo));
            List<String> replaced = lines.map(line -> line.replace("#", caminhoFontes)).collect(Collectors.toList());
            Files.write(Paths.get(caminhoArquivo), replaced);
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}