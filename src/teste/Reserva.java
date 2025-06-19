package teste;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private static int contador = 1;
    private int codigo;
    private Usuario usuario;
    private Sala sala;
    private LocalDate data;
    private LocalTime inicio;
    private LocalTime fim;

    public Reserva(Usuario usuario, Sala sala, LocalDate data, LocalTime inicio, LocalTime fim) {
        this.codigo = contador++;
        this.usuario = usuario;
        this.sala = sala;
        this.data = data;
        this.inicio = inicio;
        this.fim = fim;
    }

    public int getCodigo() { return codigo; }
    public Usuario getUsuario() { return usuario; }
    public Sala getSala() { return sala; }
    public LocalDate getData() { return data; }
    public LocalTime getInicio() { return inicio; }
    public LocalTime getFim() { return fim; }
}