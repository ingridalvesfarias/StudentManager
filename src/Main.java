import model.Aluno;

public class Main {
    public static void main(String[] args) {
        Aluno aluno = new Aluno("Maria Silva", 20, "2024001", "Informática");
        aluno.registrarNota(8.5);
        aluno.registrarNota(7.0);
        System.out.println(aluno);

        try {
            aluno.registrarNota(11);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            aluno.registrarNota(6.0);
            aluno.registrarNota(9.0);
        } catch (IllegalStateException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        try {
            new Aluno("   ", 20, "2024002", "Informática");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}