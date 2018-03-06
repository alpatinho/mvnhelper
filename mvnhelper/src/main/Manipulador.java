package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Manipulador {
    
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(""
                + "./properties/dados.properties");
        props.load(file);
        return props;
    }
    
    
    public static void main(String[] args) throws IOException {
        String compilacao;
        String banco;
        String componente;
        String macro;
        System.out.println("Teste leitura arquivo de properties");
        
        Properties prop = getProp();
        
        compilacao = prop.getProperty("prop.comando.compilacao");
        banco = prop.getProperty("prop.comando.banco");
        componente = prop.getProperty("prop.caminho.componente");
        macro = prop.getProperty("prop.caminho.macro");
                
        System.out.println("Comando compilação: " + compilacao);
        System.out.println("Comando banco: " + banco);
        System.out.println("Caminho componente: " + componente);
        System.out.println("Caminho macro-sistema: " + macro);
    }
}
