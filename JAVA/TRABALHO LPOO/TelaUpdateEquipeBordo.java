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

public class TelaUpdateEquipeBordo implements Tela, Update {
	private JPanel tela;

	private JPanel selecionarEquipeBordo;
	private JPanel cadastrarPiloto;
	private JPanel cadastrarCoPiloto;
	private JPanel cadastrarLimpeza;
	private JPanel cadastrarServico;
	private JPanel cadastrarInstrucao;
	private JPanel cadastrarRecepcao;

	private JMenuItem botaoOrigem;

	private DefaultComboBoxModel<EquipeBordo> modelEquipeBordo;
	private DefaultComboBoxModel<Piloto> modelPiloto;
	private DefaultComboBoxModel<Piloto> modelCoPiloto;
	private DefaultComboBoxModel<Comissario> modelLimpeza;
	private DefaultComboBoxModel<Comissario> modelServico;
	private DefaultComboBoxModel<Comissario> modelInstrucao;
	private DefaultComboBoxModel<Comissario> modelRecepcao;

	private JComboBox<EquipeBordo> equipeBordo;
	private JComboBox<Piloto> piloto;
	private JComboBox<Piloto> coPiloto;
	private JComboBox<Comissario> limpeza;
	private JComboBox<Comissario> servico;
	private JComboBox<Comissario> instrucao;
	private JComboBox<Comissario> recepcao;

	private EquipeBordo[] vetorEquipeBordo;
	private Piloto[] vetorPilotos;
	private Comissario[] vetorComissarioLimpeza;
	private Comissario[] vetorComissarioServico;
	private Comissario[] vetorComissarioInstrucao;
	private Comissario[] vetorComissarioRecepcao;

	private ArrayList<Piloto> arrayPilotoLocal;
	private ArrayList<Comissario> arrayComissarioLocal;
	private ArrayList<EquipeBordo> arrayEquipeBordoLocal;

	private EquipeBordo equipeBordoAlterando;
	private EquipeBordo cloneEquipeBordo;

	private JButton salvar;

	public TelaUpdateEquipeBordo(JMenuItem botaoOrigem, ArrayList<EquipeBordo> arrayEquipeBordo,
			ArrayList<Piloto> arrayPiloto, ArrayList<Comissario> arrayComissario) {
		this.botaoOrigem = botaoOrigem;
		this.arrayEquipeBordoLocal = arrayEquipeBordo;
		this.arrayPilotoLocal = arrayPiloto;
		this.arrayComissarioLocal = arrayComissario;

		atualizarTela();

		tela = new JPanel();
		tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

		modelEquipeBordo = new DefaultComboBoxModel<EquipeBordo>(vetorEquipeBordo);
		modelPiloto = new DefaultComboBoxModel<Piloto>(vetorPilotos);
		modelCoPiloto = new DefaultComboBoxModel<Piloto>(vetorPilotos);
		modelLimpeza = new DefaultComboBoxModel<Comissario>(vetorComissarioLimpeza);
		modelServico = new DefaultComboBoxModel<Comissario>(vetorComissarioServico);
		modelInstrucao = new DefaultComboBoxModel<Comissario>(vetorComissarioInstrucao);
		modelRecepcao = new DefaultComboBoxModel<Comissario>(vetorComissarioRecepcao);

		equipeBordo = new JComboBox<EquipeBordo>(modelEquipeBordo);
		piloto = new JComboBox<Piloto>(modelPiloto);
		coPiloto = new JComboBox<Piloto>(modelCoPiloto);

		limpeza = new JComboBox<Comissario>(modelLimpeza);
		servico = new JComboBox<Comissario>(modelServico);
		instrucao = new JComboBox<Comissario>(modelInstrucao);
		recepcao = new JComboBox<Comissario>(modelRecepcao);

		selecionarEquipeBordo = new JPanel();
		selecionarEquipeBordo.setLayout(new FlowLayout());
		selecionarEquipeBordo.add(new JLabel("Equipe de bordo:"));
		selecionarEquipeBordo.add(equipeBordo);
		tela.add(selecionarEquipeBordo);

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

		equipeBordo.addActionListener((e) -> {
			if (equipeBordo.getSelectedIndex() != 0) {
				EquipeBordo eb = (EquipeBordo) equipeBordo.getSelectedItem();
				Comissario[] vetAux = eb.getListaComissarios();

				piloto.setSelectedItem(eb.getPiloto());
				coPiloto.setSelectedItem(eb.getCoPiloto());

				for (int i = 0; i < vetAux.length; i++) {
					if (vetAux[i].getTarefa().equals("Limpeza")) {
						limpeza.setSelectedItem(vetAux[i]);

					} else if (vetAux[i].getTarefa().equals("Serviço")) {
						servico.setSelectedItem(vetAux[i]);

					} else if (vetAux[i].getTarefa().equals("Instruções")) {
						instrucao.setSelectedItem(vetAux[i]);

					} else if (vetAux[i].getTarefa().equals("Recepção")) {
						recepcao.setSelectedItem(vetAux[i]);
					}
				}

			} else {
				piloto.setSelectedIndex(0);
				coPiloto.setSelectedIndex(0);
				limpeza.setSelectedIndex(0);
				servico.setSelectedIndex(0);
				instrucao.setSelectedIndex(0);
				recepcao.setSelectedIndex(0);
			}
		});

		salvar = new JButton("Atualizar");
		salvar.addActionListener((e) -> {
			try {
				if (equipeBordo.getSelectedIndex() == 0 ||
						piloto.getSelectedIndex() == 0 ||
						coPiloto.getSelectedIndex() == 0 ||
						limpeza.getSelectedIndex() == 0 ||
						servico.getSelectedIndex() == 0 ||
						instrucao.getSelectedIndex() == 0 ||
						recepcao.getSelectedIndex() == 0)
					throw new EmptyFieldException();

				else if (piloto.getSelectedIndex() == coPiloto.getSelectedIndex()) {
					throw new EqualPilotSelectedException();
				}

				Comissario[] vetorAux = { (Comissario) limpeza.getSelectedItem(),
						(Comissario) servico.getSelectedItem(), (Comissario) instrucao.getSelectedItem(),
						(Comissario) recepcao.getSelectedItem() };

				Piloto castPiloto = (Piloto) piloto.getSelectedItem();
				Piloto castCoPiloto = (Piloto) coPiloto.getSelectedItem();
				equipeBordoAlterando = (EquipeBordo) equipeBordo.getSelectedItem();

				if (castPiloto.getAbordo() &&
						castPiloto.equals(equipeBordoAlterando.getPiloto()) == false &&
						castPiloto.equals(equipeBordoAlterando.getCoPiloto()) == false) {
					throw new AlreadyBordedException("piloto");
				} else if (castCoPiloto.getAbordo() &&
						castCoPiloto.equals(equipeBordoAlterando.getPiloto()) == false &&
						castCoPiloto.equals(equipeBordoAlterando.getCoPiloto()) == false) {
					throw new AlreadyBordedException("co-piloto");
				} else {
					Comissario[] antigosComissarios = equipeBordoAlterando.getListaComissarios();
					for (int i = 0; i < vetorAux.length; i++) {
						System.out.printf(vetorAux[i].getNome(), "\n", antigosComissarios[i].getNome(), "\n");
						if (vetorAux[i].getAbordo() && vetorAux[i].equals(antigosComissarios[i]) == false) {
							throw new AlreadyBordedException("comissario de " + vetorAux[i].getTarefa().toLowerCase());
						}
					}
				}

				cloneEquipeBordo = equipeBordoAlterando.clonar();

				equipeBordoAlterando.setPiloto(castPiloto);
				equipeBordoAlterando.setCoPiloto(castCoPiloto);
				equipeBordoAlterando.setListaComissarios(vetorAux);

				cloneEquipeBordo.getPiloto().setAbordo(false);
				cloneEquipeBordo.getCoPiloto().setAbordo(false);
				Comissario[] vetorTemp = cloneEquipeBordo.getListaComissarios();
				for (int i = 0; i < vetorTemp.length; i++)
					vetorTemp[i].setAbordo(false);

				castPiloto.setAbordo(true);
				castCoPiloto.setAbordo(true);
				for (int i = 0; i < vetorAux.length; i++)
					vetorAux[i].setAbordo(true);

				atualizarTela();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Equipe de bordo salva com sucesso");
			} catch (EqualPilotSelectedException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(cloneEquipeBordo, equipeBordoAlterando);
			} catch (InsufficientTaskTypeException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(cloneEquipeBordo, equipeBordoAlterando);
			} catch (LessExperienceException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Erro", 0);
				setInfoBack(cloneEquipeBordo, equipeBordoAlterando);
			} catch (AlreadyBordedException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
				setInfoBack(cloneEquipeBordo, equipeBordoAlterando);
			} catch (EmptyFieldException event) {
				JOptionPane.showMessageDialog(null, event.getMessage(), "Aviso", 2);
			}

		});
		tela.add(salvar);

	}

	@Override
	public void setInfoBack(Object Antigo, Object Atual) {
		try {
			EquipeBordo equipeBordoAntigo = (EquipeBordo) Antigo;
			EquipeBordo equipeBordoAtual = (EquipeBordo) Atual;

			equipeBordoAtual.setPiloto(equipeBordoAntigo.getPiloto());
			equipeBordoAtual.setCoPiloto(equipeBordoAntigo.getCoPiloto());
			equipeBordoAtual.setListaComissarios(equipeBordoAntigo.getListaComissarios());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void atualizarTela() {

		if (arrayEquipeBordoLocal.size() == 0) {
			this.vetorEquipeBordo = new EquipeBordo[1];

		} else {
			this.vetorEquipeBordo = new EquipeBordo[arrayEquipeBordoLocal.size() + 1];
			for (int i = 0; i < arrayEquipeBordoLocal.size(); i++) {
				vetorEquipeBordo[i + 1] = arrayEquipeBordoLocal.get(i);
			}

		}

		try {
			vetorEquipeBordo[0] = new EquipeBordo();
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		if (arrayComissarioLocal.size() == 0) {
			this.vetorComissarioLimpeza = new Comissario[1];
			this.vetorComissarioServico = new Comissario[1];
			this.vetorComissarioInstrucao = new Comissario[1];
			this.vetorComissarioRecepcao = new Comissario[1];

			try {
				vetorComissarioLimpeza[0] = new Comissario();
				vetorComissarioServico[0] = new Comissario();
				vetorComissarioInstrucao[0] = new Comissario();
				vetorComissarioRecepcao[0] = new Comissario();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			int qtdLimpeza = 0;
			int qtdServico = 0;
			int qtdInstrucao = 0;
			int qtdRecepcao = 0;

			for (int i = 0; i < arrayComissarioLocal.size(); i++) {
				if (arrayComissarioLocal.get(i).getTarefa().equals("Limpeza"))
					qtdLimpeza++;
				else if (arrayComissarioLocal.get(i).getTarefa().equals("Serviço"))
					qtdServico++;
				else if (arrayComissarioLocal.get(i).getTarefa().equals("Instruções"))
					qtdInstrucao++;
				else if (arrayComissarioLocal.get(i).getTarefa().equals("Recepção"))
					qtdRecepcao++;
			}

			this.vetorComissarioLimpeza = new Comissario[qtdLimpeza + 1];
			this.vetorComissarioServico = new Comissario[qtdServico + 1];
			this.vetorComissarioInstrucao = new Comissario[qtdInstrucao + 1];
			this.vetorComissarioRecepcao = new Comissario[qtdRecepcao + 1];

			try {
				vetorComissarioLimpeza[0] = new Comissario();
				vetorComissarioServico[0] = new Comissario();
				vetorComissarioInstrucao[0] = new Comissario();
				vetorComissarioRecepcao[0] = new Comissario();
			} catch (Exception e) {
				e.printStackTrace();
			}

			int j;
			for (int i = 0; i < arrayComissarioLocal.size(); i++) {
				j = 1;
				if (arrayComissarioLocal.get(i).getTarefa().equals("Limpeza")) {
					for (; vetorComissarioLimpeza[j] != null; j++)
						;
					vetorComissarioLimpeza[j] = arrayComissarioLocal.get(i);

				} else if (arrayComissarioLocal.get(i).getTarefa().equals("Serviço")) {
					for (; vetorComissarioServico[j] != null; j++)
						;
					vetorComissarioServico[j] = arrayComissarioLocal.get(i);

				} else if (arrayComissarioLocal.get(i).getTarefa().equals("Instruções")) {
					for (; vetorComissarioInstrucao[j] != null; j++)
						;
					vetorComissarioInstrucao[j] = arrayComissarioLocal.get(i);

				} else if (arrayComissarioLocal.get(i).getTarefa().equals("Recepção")) {
					for (; vetorComissarioRecepcao[j] != null; j++)
						;
					vetorComissarioRecepcao[j] = arrayComissarioLocal.get(i);
				}

			}

		}

		if (modelEquipeBordo != null) {
			for (int i = modelEquipeBordo.getSize() - 1; i > 0; i--) {
				modelEquipeBordo.removeElementAt(i);
			}

			for (int i = 1; i < vetorEquipeBordo.length; i++) {
				modelEquipeBordo.addElement(vetorEquipeBordo[i]);
			}
		}

		if (modelPiloto != null) {
			for (int i = modelPiloto.getSize() - 1; i > 0; i--) {
				modelPiloto.removeElementAt(i);
				modelCoPiloto.removeElementAt(i);
			}

			for (int i = 1; i < vetorPilotos.length; i++) {
				modelPiloto.addElement(vetorPilotos[i]);
				modelCoPiloto.addElement(vetorPilotos[i]);
			}
		}

		if (modelLimpeza != null) {
			for (int i = modelLimpeza.getSize() - 1; i > 0; i--) {
				modelLimpeza.removeElementAt(i);
			}

			for (int i = 1; i < vetorComissarioLimpeza.length; i++) {
				modelLimpeza.addElement(vetorComissarioLimpeza[i]);
			}
		}

		if (modelServico != null) {
			for (int i = modelServico.getSize() - 1; i > 0; i--) {
				modelServico.removeElementAt(i);
			}

			for (int i = 1; i < vetorComissarioServico.length; i++) {
				modelServico.addElement(vetorComissarioServico[i]);
			}
		}

		if (modelInstrucao != null) {
			for (int i = modelInstrucao.getSize() - 1; i > 0; i--) {
				modelInstrucao.removeElementAt(i);
			}

			for (int i = 1; i < vetorComissarioInstrucao.length; i++) {
				modelInstrucao.addElement(vetorComissarioInstrucao[i]);
			}
		}

		if (modelRecepcao != null) {
			for (int i = modelRecepcao.getSize() - 1; i > 0; i--) {
				modelRecepcao.removeElementAt(i);
			}

			for (int i = 1; i < vetorComissarioRecepcao.length; i++) {
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
		return "Atualizando uma equipe de bordo...";
	}

	@Override
	public void limparCampos() {
		equipeBordo.setSelectedIndex(0);
		piloto.setSelectedIndex(0);
		coPiloto.setSelectedIndex(0);
		limpeza.setSelectedIndex(0);
		servico.setSelectedIndex(0);
		instrucao.setSelectedIndex(0);
		recepcao.setSelectedIndex(0);
	}

}
