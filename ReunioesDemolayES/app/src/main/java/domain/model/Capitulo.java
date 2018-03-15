package domain.model;

public class Capitulo {

    private int numeroCapitulo;
    private String nome;
    private String cidade;
    private String endereco;
    private String lojaPatrocinadora;

    public String getLojaPatrocinadora() {
        return lojaPatrocinadora;
    }

    public void setLojaPatrocinadora(String lojaPatrocinadora) {
        this.lojaPatrocinadora = lojaPatrocinadora;
    }

    public int getNumeroCapitulo() {
        return numeroCapitulo;
    }

    public void setNumeroCapitulo(int numeroCapitulo) {
        this.numeroCapitulo = numeroCapitulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
