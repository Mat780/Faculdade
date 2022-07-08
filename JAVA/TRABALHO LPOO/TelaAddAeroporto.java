import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaAddAeroporto implements Tela {
	private JPanel tela;
	private JPanel cadastrarNome;
	private JPanel cadastrarPais;
	private JPanel cadastrarInternacional;

	private JMenuItem botaoOrigem;

	private JTextField nome;
	private JTextField pais;
	private JComboBox<String> internacional;

	private JButton salvar;

	
	public TelaAddAeroporto(JMenuItem botaoOrigem, ArrayList<Aeroporto> arrayAeroporto) {
		this.botaoOrigem = botaoOrigem;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
		
		nome = new JTextField(20);
		cadastrarNome = new JPanel();
		cadastrarNome.setLayout(new FlowLayout());
		cadastrarNome.add(new JLabel("Nome:"));
		cadastrarNome.add(nome);
		tela.add(cadastrarNome);

		pais = new JTextField(20);
		cadastrarPais = new JPanel();
		cadastrarPais.setLayout(new FlowLayout());
		cadastrarPais.add(new JLabel("País:"));
		cadastrarPais.add(pais);
		tela.add(cadastrarPais);

		String[] vetorAux = {"Selecionar", "Sim", "Não"};
		internacional = new JComboBox<String>(vetorAux);
		cadastrarInternacional = new JPanel();
		cadastrarInternacional.setLayout(new FlowLayout());
		cadastrarInternacional.add(new JLabel("Recebe vôo internacional: "));
		cadastrarInternacional.add(internacional);
		tela.add(cadastrarInternacional);
		
		salvar = new JButton("Cadastrar");
		salvar.addActionListener((e) -> {
			try {
				if(nome.getText().equals("") || pais.getText().equals("") || internacional.getSelectedIndex() == 0) {
					throw new EmptyFieldException();
				}

				Aeroporto aeroporto = new Aeroporto(nome.getText(), pais.getText(), internacional.getSelectedItem().equals("Sim") ? true : false);
				
				arrayAeroporto.forEach((Aeroporto obj) -> {
					if(aeroporto.equals(obj)) throw new EqualObjectException("aeroporto");
				});

				arrayAeroporto.add(aeroporto);

				limparCampos();
				JOptionPane.showMessageDialog(null, "Aeroporto salvo com sucesso");
			}
			catch(OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (EqualObjectException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			
		});
		tela.add(salvar);
		
	}
	
	@Override
	public JPanel getTela() {
		return tela;
	}

	@Override
	public JMenuItem getSource() {
		return botaoOrigem;
	}

	@Override
	public String getMensagemStatus() {
		return "Cadastrando um novo aeroporto...";
	}

	@Override
	public void limparCampos(){
		nome.setText("");
		pais.setText("");
		internacional.setSelectedIndex(0);
	}

}
