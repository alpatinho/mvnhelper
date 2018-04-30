import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;

public class Util {

    public static final String LOGO_MVNHELPER = "src/Dados/Img/Logo_MvnHelper.jpg";
    public static final String LOGO_ACC = "src/Dados/Img/Logo_Acc.png";
    public static final String SCRIPT_COMPILACAO = System.getProperty("user.dir") + "/src/Dados/Scripts/compila.bat";
    public static final String SCRIPT_EXECUCAO = System.getProperty("user.dir") + "/src/Dados/Scripts/executa.bat";
    public static final String VARIAVEIS_LOCAIS = "src/Dados/VariaveisLocais.prop";
    public static final String LISTA_AGENCIAS = "src/Dados/ListaAgencias.prop";
    public static final String LISTA_BANCOS = "src/Dados/ListaBancos.prop";

    public File stringToFile(String diretorio){
        if(diretorio == null || diretorio.equals("")){
            return null;
        }

        File file = new File(diretorio);
        if (file.canRead()){
            return file;
        }else {
            return null;
        }
    }

    public void exibeMensagem(Mensagens mensagem, boolean erroFatal){
        Alert boxMensagem;
        if(erroFatal){
            boxMensagem = new Alert(Alert.AlertType.ERROR);
        }else {
            boxMensagem = new Alert(Alert.AlertType.INFORMATION);
        }
        boxMensagem.setContentText(mensagem.toString());
        boxMensagem.setHeaderText(mensagem.TITULO);
        boxMensagem.setContentText(mensagem.DETALHE);
        boxMensagem.showAndWait();
    }

    public boolean exibeEscolha(Mensagens mensagens){
        Alert escolha = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

        escolha.setTitle(mensagens.toString());
        escolha.setHeaderText(mensagens.TITULO);
        escolha.setContentText(mensagens.DETALHE);

        escolha.getButtonTypes().setAll(btnSim, btnNao);
        escolha.showAndWait().ifPresent(botao -> {
            if (botao == btnSim.) {
                return true;
            } else{
                return false;
            }
        });
        return false;
    }

    public enum Campos {
        SUBSISTEMA,
        MACROSISTEMA,
        DESTINOCOPIA,
        NOMEEXE,
        EXECUCAO,
        SETBANCO,
        BANCO,
        AGENCIA,
        SALVARCK,
        DEBUG,
        SOBREESCREVER,
    }

    public enum TipoArquivo {
        EXE("Executavel",".exe"),
        XML("XML", ".xml");

        public String DESCRICAO;
        public String EXTENSAO;

        TipoArquivo(String descricao, String extensao) {
            this.DESCRICAO = descricao;
            this.EXTENSAO = extensao;
        }
    }

    public enum Mensagens {
        ORIGEM_INVALIDA("Selecione o Executavel manualmente", "A busca automatica é feita a partir do Macrosistema, verifique"),
        DESTINO_INVALIDO("Destino do Arquivo Invalido", "Por Favor, Verifique!"),
        EXE_INVALIDO("EXE SELECIONADO INVALIDO", "Teste novamente!"),
        EXE_NAO_ENCONTRADO("Ops!, Exe não encontrado", "Por Favor Selecione o arquivo manualmente"),
        SOBREESCREVER_EXE("Exe Encontrado no destino", ""),
        EXE_COPIADO_SUCESSO("Executavel Copiado com Sucesso", "(° ʖ °)"),
        COPIA_CANCELADA("Operacao Cancelada", ""),
        ERRO_COPIAR("Erro ao copiar Arquivo", "Tente  novamente, ou não, vai saber né?"),
        ERRO_TRIZONHO("Rolou um erro Trizonho", "Foi mal ai, não sei o que deu");

        private String TITULO;
        private String DETALHE;

        Mensagens(String titulo, String detalhe){
            this.TITULO = titulo;
            this.DETALHE = detalhe;
        }
    }

    public enum OpcoesCompilacao {
        DEBUG("-Ddebug");

        public String paramentro;

        OpcoesCompilacao(String parametro){
            this.paramentro = parametro;
        }
    }

}
