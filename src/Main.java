import exception.MatriculaDuplicadaException;
import model.Aluno;
import service.AlunoService;

public class Main {
    public static void main(String[] args) {
        AlunoService service = new AlunoService();

        service.cadastrar("Maria Silva", 20, "2024001", "Informática");
        service.cadastrar("João Souza", 19, "2024002", "Informática");
        service.cadastrar("Mariana Costa", 21, "2024003", "Design");

        System.out.println("--- Listagem ---");
        for (Aluno a : service.listarTodos()) {
            System.out.println(a);
        }

        System.out.println("--- Busca por nome 'mari' ---");
        for (Aluno a : service.buscarPorNome("mari")) {
            System.out.println(a.getNome());
        }

        System.out.println("--- Busca por matrícula ---");
        System.out.println(service.buscarPorMatricula("2024002"));

        try {
            service.cadastrar("Outra Pessoa", 22, "2024001", "Design");
        } catch (MatriculaDuplicadaException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        service.editar("2024002", "João P. Souza", 20, "Redes");
        System.out.println(service.buscarPorMatricula("2024002"));

        service.remover("2024003");
        System.out.println("Total após remover: " + service.quantidade());

        try {
            service.remover("9999");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}