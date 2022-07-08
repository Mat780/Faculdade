import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TelaDeleteViagem implements Tela {
    private JPanel tela;

    private JPanel selecionarViagem;

    private JMenuItem botaoOrigem;

    private JComboBox<Viagem> viagem;

    private DefaultComboBoxModel<Viagem> modelViagem;

    private ArrayList<Viagem> arrayViagemLocal;
    private Viagem[] vetorViagem;

    private JButton deletar;

    public TelaDeleteViagem(JMenuItem botaoOrigem, ArrayList<Viagem> arrayViagem, ArrayList<Passageiro> arrayPassageiro) {
        this.botaoOrigem = botaoOrigem;
        this.arrayViagemLocal = arrayViagem;

        atualizarTela();

        tela = new JPanel();
        tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

        modelViagem = new DefaultComboBoxModel<Viagem>(vetorViagem);
        viagem = new JComboBox<Viagem>(modelViagem);
        selecionarViagem = new JPanel();
        selecionarViagem.setLayout(new FlowLayout());
        selecionarViagem.add(new JLabel("Viagem: "));
        selecionarViagem.add(viagem);
        tela.add(selecionarViagem);

        deletar = new JButton("Deletar");
        deletar.addActionListener((e) -> {
            try {
                
				Viagem castViagem = (Viagem) viagem.getSelectedItem();

				ArrayList<Passageiro> arrayP = castViagem.getListaPassageiro();

				while(arrayP.size() > 0) {
					arrayPassageiro.remove(arrayP.get(0));
					arrayP.remove(0);
				}

				castViagem.getAviao().removeViagem(castViagem);
				
				ArrayList<Aeroporto> arrayAeroporto = castViagem.getListaAeroportos();

				arrayAeroporto.get(0).removeViagem(castViagem);
				arrayAeroporto.get(1).removeViagem(castViagem);

				arrayViagem.remove(castViagem);

                atualizarTela();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Viagem deletada com sucesso");
            }
            catch (EmptyFieldException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
            }

        });
        tela.add(deletar);

    }

    private void atualizarTela() {

        if (arrayViagemLocal.size() == 0) {
            this.vetorViagem = new Viagem[1];

        } else {
            this.vetorViagem = new Viagem[arrayViagemLocal.size() + 1];
            for (int i = 0; i < arrayViagemLocal.size(); i++) {
                vetorViagem[i + 1] = arrayViagemLocal.get(i);
            }

        }
        vetorViagem[0] = new Viagem();

		if (modelViagem != null) {
            for (int i = modelViagem.getSize() - 1; i > 0; i--) {
                modelViagem.removeElementAt(i);
            }

            for (int i = 1; i < vetorViagem.length; i++) {
                modelViagem.addElement(vetorViagem[i]);
            }
        }
    }

    @Override
    public JPanel getTela() {
        atualizarTela();

        return tela;
    }

    @Override
    public JMenuItem getSource() {
        return botaoOrigem;
    }

    @Override
    public String getMensagemStatus() {
        return "Deletando uma viagem...";
    }

    @Override
    public void limparCampos() {
        viagem.setSelectedIndex(0);
    }

}
