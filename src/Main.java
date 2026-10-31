import model.Desempenho;
import service.AlunoService;

public class Main {
    public static void main(String[] args) {
        AlunoService service = new AlunoService();

        service.cadastrar("Maria Silva", 20, "2024001", "Informática");
        service.cadastrar("João Souza", 19, "2024002", "Informática");
        service.cadastrar("Carlos Lima", 21, "2024003", "Design");

        service.registrarNota("2024001", 8.5);
        service.registrarNota("2024001", 7.0);
        service.registrarNota("2024001", 6.0);

        service.registrarNota("2024002", 4.0);

        service.registrarNota("2024003", 5.0);
        service.registrarNota("2024003", 6.0);

        for (String matricula : new String[]{"2024001", "2024003"}) {
            Desempenho d = service.consultarDesempenho(matricula);
            System.out.printf("%s | Média: %.2f | %s%n",
                    d.aluno().getNome(), d.media(), d.situacao().getDescricao());
        }

        try {
            service.consultarDesempenho("2024002"); // só 1 nota
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            service.registrarNota("2024001", 7.5); // 4ª nota
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            service.registrarNota("2024003", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            service.registrarNota("9999", 8);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}