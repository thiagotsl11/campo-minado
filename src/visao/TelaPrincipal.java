package visao;

import javax.swing.JFrame;

import projetoCampo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16, 30, 50);
		add(new PainelTabuleiro(tabuleiro));
		
		// Definir titulo
		setTitle("CampoMinado");
		// Definir 	
		setVisible(true);
		// Definir tamanho	
		setSize(690, 438);
		// Para centralizar a tela	
		setLocationRelativeTo(null);
		// Para centralizar a tela	
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {

		new TelaPrincipal();
	}

}
