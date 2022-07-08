import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaAddPiloto implements Tela {
	private JPanel tela;
	private JPanel cadastrarNome;
	private JPanel cadastrarCpf;
	private JPanel cadastrarIdade;
	private JPanel cadastrarExp;

	private JMenuItem botaoOrigem;

	private JTextField nome;
	private JTextField cpf;
	private JTextField idade;
	private JTextField anosExp;

	private JButton salvar;

	
	public TelaAddPiloto(JMenuItem botaoOrigem, ArrayList<Piloto> arrayPiloto) {
		this.botaoOrigem = botaoOrigem;
		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
		
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

		idade = new JTextField(10);
		cadastrarIdade = new JPanel();
		cadastrarIdade.setLayout(new FlowLayout());
		cadastrarIdade.add(new JLabel("Idade do piloto: "));
		cadastrarIdade.add(idade);
		tela.add(cadastrarIdade);
		
		anosExp = new JTextField(10);
		cadastrarExp = new JPanel();
		cadastrarExp.setLayout(new FlowLayout());
		cadastrarExp.add(new JLabel("Anos de experiência: "));
		cadastrarExp.add(anosExp);
		tela.add(cadastrarExp);

		
		salvar = new JButton("Cadastrar");
		salvar.addActionListener((e) -> {
			try {
				if(nome.getText().equals("") || cpf.getText().equals("") || idade.getText().equals("") || anosExp.getText().equals("")){
					throw new EmptyFieldException();
				}

				Piloto p = new Piloto(nome.getText(), cpf.getText(), Integer.parseInt(idade.getText()), Integer.parseInt(anosExp.getText()));
				
				arrayPiloto.forEach((Piloto obj) -> {
					if(p.equals(obj)) throw new EqualObjectException("piloto");
				});

				arrayPiloto.add(p);
				limparCampos();
				JOptionPane.showMessageDialog(null, "Piloto salvo com sucesso");
			}
			catch(OnlyLetterException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch(InvalidCpfException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch(TooOldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
			}
			catch(TooYoungException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
			}
			catch(ImpossibleTimeExperienceException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
			}
			catch (OnlyPositiveNumbersException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			} 
			catch (EqualObjectException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch(NumberFormatException event) {
				JOptionPane.showMessageDialog(null, "Os campos de idade e anos de experiência precisam ser compostos de numeros apenas", "Aviso", 2);

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
		return "Cadastrando um novo piloto...";
	}

	@Override
	public void limparCampos(){
		nome.setText("");
		cpf.setText("");
		idade.setText("");
		anosExp.setText("");
	}

}
