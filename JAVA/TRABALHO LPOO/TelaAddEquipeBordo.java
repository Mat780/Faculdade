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

public class TelaAddEquipeBordo implements Tela {
	private JPanel tela;

	private JPanel cadastrarPiloto;
	private JPanel cadastrarCoPiloto;
	private JPanel cadastrarLimpeza;
	private JPanel cadastrarServico;
	private JPanel cadastrarInstrucao;
	private JPanel cadastrarRecepcao;
	
	private JMenuItem botaoOrigem;

	private DefaultComboBoxModel<Piloto> modelPiloto;
	private DefaultComboBoxModel<Piloto> modelCoPiloto;
	private DefaultComboBoxModel<Comissario> modelLimpeza;
	private DefaultComboBoxModel<Comissario> modelServico;
	private DefaultComboBoxModel<Comissario> modelInstrucao;
	private DefaultComboBoxModel<Comissario> modelRecepcao;

	private JComboBox<Piloto> piloto;
	private JComboBox<Piloto> coPiloto;
	private JComboBox<Comissario> limpeza;
	private JComboBox<Comissario> servico;
	private JComboBox<Comissario> instrucao;
	private JComboBox<Comissario> recepcao;

	private Piloto[] vetorPilotos;
	private Comissario[] vetorComissarioLimpeza;
	private Comissario[] vetorComissarioServico;
	private Comissario[] vetorComissarioInstrucao;
	private Comissario[] vetorComissarioRecepcao;

	private ArrayList<Piloto> arrayPilotoLocal;
	private ArrayList<Comissario> arrayComissarioLocal;

	private JButton salvar;

	
	public TelaAddEquipeBordo(JMenuItem botaoOrigem, ArrayList<EquipeBordo> arrayEquipeBordo, ArrayList<Piloto> arrayPiloto, ArrayList<Comissario> arrayComissario) {
		this.botaoOrigem = botaoOrigem;
		this.arrayPilotoLocal = arrayPiloto;
		this.arrayComissarioLocal = arrayComissario;
		
		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		modelPiloto = new DefaultComboBoxModel<Piloto>(vetorPilotos);
		modelCoPiloto = new DefaultComboBoxModel<Piloto>(vetorPilotos);
		modelLimpeza = new DefaultComboBoxModel<Comissario>(vetorComissarioLimpeza);
		modelServico = new DefaultComboBoxModel<Comissario>(vetorComissarioServico);
		modelInstrucao = new DefaultComboBoxModel<Comissario>(vetorComissarioInstrucao);
		modelRecepcao = new DefaultComboBoxModel<Comissario>(vetorComissarioRecepcao);
		
		piloto = new JComboBox<Piloto>(modelPiloto);
		coPiloto = new JComboBox<Piloto>(modelCoPiloto);

		limpeza = new JComboBox<Comissario>(modelLimpeza);
		servico = new JComboBox<Comissario>(modelServico);
		instrucao = new JComboBox<Comissario>(modelInstrucao);
		recepcao = new JComboBox<Comissario>(modelRecepcao);
		
		cadastrarPiloto = new JPanel();
		cadastrarPiloto.setLayout(new FlowLayout());
		cadastrarPiloto.add(new JLabel("Piloto: "));
		cadastrarPiloto.add(piloto);
		tela.add(cadastrarPiloto);

		cadastrarCoPiloto = new JPanel();
		cadastrarCoPiloto.setLayout(new FlowLayout());
		cadastrarCoPiloto.add(new JLabel("Co-Piloto: "));
		cadastrarCoPiloto.add(coPiloto);
		tela.add(cadastrarCoPiloto);

		cadastrarLimpeza = new JPanel();
		cadastrarLimpeza.setLayout(new FlowLayout());
		cadastrarLimpeza.add(new JLabel("Comissario para Limpeza: "));
		cadastrarLimpeza.add(limpeza);
		tela.add(cadastrarLimpeza);

		cadastrarServico = new JPanel();
		cadastrarServico.setLayout(new FlowLayout());
		cadastrarServico.add(new JLabel("Comissario para Serviço: "));
		cadastrarServico.add(servico);
		tela.add(cadastrarServico);

		cadastrarInstrucao = new JPanel();
		cadastrarInstrucao.setLayout(new FlowLayout());
		cadastrarInstrucao.add(new JLabel("Comissario para Instruções: "));
		cadastrarInstrucao.add(instrucao);
		tela.add(cadastrarInstrucao);

		cadastrarRecepcao = new JPanel();
		cadastrarRecepcao.setLayout(new FlowLayout());
		cadastrarRecepcao.add(new JLabel("Comissario para Recepção: "));
		cadastrarRecepcao.add(recepcao);
		tela.add(cadastrarRecepcao);
		
		salvar = new JButton("Cadastrar");
		salvar.addActionListener((e) -> {
			try {
				if(piloto.getSelectedIndex() == 0 ||
					coPiloto.getSelectedIndex() == 0 ||
					limpeza.getSelectedIndex() == 0 || 
					servico.getSelectedIndex() == 0 || 
					instrucao.getSelectedIndex() == 0 || 
					recepcao.getSelectedIndex() == 0) throw new EmptyFieldException();

				else if(piloto.getSelectedIndex() == coPiloto.getSelectedIndex()){
					throw new EqualPilotSelectedException();
				}

				Comissario[] vetorAux = {(Comissario)limpeza.getSelectedItem(), (Comissario)servico.getSelectedItem(), (Comissario)instrucao.getSelectedItem(), (Comissario)recepcao.getSelectedItem()};

				Piloto castPiloto = (Piloto)piloto.getSelectedItem();
				Piloto castCoPiloto = (Piloto)coPiloto.getSelectedItem();

				if (castPiloto.getAbordo()){
					throw new AlreadyBordedException("piloto");
				} else if (castCoPiloto.getAbordo()){
					throw new AlreadyBordedException("co-piloto");
				} else {
					for(int i=0; i< vetorAux.length; i++){
						if(vetorAux[i].getAbordo()){
							throw new AlreadyBordedException("comissario de " + vetorAux[i].getTarefa().toLowerCase());
						}
					}
				}

				EquipeBordo equipeBordo = new EquipeBordo(castPiloto, castCoPiloto, vetorAux);
				
				castPiloto.setAbordo(true);
				castCoPiloto.setAbordo(true);
				for(int i = 0; i < vetorAux.length; i++) vetorAux[i].setAbordo(true);

				arrayEquipeBordo.add(equipeBordo);
				limparCampos();
				JOptionPane.showMessageDialog(null, "Equipe de bordo salva com sucesso");
			}
			catch (EqualPilotSelectedException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (InsufficientTaskTypeException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			} 
			catch (LessExperienceException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
			}
			catch (AlreadyBordedException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}
			catch (EmptyFieldException event){
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}

		});
		tela.add(salvar);
		
	}

	private void atualizarTela() {
		if(arrayPilotoLocal.size() == 0) {
			this.vetorPilotos = new Piloto[1];
			
		} else {
			this.vetorPilotos = new Piloto[arrayPilotoLocal.size() + 1];
			for(int i = 0; i < arrayPilotoLocal.size(); i++){
				vetorPilotos[i+1] = arrayPilotoLocal.get(i);
			}
			
		}

		try {
			vetorPilotos[0] = new Piloto();
		} catch (Exception e) {
			e.printStackTrace();
		}


		if(arrayComissarioLocal.size() == 0){
			this.vetorComissarioLimpeza = new Comissario[1];
			this.vetorComissarioServico = new Comissario[1];
			this.vetorComissarioInstrucao = new Comissario[1];
			this.vetorComissarioRecepcao = new Comissario[1];

			try{
				vetorComissarioLimpeza[0] = new Comissario();
				vetorComissarioServico[0] = new Comissario();
				vetorComissarioInstrucao[0] = new Comissario();
				vetorComissarioRecepcao[0] = new Comissario();
			} catch (Exception e){
				e.printStackTrace();
			}
			
		} else {
			int qtdLimpeza = 0;
			int qtdServico = 0;
			int qtdInstrucao = 0;
			int qtdRecepcao = 0;

			for(int i = 0; i < arrayComissarioLocal.size(); i++){
				if(arrayComissarioLocal.get(i).getTarefa().equals("Limpeza")) qtdLimpeza++;
				else if(arrayComissarioLocal.get(i).getTarefa().equals("Serviço")) qtdServico++;
				else if(arrayComissarioLocal.get(i).getTarefa().equals("Instruções")) qtdInstrucao++;
				else if(arrayComissarioLocal.get(i).getTarefa().equals("Recepção")) qtdRecepcao++;
			}

			this.vetorComissarioLimpeza = new Comissario[qtdLimpeza + 1];
			this.vetorComissarioServico = new Comissario[qtdServico + 1];
			this.vetorComissarioInstrucao = new Comissario[qtdInstrucao + 1];
			this.vetorComissarioRecepcao = new Comissario[qtdRecepcao + 1];

			try{
				vetorComissarioLimpeza[0] = new Comissario();
				vetorComissarioServico[0] = new Comissario();
				vetorComissarioInstrucao[0] = new Comissario();
				vetorComissarioRecepcao[0] = new Comissario();
			} catch (Exception e){
				e.printStackTrace();
			}
			

			int j;
			for(int i = 0; i < arrayComissarioLocal.size(); i++){
				j = 1;
				if(arrayComissarioLocal.get(i).getTarefa().equals("Limpeza")){
					for(; vetorComissarioLimpeza[j] != null; j++);
					vetorComissarioLimpeza[j] = arrayComissarioLocal.get(i);

				} else if (arrayComissarioLocal.get(i).getTarefa().equals("Serviço")){
					for(; vetorComissarioServico[j] != null; j++);
					vetorComissarioServico[j] = arrayComissarioLocal.get(i);
				
				} else if (arrayComissarioLocal.get(i).getTarefa().equals("Instruções")){
					for(; vetorComissarioInstrucao[j] != null; j++);
					vetorComissarioInstrucao[j] = arrayComissarioLocal.get(i);
				
				} else if (arrayComissarioLocal.get(i).getTarefa().equals("Recepção")){
					for(; vetorComissarioRecepcao[j] != null; j++);
					vetorComissarioRecepcao[j] = arrayComissarioLocal.get(i);
				}

			}

		}

		if(modelPiloto != null){
			for(int i = modelPiloto.getSize() - 1; i > 0; i--){
				modelPiloto.removeElementAt(i);
				modelCoPiloto.removeElementAt(i);
			}

			for(int i = 1; i < vetorPilotos.length; i++){
				modelPiloto.addElement(vetorPilotos[i]);
				modelCoPiloto.addElement(vetorPilotos[i]);
			}
		}

		if(modelLimpeza != null){
			for(int i = modelLimpeza.getSize() - 1; i > 0; i--){
				modelLimpeza.removeElementAt(i);
			}

			for(int i = 1; i < vetorComissarioLimpeza.length; i++){
				modelLimpeza.addElement(vetorComissarioLimpeza[i]);
			}
		}

		if(modelServico != null){
			for(int i = modelServico.getSize() - 1; i > 0; i--){
				modelServico.removeElementAt(i);
			}

			for(int i = 1; i < vetorComissarioServico.length; i++){
				modelServico.addElement(vetorComissarioServico[i]);
			}
		}

		if(modelInstrucao != null){
			for(int i = modelInstrucao.getSize() - 1; i > 0; i--){
				modelInstrucao.removeElementAt(i);
			}

			for(int i = 1; i < vetorComissarioInstrucao.length; i++){
				modelInstrucao.addElement(vetorComissarioInstrucao[i]);
			}
		}

		if(modelRecepcao != null){
			for(int i = modelRecepcao.getSize() - 1; i > 0; i--){
				modelRecepcao.removeElementAt(i);
			}

			for(int i = 1; i < vetorComissarioRecepcao.length; i++){
				modelRecepcao.addElement(vetorComissarioRecepcao[i]);
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
		return "Cadastrando uma nova equipe de bordo...";
	}

	@Override
	public void limparCampos(){
		piloto.setSelectedIndex(0);
		coPiloto.setSelectedIndex(0);
		limpeza.setSelectedIndex(0);
		servico.setSelectedIndex(0);
		instrucao.setSelectedIndex(0);
		recepcao.setSelectedIndex(0);
	}

}
