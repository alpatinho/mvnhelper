package Core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AcessoVariaveis {

    private static Properties prop = new Properties();
    private Util util = new Util();

    public String getValor(Enums.Campos campo) {
        String valor = "";
        try {
            prop.load(new FileInputStream(Enums.ConfigPath.VARIAVEIS_LOCAIS.getCaminho()));
            valor = prop.getProperty(campo.toString());
        } catch (IOException e) {
            util.exibeMensagem(Enums.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        }
        return valor;
    }

    public void setValor(Enums.Campos campo, String valor) {
        try {
            if(valor == null) valor = "";
            prop.setProperty(campo.toString(), valor);
            prop.store(new FileOutputStream(Enums.ConfigPath.VARIAVEIS_LOCAIS.getCaminho()), null);
        } catch (IOException e) {
            util.exibeMensagem(Enums.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        }
    }

    public ObservableList getListaValores(String lista) {
        try (Stream<String> stream = Files.lines(Paths.get(lista))) {
             return stream.collect(Collectors.toCollection(FXCollections::observableArrayList));
        } catch (IOException e) {
            util.exibeMensagem(Enums.Mensagens.ERRO_ARQUIVO_CONFIGURACAO, true);
        }
        return null;
    }

    public void setPathFontes(String caminhoArquivo, String caminhoFontes){
        try {
            Stream<String> lines = Files.lines(Paths.get(caminhoArquivo));
            List<String> replaced = lines.map(line -> line.replace("#", caminhoFontes)).collect(Collectors.toList());
            Files.write(Paths.get(caminhoArquivo), replaced);
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String tradutorMapeamento(String sigla){
        try(Scanner reader  = new Scanner(Files.newBufferedReader(Paths.get("map.prop")))){
            //reader.useDelimiter("[\n]");
            String mapeamento;
            while (reader.hasNext()) {
                mapeamento = reader.next();
                if(mapeamento.contains(":")) if (mapeamento.contains(sigla.toUpperCase())) return reader.next();
            }

        } catch (IOException e) {
            util.exibeMensagem(Enums.Mensagens.ERRO_TRIZONHO, true);
        }
        return null;
    }
}