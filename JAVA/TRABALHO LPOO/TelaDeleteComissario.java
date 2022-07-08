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

public class TelaDeleteComissario implements Tela {
    private JPanel tela;
    private JPanel selecionarComissario;

    private DefaultComboBoxModel<Comissario> modelComissario;

    private JMenuItem botaoOrigem;

    private JComboBox<Comissario> comissarioSelecionado;

    private ArrayList<Comissario> arrayComissarioLocal;
    private Comissario[] vetorComissario;

    private JButton deletar;

    public TelaDeleteComissario(JMenuItem botaoOrigem, ArrayList<Comissario> arrayComissario) {
        this.botaoOrigem = botaoOrigem;
        this.arrayComissarioLocal = arrayComissario;
        tela = new JPanel();
        tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

        atualizarTela();

        modelComissario = new DefaultComboBoxModel<Comissario>(vetorComissario);
        comissarioSelecionado = new JComboBox<Comissario>(modelComissario);
        selecionarComissario = new JPanel();
        selecionarComissario.setLayout(new FlowLayout());
        selecionarComissario.add(new JLabel("Comissario a ser deletado"));
        selecionarComissario.add(comissarioSelecionado);
        tela.add(selecionarComissario);

        deletar = new JButton("Deletar");
        deletar.addActionListener((e) -> {
            try {
                if (comissarioSelecionado.getSelectedIndex() == 0) {
                    throw new EmptyFieldException();
                }

                Comissario comissarioDeletar = (Comissario) comissarioSelecionado.getSelectedItem();

                if (comissarioDeletar.getAbordo()) {
                    throw new ImpossibleDeleteException("comissario", "equipe de bordo");
                }

                arrayComissario.remove(comissarioDeletar);

                atualizarTela();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Comissario deletado com sucesso");

            } catch (ImpossibleDeleteException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);

            } catch (EmptyFieldException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
            }

        });
        tela.add(deletar);

    }

    private void atualizarTela() {

        if (arrayComissarioLocal.size() == 0) {
            this.vetorComissario = new Comissario[1];

        } else {
            this.vetorComissario = new Comissario[arrayComissarioLocal.size() + 1];
            for (int i = 0; i < arrayComissarioLocal.size(); i++) {
                vetorComissario[i + 1] = arrayComissarioLocal.get(i);
            }

        }

        try {
            vetorComissario[0] = new Comissario();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (modelComissario != null) {
            for (int i = modelComissario.getSize() - 1; i > 0; i--) {
                modelComissario.removeElementAt(i);
            }

            for (int i = 1; i < vetorComissario.length; i++) {
                modelComissario.addElement(vetorComissario[i]);
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
        return "Deletando um comissario...";
    }

    @Override
    public void limparCampos() {
        comissarioSelecionado.setSelectedIndex(0);
    }

}
