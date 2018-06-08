package Core;

import java.time.LocalDateTime;

public class Enums {
    public enum ConfigPath{
        LOGO_MVNHELPER("/Dados/Img/Logo_MvnHelper.jpg"),
        LOGO_ACC("/Dados/Img/Logo_Acc.png"),
        SCRIPT_COMPILACAO("/Dados/Scripts/compila.bat"),
        SCRIPT_EXECUCAO("/Dados/Scripts/executa.bat"),
        SCRIPT_MANUAL("/Dados/Scripts/manual.bat"),
        SCRIPT_OPCAO_COMMIT("/Dados/Scripts/opcaoCommit.bat"),
        VARIAVEIS_LOCAIS("/Dados/Variaveis/VariaveisLocais.prop"),
        LISTA_AGENCIAS("/Dados/Variaveis/ListaAgencias.prop"),
        LISTA_BANCOS("/Dados/Variaveis/ListaBancos.prop"),
        PATH_FONTES("/Dados/init.cld"),
        DIR_MAP("/Dados/map.prop");

        private String caminho;

        ConfigPath(String caminhoConfig){
            this.caminho = caminhoConfig;
        }

        public String getCaminho(){
            return System.getProperty("user.dir") + caminho;
        }
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
        PRG("Fonte Legado", "*.prg");

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
        MVN_HELPER_VERSION("MVN Helper V 0.3.0", " "),
        ERRO_ARQUIVO_CONFIGURACAO("Erro ao Ler arquivos de configuração", System.getProperty("user.dir")+"\\Dados\\variaveis"),
        EXE_ORIGEM_INVALIDA("Selecione o Executavel manualmente", "A busca automatica é feita a partir do Macrosistema, verifique"),
        ORIGEM_INVALIDA("Campo de Origem Invalido", "Por favor, verifique"),
        ERRO_SALVAR_CAMINHO("Caminho do Executavel inválidos", "A configuração é salva no caminho de execução do sistema na aba EXECUCAO \n Por favor, verifique"),
        DESTINO_INVALIDO("Destino do(s) Arquivo(s) Invalido", "Por Favor, Verifique!"),
        ARQUIVO_INVALIDO("ARQUIVO SELECIONADO INVALIDO", "Teste novamente!"),
        ARQUIVO_NAO_ENCONTRADO("Ops! Arquivo não encontrado", "Tente Novamente"),
        SOBREESCREVER("Arquivo Encontrado no destino", "Deseja sobreescrever ?"),
        OPERACAO_SUCESSO("Operação concluida com Sucesso", "(° ʖ °)"),
        OPERACAO_CANCELADA("Operacao Cancelada", ""),
        OPERACAO_CONCLUIDA("Operacao concluída", "༼ つ ◕_◕ ༽つ"),
        ERRO_COPIAR("Erro ao copiar Arquivo", "Tente novamente!"),
        ORIGEM_DESTINO_IGUAIS("Existem arquivos com Origem e destino iguais", "Verifique, a não ser que isso faça sentido para você, vai saber..."),
        ERRO_CAMINHO_EXECUCAO("Diretório de execucao inválido", "Selecione Novamente!"),
        ERRO_LOGIN_CARACTER_INVALIDO("Caracter não reconhecido", "Verifique o login automático"),
        ERRO_AUTOLOGGER("Ops! Erro no LogIn automático", "Tente novamente mais tarde"),
        EFETUAR_AUTO_LOGIN("Efetuar Login automático ?", ""),
        SEM_ARQUIVOS("Não há Arquivos para efetuar a operação", "Verifique!"),
        EXECUCAO_MANUAL("Script de execucão manual disponivel em:", ConfigPath.SCRIPT_MANUAL.getCaminho()),
        ERRO_TRIZONHO("Foi mal ai, não sei o que deu", "Mas tenta reiniciar a aplicação...");

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

    public enum opcoesExtras {
        DEBUG("-Ddebug"),
        BUILD("sicredi-helper:inc-build"),
        TAG("sicredi-helper:svn-tag");

        public String paramentro;

        opcoesExtras(String parametro){
            this.paramentro = parametro;
        }
    }
}
