import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.util.Optional;

public class Util {

    public static final String LOGO_MVNHELPER = System.getProperty("user.dir") + "/Dados/Img/Logo_MvnHelper.jpg";
    public static final String LOGO_ACC = System.getProperty("user.dir") + "/Dados/Img/Logo_Acc.png";
    public static final String SCRIPT_COMPILACAO = System.getProperty("user.dir") + "/Dados/Scripts/compila.bat";
    public static final String SCRIPT_EXECUCAO = System.getProperty("user.dir") + "/Dados/Scripts/executa.bat";
    public static final String VARIAVEIS_LOCAIS = System.getProperty("user.dir") + "/Dados/VariaveisLocais.prop";
    public static final String LISTA_AGENCIAS = System.getProperty("user.dir") + "/Dados/ListaAgencias.prop";
    public static final String LISTA_BANCOS = System.getProperty("user.dir") + "/Dados/ListaBancos.prop";

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
            boxMensagem.setContentText("Erro!");
        }else {
            boxMensagem = new Alert(Alert.AlertType.INFORMATION);
            boxMensagem.setContentText("Aviso!");
        }
        boxMensagem.setHeaderText(mensagem.getTitulo());
        boxMensagem.setContentText(mensagem.getDetalhe());
        boxMensagem.showAndWait();
    }

    public boolean exibeEscolha(Mensagens mensagens){
        Alert escolha = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);

        escolha.setTitle(mensagens.toString());
        escolha.setHeaderText(mensagens.getTitulo());
        escolha.setContentText(mensagens.getDetalhe());
        Optional<ButtonType> result = escolha.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
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
        EXE("Executavel","*.exe"),
        XML("XML", ".xml");

        private String DESCRICAO;
        private String EXTENSAO;

        TipoArquivo(String descricao, String extensao) {
            this.DESCRICAO = descricao;
            this.EXTENSAO = extensao;
        }

        public String getDescricao(){
            return DESCRICAO;
        }
        public String getExtensao(){
            return EXTENSAO;
        }
    }

    public enum Mensagens {
        ERRO_ARQUIVO_CONFIGURACAO("Erro ao Ler arquivos de configuração", System.getProperty("user.dir")+"\\Dados"),
        ORIGEM_INVALIDA("Selecione o Executavel manualmente", "A busca automatica é feita a partir do Macrosistema, verifique"),
        DESTINO_INVALIDO("Destino do Arquivo Invalido", "Por Favor, Verifique!"),
        EXE_INVALIDO("EXE SELECIONADO INVALIDO", "Teste novamente!"),
        EXE_NAO_ENCONTRADO("Ops!, Exe não encontrado", "Por Favor Selecione o arquivo manualmente"),
        SOBREESCREVER_EXE("Exe Encontrado no destino", ""),
        EXE_COPIADO_SUCESSO("Executavel Copiado com Sucesso", "(° ʖ °)"),
        OPERACAO_CANCELADA("Operacao Cancelada", ""),
        ERRO_COPIAR("Erro ao copiar Arquivo", "Tente  novamente, ou não, vai saber né?"),
        ERRO_CAMINHO_EXECUCAO("Diretório de execucao inválido", "Selecione Novamente!"),
        ERRO_TRIZONHO("Rolou um erro Trizonho", "Foi mal ai, não sei o que deu");

        private String TITULO;
        private String DETALHE;

        Mensagens(String titulo, String detalhe){
            this.TITULO = titulo;
            this.DETALHE = detalhe;
        }

        public String getTitulo(){
            return TITULO;
        }

        public String getDetalhe(){
            return DETALHE;
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
