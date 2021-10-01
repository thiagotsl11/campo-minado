package visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import projetoCampo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {

	public PainelTabuleiro(Tabuleiro tabuleiro) {

		// Como vou organizar os componetes visuais na minha tela que no caso sera em
		// linhas e colunas
		setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));

		tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));

		tabuleiro.registrarObservador(e -> {

			SwingUtilities.invokeLater(() -> {
				if (e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Você ganhou, parabéns !!");
				} else {
					JOptionPane.showMessageDialog(this, "Você perdeu, tente novamente !!");
				}
				tabuleiro.reiniciar();
			});
		});

	}
}
