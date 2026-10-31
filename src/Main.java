import model.RelatorioTurma;
import service.AlunoService;

public class Main {
    public static void main(String[] args) {
        AlunoService service = new AlunoService();

        // Turma vazia
        imprimir(service.gerarRelatorio());

        service.cadastrar("Maria Silva", 20, "2024001", "Informática");
        service.cadastrar("João Souza", 19, "2024002", "Informática");
        service.cadastrar("Carlos Lima", 21, "2024003", "Design");
        service.cadastrar("Ana Rocha", 22, "2024004", "Design");

        service.registrarNota("2024001", 8.5);
        service.registrarNota("2024001", 7.0);
        service.registrarNota("2024001", 6.0);

        service.registrarNota("2024002", 4.0); // só 1 nota: não entra nas contas

        service.registrarNota("2024003", 5.0);
        service.registrarNota("2024003", 6.0);

        service.registrarNota("2024004", 3.0);
        service.registrarNota("2024004", 4.0);

        imprimir(service.gerarRelatorio());
    }

    private static void imprimir(RelatorioTurma r) {
        System.out.println("====== RELATÓRIO GERAL ======");
        System.out.println("Total de alunos:      " + r.totalAlunos());
        System.out.println("Alunos avaliados:     " + r.avaliados());
        System.out.println("Aprovados:            " + r.aprovados());
        System.out.println("Em recuperação:       " + r.emRecuperacao());
        System.out.println("Reprovados:           " + r.reprovados());
        System.out.printf("Média geral da turma: %.2f%n", r.mediaGeral());
        System.out.println("=============================");
    }
}