package visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import projetoCampo.Campo;
import projetoCampo.CampoEvento;
import projetoCampo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener {
	// esses metodos vao definir as cores do evento
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);

	private Campo campo;

	public BotaoCampo(Campo campo) {
		this.campo = campo;
		// Muda um pouco a cor do fundo da tela do jogo
		setBackground(BG_PADRAO);
		// Da um efeito na borda do jogo
		setBorder(BorderFactory.createBevelBorder(0));

		setOpaque(true);
		// faz com que os clicks no jogo deem o retorno
		addMouseListener(this);
		// Registar a minha classe como interessada em escutar os eventos passado abaixo
		campo.registrarObservador(this);

	}

	// Esse metodo a partir do evento ocorrido seja ele
	// abrir fechar explodir ele vai fazer um botao diferente
	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch (evento) { // switch é uma seleção multipla
		case ABRIR:
			aplicarEstiloAbrir();
			break; // break faz com que ele execute somente o abrir e nao todos os outros eventos
		case MARCAR:
			aplicarEstiloMarcar();
			break; // break faz com que ele execute somente o marcar e nao todos os outros eventos
		case EXPLODIR:
			aplicarEstiloExplodir();
			break; // break faz com que ele execute somente o explodir e nao todos os outros
					// eventos
		default:
			aplicarEstiloPadrao();
		}

	}

	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.WHITE);
		setText("X");

	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.BLACK);
		setText("M");

	}

	private void aplicarEstiloAbrir() {

		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		if (campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		setBackground(BG_PADRAO);

		// esse switch faz com que de acordo com nosso click ele mostre
		// o numero azul,verde ou vermelho, conforme o nivel de perigo
		// da vizinhança
		switch (campo.minasNaVizinhanca()) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		// Esse metodo faz com que a logica acima funcione
		String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
		setText(valor);
	}

	// Essa logica faz o jogo abrir o campo com click do mouse esquerdo
	// e marcar o campo com click direito do mouse
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarMarcacao();
		}
	}

	// Esse metodos de baixo nao serao necessarios no programa mas
	// precisam serem colocados
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
