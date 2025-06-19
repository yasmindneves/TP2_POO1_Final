package teste;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SGSRSistema {
    private static List<Sala> salas = new ArrayList<>();
    private static List<Usuario> usuarios = new ArrayList<>();
    private static ReservaManager reservaManager = new ReservaManager();
    private static RelatorioManager relatorioManager = new RelatorioManager();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("===== Sistema de Gestão de Salas de Reunião =====");
            System.out.println("1. Cadastrar Sala");
            System.out.println("2. Cadastrar Usuário");
            System.out.println("3. Reservar Sala");
            System.out.println("4. Cancelar Reserva");
            System.out.println("5. Exibir Relatórios");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> cadastrarSala(sc);
                case 2 -> cadastrarUsuario(sc);
                case 3 -> reservarSala(sc);
                case 4 -> cancelarReserva(sc);
                case 5 -> exibirRelatorios(sc);
            }
        } while (opcao != 6);
        sc.close();
    }

    private static void cadastrarSala(Scanner sc) {
        System.out.println("--- Cadastro de Sala ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Capacidade: ");
        int capacidade = sc.nextInt();
        sc.nextLine();
        System.out.print("Equipamentos (separados por vírgula): ");
        List<String> equipamentos = Arrays.asList(sc.nextLine().split(","));
        System.out.print("Status (Ativa/Inativa): ");
        boolean ativa = sc.nextLine().equalsIgnoreCase("Ativa");
        salas.add(new Sala(nome, capacidade, equipamentos, ativa));
        System.out.println("[Sala cadastrada com sucesso!]");
    }

    private static void cadastrarUsuario(Scanner sc) {
        System.out.println("--- Cadastro de Usuário ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Tipo (Aluno/Professor/Técnico): ");
        String tipo = sc.nextLine();
        switch (tipo.toLowerCase()) {
            case "aluno" -> usuarios.add(new Aluno(nome, matricula));
            case "professor" -> usuarios.add(new Professor(nome, matricula));
            case "técnico" -> usuarios.add(new Tecnico(nome, matricula));
            default -> System.out.println("[Tipo inválido!]");
        }
        System.out.println("[Usuário cadastrado com sucesso!]");
    }

    private static void reservarSala(Scanner sc) {
        System.out.println("--- Reservar Sala ---");
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        Usuario usuario = usuarios.stream().filter(u -> u.getMatricula().equals(matricula)).findFirst().orElse(null);
        if (usuario == null) {
            System.out.println("[Usuário não encontrado!]");
            return;
        }
        System.out.print("Sala: ");
        String nomeSala = sc.nextLine();
        Sala sala = salas.stream().filter(s -> s.getNome().equals(nomeSala)).findFirst().orElse(null);
        if (sala == null || !sala.isAtiva()) {
            System.out.println("[Sala inválida ou inativa!]");
            return;
        }
        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(sc.nextLine());
        System.out.print("Início (HH:MM): ");
        LocalTime inicio = LocalTime.parse(sc.nextLine());
        System.out.print("Fim (HH:MM): ");
        LocalTime fim = LocalTime.parse(sc.nextLine());
        Reserva nova = new Reserva(usuario, sala, data, inicio, fim);
        if (reservaManager.adicionarReserva(nova, usuario)) {
            System.out.println("[Reserva realizada com sucesso!]");
        } else {
            System.out.println("[Erro: Conflito de horário ou limite de reservas excedido!]");
        }
    }

    private static void cancelarReserva(Scanner sc) {
        System.out.println("--- Cancelar Reserva ---");
        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();
        System.out.print("Código da reserva: ");
        int codigo = sc.nextInt();
        sc.nextLine();
        if (reservaManager.cancelarReserva(codigo, matricula)) {
            System.out.println("[Reserva cancelada com sucesso!]");
        } else {
            System.out.println("[Erro: Reserva não encontrada ou não pode ser cancelada!]");
        }
    }

    private static void exibirRelatorios(Scanner sc) {
        System.out.println("--- Relatórios ---");
        System.out.println("1. Salas mais reservadas");
        System.out.println("2. Reservas futuras de um usuário");
        int op = sc.nextInt();
        sc.nextLine();
        if (op == 1) {
            relatorioManager.salasMaisReservadas(reservaManager.getReservas());
        } else if (op == 2) {
            System.out.print("Informe a matrícula do usuário: ");
            String matricula = sc.nextLine();
            relatorioManager.reservasFuturasUsuario(reservaManager.getReservas(), matricula);
        }
    }
}