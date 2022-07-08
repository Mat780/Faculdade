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

public class TelaDeletePassageiro implements Tela {
    private JPanel tela;

    private JPanel selecionarPassageiro;

    private JMenuItem botaoOrigem;

    private JComboBox<Passageiro> passageiro;

    private DefaultComboBoxModel<Passageiro> modelPassageiro;

    private ArrayList<Passageiro> arrayPassageiroLocal;
    private Passageiro[] vetorPassageiro;

    private JButton deletar;

    public TelaDeletePassageiro(JMenuItem botaoOrigem, ArrayList<Passageiro> arrayPassageiro) {
        this.botaoOrigem = botaoOrigem;
        this.arrayPassageiroLocal = arrayPassageiro;

        atualizarTela();

        tela = new JPanel();
        tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

        modelPassageiro = new DefaultComboBoxModel<Passageiro>(vetorPassageiro);
        passageiro = new JComboBox<Passageiro>(modelPassageiro);
        selecionarPassageiro = new JPanel();
        selecionarPassageiro.setLayout(new FlowLayout());
        selecionarPassageiro.add(new JLabel("Passageiro a ser deletado: "));
        selecionarPassageiro.add(passageiro);
        tela.add(selecionarPassageiro);

        deletar = new JButton("Deletar");
        deletar.addActionListener((e) -> {
            try {
                if (passageiro.getSelectedIndex() == 0) {
                    throw new EmptyFieldException();
                }

                Passageiro passageiroDeletar = (Passageiro) passageiro.getSelectedItem();

                passageiroDeletar.getViagem().removePassageiro(passageiroDeletar);
                arrayPassageiro.remove(passageiroDeletar);

                atualizarTela();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Passageiro deletado com sucesso");

            } catch (EmptyFieldException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
            }

        });
        tela.add(deletar);

    }

    private void atualizarTela() {

        if (arrayPassageiroLocal.size() == 0) {
            this.vetorPassageiro = new Passageiro[1];

        } else {
            this.vetorPassageiro = new Passageiro[arrayPassageiroLocal.size() + 1];
            for (int i = 0; i < arrayPassageiroLocal.size(); i++) {
                vetorPassageiro[i + 1] = arrayPassageiroLocal.get(i);
            }

        }

        try {
            vetorPassageiro[0] = new Passageiro();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (modelPassageiro != null) {
            for (int i = modelPassageiro.getSize() - 1; i > 0; i--) {
                modelPassageiro.removeElementAt(i);
            }

            for (int i = 1; i < vetorPassageiro.length; i++) {
                modelPassageiro.addElement(vetorPassageiro[i]);
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
        return "Deletando um passageiro...";
    }

    @Override
    public void limparCampos() {
        passageiro.setSelectedIndex(0);
    }

}
