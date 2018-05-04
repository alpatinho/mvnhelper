import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.util.Optional;

class Util {

    static final String LOGO_MVNHELPER = System.getProperty("user.dir") + "/Dados/Img/Logo_MvnHelper.jpg";
    static final String LOGO_ACC = System.getProperty("user.dir") + "/Dados/Img/Logo_Acc.png";
    static final String SCRIPT_COMPILACAO = System.getProperty("user.dir") + "/Dados/Scripts/compila.bat";
    static final String SCRIPT_EXECUCAO = System.getProperty("user.dir") + "/Dados/Scripts/executa.bat";
    static final String VARIAVEIS_LOCAIS = System.getProperty("user.dir") + "/Dados/Variaveis/VariaveisLocais.prop";
    static final String LISTA_AGENCIAS = System.getProperty("user.dir") + "/Dados/Variaveis/ListaAgencias.prop";
    static final String LISTA_BANCOS = System.getProperty("user.dir") + "/Dados/Variaveis/ListaBancos.prop";
    static final String PATH_FONTES = System.getProperty("user.dir") + "/Dados/init.cld";

    File stringToFile(String diretorio){
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

    void exibeMensagem(Mensagens mensagem, boolean erroFatal){
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

    boolean exibeEscolha(Mensagens mensagens){
        Alert escolha = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("SIM", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNao = new ButtonType("NAO", ButtonBar.ButtonData.CANCEL_CLOSE);

        escolha.setTitle(mensagens.toString());
        escolha.setHeaderText(mensagens.getTitulo());
        escolha.setContentText(mensagens.getDetalhe());
        Optional<ButtonType> result = escolha.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public enum Campos {
        SUBSISTEMA,
        MACROSISTEMA,
        DEBUG,
        DESTINOCOPIA,
        EXECUCAO,
        SETBANCO,
        BANCO,
        AGENCIA,
        AUTOLOGIN,
        LOGIN,
        SENHA,
        FONTES,
        SALVARCK,
        SOBREESCREVER,
    }

    public enum TipoArquivo {
        ANY("Todos Arquivos", "*.*"),
        EXE("Executavel","*.exe"),
        PRG("Fonte Legado", "*.prg"),
        XML("XML", "*.xml");

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
        ARQUIVO_INVALIDO("ARQUIVO SELECIONADO INVALIDO", "Teste novamente!"),
        ARQUIVO_NAO_ENCONTRADO("Ops!, Arquivo não encontrado", "Por Favor Selecione o arquivo manualmente"),
        SOBREESCREVER("Arquivo Encontrado no destino", "Deseja sobreescrever ?"),
        OPERACAO_SUCESSO("Operação concluida com Sucesso", "(° ʖ °)"),
        OPERACAO_CANCELADA("Operacao Cancelada", ""),
        ERRO_COPIAR("Erro ao copiarEXE Arquivo", "Tente  novamente, ou não, vai saber né?"),
        ERRO_CAMINHO_EXECUCAO("Diretório de execucao inválido", "Selecione Novamente!"),
        ERRO_LOGIN_CARACTER_INVALIDO("Caracter não reconhecido", "Verifique o login automático"),
        ERRO_AUTOLOGGER("Ops! Erro no LogIn automático", "Tente novamente mais tarde"),
        EFETUAR_AUTO_LOGIN("Efetuar Login automático ?", ""),
        ERRO_SALVAR_CAMINHO_FONTES("Destino dos Fontes ou caminho do Executavel inválidos", "Por Favor, Verifique!"),
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
