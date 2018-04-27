public enum TipoArquivo {
    EXE("Executavel",".exe"),
    POM("Pom", ".xml");

    public String descricao;
    public String extensao;

    TipoArquivo(String descricao, String extensao) {
        this.descricao = descricao;
        this.extensao = extensao;
    }

    public String getDescricao(){
            return descricao;
        }

    public String getExtensao(){
        return extensao;
    }


}
