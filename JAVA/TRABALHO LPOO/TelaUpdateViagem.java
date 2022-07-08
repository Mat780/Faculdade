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
import javax.swing.JTextField;

public class TelaUpdateViagem implements Tela, Update {
    private JPanel tela;

    private JPanel selecionarViagem;
    private JPanel cadastrarAviao;
    private JPanel cadastrarValorPassagem;

    private JMenuItem botaoOrigem;

    private JComboBox<Viagem> viagem;
    private JComboBox<Aviao> aviao;
    private JTextField valorPassagem;

    private DefaultComboBoxModel<Viagem> modelViagem;
    private DefaultComboBoxModel<Aviao> modelAviao;

    private ArrayList<Viagem> arrayViagemLocal;
    private Viagem[] vetorViagem;

    private ArrayList<Aviao> arrayAviaoLocal;
    private Aviao[] vetorAviao;

    private Viagem viagemAlterando;
    private Viagem cloneViagem;

    private JButton salvar;

    public TelaUpdateViagem(JMenuItem botaoOrigem, ArrayList<Viagem> arrayViagem, ArrayList<Aviao> arrayAviao) {
        this.botaoOrigem = botaoOrigem;
        this.arrayViagemLocal = arrayViagem;
        this.arrayAviaoLocal = arrayAviao;

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

        modelAviao = new DefaultComboBoxModel<Aviao>(vetorAviao);
        aviao = new JComboBox<Aviao>(modelAviao);
        cadastrarAviao = new JPanel();
        cadastrarAviao.setLayout(new FlowLayout());
        cadastrarAviao.add(new JLabel("Avião: "));
        cadastrarAviao.add(aviao);
        tela.add(cadastrarAviao);

        valorPassagem = new JTextField(20);
        cadastrarValorPassagem = new JPanel();
        cadastrarValorPassagem.setLayout(new FlowLayout());
        cadastrarValorPassagem.add(new JLabel("Valor da passagem: "));
        cadastrarValorPassagem.add(valorPassagem);
        tela.add(cadastrarValorPassagem);

        viagem.addActionListener((e) -> {
            if (viagem.getSelectedIndex() != 0) {
                Viagem castViagem = (Viagem) viagem.getSelectedItem();
                aviao.setSelectedItem(castViagem.getAviao());
                valorPassagem.setText(castViagem.getValorViagem() + "");
            } else {
                aviao.setSelectedIndex(0);
                valorPassagem.setText("");
            }
        });

        salvar = new JButton("Atualizar");
        salvar.addActionListener((e) -> {
            try {
                if (aviao.getSelectedIndex() == 0 ||
                        valorPassagem.getText().equals(""))
                    throw new EmptyFieldException();

                Aviao castAviao = (Aviao) aviao.getSelectedItem();
                viagemAlterando = (Viagem) viagem.getSelectedItem();
                cloneViagem = viagemAlterando.clonar();

                if (viagemAlterando.getIsInternational() && castAviao.getVoarInternacional() == false) {
                    throw new ImpossibleInternationalTripException();
                }

                viagemAlterando.getAviao().removeViagem(viagemAlterando);

                viagemAlterando.setValorViagem(Double.parseDouble(valorPassagem.getText()));
                viagemAlterando.setAviao(castAviao);

                viagemAlterando.getAviao().addViagem(viagemAlterando);

                atualizarTela();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Viagem salva com sucesso");
            } catch (OnlyPositiveNumbersException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
                setInfoBack(cloneViagem, viagemAlterando);
            } catch (SameAirportException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
            } catch (EmptyFieldException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
            } catch (ImpossibleInternationalTripException event) {
                JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
                setInfoBack(cloneViagem, viagemAlterando);
            } catch (NumberFormatException event) {
                JOptionPane.showMessageDialog(null,
                        "O campo de valor da passagem precisa ser composto de numeros apenas", "Aviso", 2);
                setInfoBack(cloneViagem, viagemAlterando);
            }

        });
        tela.add(salvar);

    }

    @Override
    public void setInfoBack(Object Antigo, Object Atual) {
        try {
            Viagem viagemAtual = (Viagem) Atual;
            Viagem viagemAntigo = (Viagem) Antigo;

            viagemAtual.setValorViagem(viagemAntigo.getValorViagem());
            viagemAtual.setAviao(viagemAntigo.getAviao());
            viagemAtual.getAviao().addViagem(viagemAtual);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

        if (arrayAviaoLocal.size() == 0) {
            this.vetorAviao = new Aviao[1];

        } else {
            this.vetorAviao = new Aviao[arrayAviaoLocal.size() + 1];
            for (int i = 0; i < arrayAviaoLocal.size(); i++) {
                vetorAviao[i + 1] = arrayAviaoLocal.get(i);
            }

        }
        vetorAviao[0] = new Aviao();

        if (modelViagem != null) {
            for (int i = modelViagem.getSize() - 1; i > 0; i--) {
                modelViagem.removeElementAt(i);
            }

            for (int i = 1; i < vetorViagem.length; i++) {
                modelViagem.addElement(vetorViagem[i]);
            }
        }

        if (modelAviao != null) {
            for (int i = modelAviao.getSize() - 1; i > 0; i--) {
                modelAviao.removeElementAt(i);
            }

            for (int i = 1; i < vetorAviao.length; i++) {
                modelAviao.addElement(vetorAviao[i]);
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
        return "Alterando uma viagem...";
    }

    @Override
    public void limparCampos() {
        aviao.setSelectedIndex(0);
        viagem.setSelectedIndex(0);
        valorPassagem.setText("");
    }

}
