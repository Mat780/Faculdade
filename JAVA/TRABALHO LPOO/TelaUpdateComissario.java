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

public class TelaUpdateComissario implements Tela, Update {
	private JPanel tela;
	private JPanel selecionarComissario;
	private JPanel cadastrarNome;
	private JPanel cadastrarCpf;

	private DefaultComboBoxModel<Comissario> model;

	private JMenuItem botaoOrigem;

	private JTextField nome;
	private JTextField cpf;

	private JComboBox<Comissario> comissarioSelecionado;

	private ArrayList<Comissario> arrayComissarioLocal;
	private ArrayList<Comissario> arrayComissarioAntigo;
	private Comissario[] vetorComissario;

	private Comissario comissarioAlterando;
	private Comissario cloneComissario;

	private JButton salvar;

	public TelaUpdateComissario(JMenuItem botaoOrigem, ArrayList<Comissario> arrayComissario) {
		this.botaoOrigem = botaoOrigem;
		this.arrayComissarioAntigo = arrayComissario;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		atualizarTela();

		model = new DefaultComboBoxModel<Comissario>(vetorComissario);
		comissarioSelecionado = new JComboBox<Comissario>(model);
		selecionarComissario = new JPanel();
		selecionarComissario.setLayout(new FlowLayout());
		selecionarComissario.add(new JLabel("Comissario a ser atualizado"));
		selecionarComissario.add(comissarioSelecionado);
		tela.add(selecionarComissario);

		nome = new JTextField(20);
		cadastrarNome = new JPanel();
		cadastrarNome.setLayout(new FlowLayout());
		cadastrarNome.add(new JLabel("Nome:"));
		cadastrarNome.add(nome);
		tela.add(cadastrarNome);

		cpf = new JTextField(20);
		cadastrarCpf = new JPanel();
		cadastrarCpf.setLayout(new FlowLayout());
		cadastrarCpf.add(new JLabel("CPF:"));
		cadastrarCpf.add(cpf);
		tela.add(cadastrarCpf);

		comissarioSelecionado.addActionListener((e) -> {
			if (comissarioSelecionado.getSelectedIndex() != 0) {
				Comissario cloneComissario = (Comissario) comissarioSelecionado.getSelectedItem();

				nome.setText(cloneComissario.getNome());
				cpf.setText(cloneComissario.getCpf());

			} else {
				nome.setText("");
				cpf.setText("");
			}
		});

		salvar = new JButton("Atualizar");
		salvar.addActionListener((e) -> {
			try {
				if (nome.getText().equals("") || cpf.getText().equals("")
						|| comissarioSelecionado.getSelectedIndex() == 0) {
					throw new EmptyFieldException();
				}

				comissarioAlterando = (Comissario) comissarioSelecionado.getSelectedItem();

				arrayComissario.forEach((Comissario obj) -> {
					if (obj.getCpf().equals(cpf.getText()) && obj.equals(comissarioAlterando) == false)
						throw new EqualObjectException("comissario");
				});

				cloneComissario = comissarioAlterando.clonar();

				comissarioAlterando.setNome(nome.getText());
				comissarioAlterando.setCpf(cpf.getText());

				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Comissario atualizado com sucesso");
			} catch (InvalidTaskException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
				setInfoBack(cloneComissario, comissarioAlterando);
			} catch (OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(cloneComissario, comissarioAlterando);
			} catch (InvalidCpfException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(cloneComissario, comissarioAlterando);
			} catch (EmptyFieldException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}

		});
		tela.add(salvar);

	}

	@Override
	public void setInfoBack(Object Antigo, Object Atual) {
		try {
			Comissario comissarioAntigo = (Comissario) Antigo;
			Comissario comissarioAtual = (Comissario) Atual;

			comissarioAtual.setNome(comissarioAntigo.getNome());
			comissarioAtual.setCpf(comissarioAntigo.getCpf());
			comissarioAtual.setTarefa(comissarioAntigo.getTarefa());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void atualizarTela() {

		arrayComissarioLocal = (ArrayList<Comissario>) arrayComissarioAntigo.clone();

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

		if (model != null) {
			for (int i = model.getSize() - 1; i > 0; i--) {
				model.removeElementAt(i);
			}

			for (int i = 1; i < vetorComissario.length; i++) {
				model.addElement(vetorComissario[i]);
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
		return "Atualizando um comissario...";
	}

	@Override
	public void limparCampos() {
		comissarioSelecionado.setSelectedIndex(0);
		nome.setText("");
		cpf.setText("");
	}

}
