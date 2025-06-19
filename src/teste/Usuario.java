package teste;
public abstract class Usuario {
    protected String nome;
    protected String matricula;
    protected String tipo;

    public Usuario(String nome, String matricula, String tipo) {
        this.nome = nome;
        this.matricula = matricula;
        this.tipo = tipo;
    }

    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
}