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

public class TelaUpdatePassageiro implements Tela, Update {
	private JPanel tela;

	private JPanel selecionarPassageiro;
	private JPanel cadastrarNome;
	private JPanel cadastrarCpf;
	private JPanel cadastrarViagem;
	private JPanel cadastrarPassaporte;

	private JMenuItem botaoOrigem;

	private JComboBox<Passageiro> passageiro;
	private JTextField nome;
	private JTextField cpf;
	private JComboBox<Viagem> viagem;
	private JComboBox<String> passaporte;

	private DefaultComboBoxModel<Passageiro> modelPassageiro;
	private DefaultComboBoxModel<Viagem> modelViagem;

	private ArrayList<Passageiro> arrayPassageiroLocal;
	private Passageiro[] vetorPassageiro;
	private ArrayList<Viagem> arrayViagemLocal;
	private Viagem[] vetorViagem;

	private Passageiro passageiroAlterando;
	private Passageiro clonePassageiro;

	private JButton salvar;

	public TelaUpdatePassageiro(JMenuItem botaoOrigem, ArrayList<Passageiro> arrayPassageiro,
			ArrayList<Viagem> arrayViagem) {
		this.botaoOrigem = botaoOrigem;
		this.arrayPassageiroLocal = arrayPassageiro;
		this.arrayViagemLocal = arrayViagem;

		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		modelPassageiro = new DefaultComboBoxModel<Passageiro>(vetorPassageiro);
		passageiro = new JComboBox<Passageiro>(modelPassageiro);
		selecionarPassageiro = new JPanel();
		selecionarPassageiro.setLayout(new FlowLayout());
		selecionarPassageiro.add(new JLabel("Passageiro a atualizar: "));
		selecionarPassageiro.add(passageiro);
		tela.add(selecionarPassageiro);

		nome = new JTextField(20);
		cadastrarNome = new JPanel();
		cadastrarNome.setLayout(new FlowLayout());
		cadastrarNome.add(new JLabel("Nome: "));
		cadastrarNome.add(nome);
		tela.add(cadastrarNome);

		cpf = new JTextField(20);
		cadastrarCpf = new JPanel();
		cadastrarCpf.setLayout(new FlowLayout());
		cadastrarCpf.add(new JLabel("CPF: "));
		cadastrarCpf.add(cpf);
		tela.add(cadastrarCpf);

		modelViagem = new DefaultComboBoxModel<Viagem>(vetorViagem);
		viagem = new JComboBox<Viagem>(modelViagem);
		cadastrarViagem = new JPanel();
		cadastrarViagem.setLayout(new FlowLayout());
		cadastrarViagem.add(new JLabel("Viagem: "));
		cadastrarViagem.add(viagem);
		tela.add(cadastrarViagem);

		String[] vetorAux = { "Selecionar", "Sim", "Não" };
		passaporte = new JComboBox<String>(vetorAux);
		cadastrarPassaporte = new JPanel();
		cadastrarPassaporte.setLayout(new FlowLayout());
		cadastrarPassaporte.add(new JLabel("Passaporte está valido: "));
		cadastrarPassaporte.add(passaporte);
		tela.add(cadastrarPassaporte);

		
		passageiro.addActionListener((e) -> {
			if (passageiro.getSelectedIndex() != 0) {
				Passageiro castPassageiro = (Passageiro) passageiro.getSelectedItem();
				nome.setText(castPassageiro.getNome());
				cpf.setText(castPassageiro.getCpf());
				viagem.setSelectedItem(castPassageiro.getViagem());
				passaporte.setSelectedIndex(castPassageiro.getPassaporteAtualizado() ? 1 : 2);

			} else {
				nome.setText("");
				cpf.setText("");
				viagem.setSelectedIndex(0);
				passaporte.setSelectedIndex(0);
			}
		});

		salvar = new JButton("Atualizar");
		salvar.addActionListener((e) -> {
			try {
				if (passaporte.getSelectedIndex() == 0 ||
						nome.getText().equals("") ||
						cpf.getText().equals("") ||
						viagem.getSelectedIndex() == 0 ||
						passaporte.getSelectedIndex() == 0) {
					throw new EmptyFieldException();
				}

				Viagem castViagem = (Viagem) viagem.getSelectedItem();
				passageiroAlterando = (Passageiro) passageiro.getSelectedItem();

				arrayPassageiro.forEach((Passageiro obj) -> {
					if (obj.getCpf().equals(cpf.getText()) &&
							passageiroAlterando.equals(obj) == false)
						throw new EqualObjectException("passageiro");
				});

				clonePassageiro = passageiroAlterando.clonar();

				passageiroAlterando.getViagem().removePassageiro(passageiroAlterando);

				passageiroAlterando.setNome(nome.getText());
				passageiroAlterando.setCpf(cpf.getText());
				passageiroAlterando.setPassaporteAtualizado(passaporte.getSelectedIndex() == 1 ? true : false);
				passageiroAlterando.setViagem(castViagem);

				passageiroAlterando.getViagem().addPassageiro(passageiroAlterando);

				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Passageiro salvo com sucesso");
			} catch (OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(clonePassageiro, passageiroAlterando);
			} catch (InvalidCpfException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(clonePassageiro, passageiroAlterando);
			} catch (EqualObjectException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			} catch (EmptyFieldException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}

		});
		tela.add(salvar);

	}

	@Override
	public void setInfoBack(Object Antigo, Object Atual) {
		try {
			Passageiro passageiroAntigo = (Passageiro) Antigo;
			Passageiro passageiroAtual = (Passageiro) Atual;

			passageiroAtual.setNome(passageiroAntigo.getNome());
			passageiroAtual.setCpf(passageiroAntigo.getCpf());
			passageiroAtual.setPassaporteAtualizado(passageiroAntigo.getPassaporteAtualizado());
			passageiroAtual.setViagem(passageiroAntigo.getViagem());
			passageiroAtual.getViagem().addPassageiro(passageiroAtual);

		} catch (Exception e) {
			e.printStackTrace();
		}
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

		if (arrayViagemLocal.size() == 0) {
			this.vetorViagem = new Viagem[1];

		} else {
			this.vetorViagem = new Viagem[arrayViagemLocal.size() + 1];
			for (int i = 0; i < arrayViagemLocal.size(); i++) {
				vetorViagem[i + 1] = arrayViagemLocal.get(i);
			}

		}

		try {
			vetorViagem[0] = new Viagem();
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
		return "Atualizando um passageiro...";
	}

	@Override
	public void limparCampos() {
		passageiro.setSelectedIndex(0);
		nome.setText("");
		cpf.setText("");
		viagem.setSelectedIndex(0);
		passaporte.setSelectedIndex(0);
	}

}
