import model.Aluno;
import model.SituacaoAcademica;
import util.CalculadoraMedia;

public class Main {
    public static void main(String[] args) {
        Aluno aluno = new Aluno("Maria Silva", 20, "2024001", "Informática");
        aluno.registrarNota(8.5);
        aluno.registrarNota(7.0);
        aluno.registrarNota(6.0);

        double media = CalculadoraMedia.calcularMedia(aluno.getNotas());
        SituacaoAcademica situacao = CalculadoraMedia.definirSituacao(media);
        System.out.printf("Média: %.2f | Situação: %s%n", media, situacao.getDescricao());

        // Testando os limites das regras
        System.out.println("7.0 -> " + CalculadoraMedia.definirSituacao(7.0).getDescricao());
        System.out.println("6.9 -> " + CalculadoraMedia.definirSituacao(6.9).getDescricao());
        System.out.println("5.0 -> " + CalculadoraMedia.definirSituacao(5.0).getDescricao());
        System.out.println("4.9 -> " + CalculadoraMedia.definirSituacao(4.9).getDescricao());

        // Aluno sem notas
        try {
            Aluno semNotas = new Aluno("João Souza", 19, "2024002", "Informática");
            CalculadoraMedia.calcularMedia(semNotas.getNotas());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }
    }
}