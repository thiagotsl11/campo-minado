package projetoCampo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

	private final int linhas;
	private final int colunas;
	private final int minas;

	// Array do campo Campo
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

	// Construtor, Melhor lugar para inicar o jogo é no contrutor, é onde voce
	// recebe os parametros
	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		// Gerar 3 metodos para geração do tabuleiro, sendo que gerar campo e associar
		// sao executados uma unica vez e
		// sortear minas sempre que o jogo reinicializa
		gerarCampo();
		associarVizinhos();
		sortearMinas();
	}

	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}

	public void registrarObservador(Consumer<ResultadoEvento> observador) {
		observadores.add(observador);
	}

	public void notificarObservadores(boolean resultado) {
		observadores.stream().forEach(o -> o.accept(new ResultadoEvento(resultado)));
	}

	// Procurar na list criada na linha 14 e abrir o campo com os parametros que
	// seram passados
	public void abri(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.abrir());
	}

	// Procurar na list criada na linha 14 e marcar o campo com os parametros que
	// seram passados
	public void marcar(int linha, int coluna) {
		campos.parallelStream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacao());
	}

	// Metodo que vai gerar o Tabuleiro
	private void gerarCampo() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.registrarObservador(this);
				campos.add(campo);
			}
		}
	}

	// Metodo que vai associar os vizinhos
	private void associarVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);
			}
		}

	}

	// Metodo que vai sortear as minas toda vez que o jogo recomeçar Metodo está
	// usando uma Stream para ver quantas minas tem o jogo e usando do while para
	// rodar adicionar uma mina enquanto minasArmadas for < que a quantidado
	// requerida
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		} while (minasArmadas < minas);
	}

	// Metodo que finaliza o jogo
	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	// Metodo para reiniciar o jogo e ja sortear as minas
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if (evento == CampoEvento.EXPLODIR) {
			System.out.println("Perdeu");
			notificarObservadores(false);
		} else if (objetivoAlcancado()) {
			notificarObservadores(true);
		}
	}

	public void mostrarMinas() {
		campos.stream().filter(c -> c.isMinado()).filter(c -> !c.isMarado()).forEach(c -> c.setAberto(true));

	}
}