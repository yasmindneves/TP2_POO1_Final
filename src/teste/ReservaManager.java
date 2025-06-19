package teste;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaManager {
    private List<Reserva> reservas = new ArrayList<>();

    public boolean adicionarReserva(Reserva nova, Usuario usuario) {
        // Verificar limite de 2 reservas futuras por usuário
        long futuras = reservas.stream()
            .filter(r -> r.getUsuario().getMatricula().equals(usuario.getMatricula()) 
                      && r.getData().isAfter(LocalDate.now()))
            .count();
        if (futuras >= 2) {
            System.out.println("[Erro: Limite de 2 reservas futuras atingido!]");
            return false;
        }

        // Verificar conflitos de horário
        for (Reserva r : reservas) {
            if (r.getSala().getNome().equals(nova.getSala().getNome()) && r.getData().equals(nova.getData())) {
                boolean conflito = !(nova.getFim().isBefore(r.getInicio()) || nova.getInicio().isAfter(r.getFim()));
                if (conflito) {
                    System.out.println("[Erro: Conflito de horário!]");
                    return false;
                }
            }
        }

        reservas.add(nova);
        return true;
    }

    public boolean cancelarReserva(int codigo, String matricula) {
        return reservas.removeIf(r ->
            r.getCodigo() == codigo &&
            r.getUsuario().getMatricula().equals(matricula) &&
            r.getData().isAfter(LocalDate.now()) // Só pode cancelar reservas futuras
        );
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}
