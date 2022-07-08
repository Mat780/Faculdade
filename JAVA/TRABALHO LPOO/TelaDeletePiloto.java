import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;

public class TelaDeletePiloto implements Tela {
    private JPanel tela;
    private JPanel selecionarPiloto;

    private JMenuItem botaoOrigem;

    DefaultComboBoxModel<Piloto> modelPiloto;
    private JComboBox<Piloto> pilotoSelecionado;

    private ArrayList<Piloto> arrayPilotoLocal;
    private ArrayList<Piloto> Antigo;
    private Piloto[] vetorPilotos;

    private JButton deletar;

    public TelaDeletePiloto(JMenuItem botaoOrigem, ArrayList<Piloto> arrayPiloto) {
        this.botaoOrigem = botaoOrigem;
        this.Antigo = arrayPiloto;
        tela = new JPanel();
        tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

        atualizarTela();

        modelPiloto = new DefaultComboBoxModel<Piloto>(vetorPilotos);
        pilotoSelecionado = new JComboBox<Piloto>(modelPiloto);
        selecionarPiloto = new JPanel();
        selecionarPiloto.setLayout(new FlowLayout());
        selecionarPiloto.add(new JLabel("Piloto a ser deletado"));
        selecionarPiloto.add(pilotoSelecionado);
        tela.add(selecionarPiloto);

        deletar = new JButton("Deletar");
        deletar.addActionListener((e) -> {
            try {

                if (pilotoSelecionado.getSelectedIndex() == 0) {
                    throw new EmptyFieldException();
                }

                Piloto pilotoDelete = (Piloto) pilotoSelecionado.getSelectedItem();

                if (pilotoDelete.getAbordo()) {
                    throw new ImpossibleDeleteException("piloto", "equipe de bordo");
                }

                arrayPiloto.remove(pilotoDelete);

                atualizarTela();
                limparCampos();

                JOptionPane.showMessageDialog(null, "Piloto deletado com sucesso");

            } catch (EmptyFieldException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);

            } catch (ImpossibleDeleteException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
            }
        });
        tela.add(deletar);

    }

    @SuppressWarnings("unchecked")
    private void atualizarTela() {

        arrayPilotoLocal = (ArrayList<Piloto>) Antigo.clone();

        if (arrayPilotoLocal.size() == 0) {
            this.vetorPilotos = new Piloto[1];

        } else {
            this.vetorPilotos = new Piloto[arrayPilotoLocal.size() + 1];
            for (int i = 0; i < arrayPilotoLocal.size(); i++) {
                vetorPilotos[i + 1] = arrayPilotoLocal.get(i);
            }

        }

        try {
            vetorPilotos[0] = new Piloto();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (modelPiloto != null) {
            for (int i = modelPiloto.getSize() - 1; i > 0; i--) {
                modelPiloto.removeElementAt(i);
            }

            for (int i = 1; i < vetorPilotos.length; i++) {
                modelPiloto.addElement(vetorPilotos[i]);
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
        return "Delatando um piloto...";
    }

    @Override
    public void limparCampos() {
        pilotoSelecionado.setSelectedIndex(0);

    }

}
