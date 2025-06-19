package teste;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioManager {
    public void salasMaisReservadas(List<Reserva> reservas) {
        Map<String, Integer> contagem = new HashMap<>();
        for (Reserva r : reservas) {
            contagem.put(r.getSala().getNome(), contagem.getOrDefault(r.getSala().getNome(), 0) + 1);
        }
        contagem.forEach((sala, count) -> System.out.println(sala + ": " + count + " reservas"));
    }

    public void reservasFuturasUsuario(List<Reserva> reservas, String matricula) {
        for (Reserva r : reservas) {
            if (r.getUsuario().getMatricula().equals(matricula) && r.getData().isAfter(java.time.LocalDate.now())) {
                System.out.println("Reserva " + r.getCodigo() + " - Sala: " + r.getSala().getNome() + ", Data: " + r.getData());
            }
        }
    }
}