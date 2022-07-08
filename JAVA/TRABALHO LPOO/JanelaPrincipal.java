import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class JanelaPrincipal extends JFrame {

	// A barra de menu, junto aos menus disponíveis
	private JMenuBar barraMenus;
	private JMenu menuAdd;
	private JMenu menuUpdate;
	private JMenu menuDelete;
	private JMenu menuList;

	// O texto de status de como está a aplicacao
	private JLabel status;

	// ArrayList de telas, junto a tela atual
	private ArrayList<Tela> arrayTelas;
	private Tela telaAtual;

	// Itens dos menus de Add, Update, Delete e List
	private JMenuItem menuItemAddEquipeBordo;
	private JMenuItem menuItemUpdateEquipeBordo;
	private JMenuItem menuItemDeleteEquipeBordo;
	private JMenuItem menuItemListEquipeBordo;

	private JMenuItem menuItemAddComissario;
	private JMenuItem menuItemUpdateComissario;
	private JMenuItem menuItemDeleteComissario;
	private JMenuItem menuItemListComissario;

	private JMenuItem menuItemAddPiloto;
	private JMenuItem menuItemUpdatePiloto;
	private JMenuItem menuItemDeletePiloto;
	private JMenuItem menuItemListPiloto;

	private JMenuItem menuItemAddAviao;
	private JMenuItem menuItemUpdateAviao;
	private JMenuItem menuItemDeleteAviao;
	private JMenuItem menuItemListAviao;

	private JMenuItem menuItemAddAeroporto;
	private JMenuItem menuItemUpdateAeroporto;
	private JMenuItem menuItemDeleteAeroporto;
	private JMenuItem menuItemListAeroporto;

	private JMenuItem menuItemAddPassageiro;
	private JMenuItem menuItemUpdatePassageiro;
	private JMenuItem menuItemDeletePassageiro;
	private JMenuItem menuItemListPassageiro;

	private JMenuItem menuItemAddViagem;
	private JMenuItem menuItemUpdateViagem;
	private JMenuItem menuItemDeleteViagem;
	private JMenuItem menuItemListViagem;

	// ArrayList<>
	private ArrayList<EquipeBordo> arrayEquipeBordo;
	private ArrayList<Comissario> arrayComissario;
	private ArrayList<Piloto> arrayPiloto;
	private ArrayList<Aviao> arrayAviao;
	private ArrayList<Aeroporto> arrayAeroporto;
	private ArrayList<Passageiro> arrayPassageiro;
	private ArrayList<Viagem> arrayViagem;

	public JanelaPrincipal() {
		super();
		setLayout(new BorderLayout());

		configuraVetoresDeEntidades();

		configuraMenuStatus();

		configuraTelas();

		configuraListeners();

		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void configuraVetoresDeEntidades() {
		arrayEquipeBordo = new ArrayList<EquipeBordo>();
		arrayComissario = new ArrayList<Comissario>();
		arrayPiloto = new ArrayList<Piloto>();
		arrayAviao = new ArrayList<Aviao>();
		arrayAeroporto = new ArrayList<Aeroporto>();
		arrayPassageiro = new ArrayList<Passageiro>();
		arrayViagem = new ArrayList<Viagem>();

		// Colocar informações padrões.

		try {
			// Adicionando Comissarios
			arrayComissario.add(new Comissario("Mikey", "100.000.000-00", "Limpeza"));
			arrayComissario.add(new Comissario("Draken", "222.222.222-22", "Serviço"));
			arrayComissario.add(new Comissario("Takemichi", "111.111.111-11", "Instruções"));
			arrayComissario.add(new Comissario("Izana", "333.333.333-33", "Recepção"));

			// Adicionando Pilotos
			arrayPiloto.add(new Piloto("Matheus", "100.000.000-00", 23, 5));
			arrayPiloto.add(new Piloto("Felipe", "111.111.111-11", 30, 4));
			arrayPiloto.add(new Piloto("Zugrati", "222.222.222-22", 70, 50));
			arrayPiloto.add(new Piloto("Tim maia", "333.333.333-33", 80, 10));
			arrayPiloto.add(new Piloto("Diorno", "444.444.444-44", 18, 0));
			arrayPiloto.add(new Piloto("Jonathan", "999.999.999-99", 88, 70));

			// Adicionando Equipes de Bordo
			Comissario[] vetorAux = { arrayComissario.get(0), arrayComissario.get(1), arrayComissario.get(2),
					arrayComissario.get(3) };
			arrayEquipeBordo.add(new EquipeBordo(arrayPiloto.get(0), arrayPiloto.get(1), vetorAux));
			arrayEquipeBordo.get(0).setTodosAbordo(true);

			// Adicionando Aviões
			arrayAviao.add(new Aviao("TELEX-2500", true, arrayEquipeBordo.get(0)));
			arrayEquipeBordo.get(0).setContratada(true);

			// Adicionand Aeroportos
			arrayAeroporto.add(new Aeroporto("Guarulhos", "Brasil", true));
			arrayAeroporto.add(new Aeroporto("New York Airport", "EUA", true));

			// Adicionando Viagens
			ArrayList<Aeroporto> arrayAeroportoTemp = new ArrayList<Aeroporto>();
			arrayAeroportoTemp.add(arrayAeroporto.get(0));
			arrayAeroportoTemp.add(arrayAeroporto.get(1));
			arrayViagem.add(new Viagem(arrayAviao.get(0), arrayAeroportoTemp, 3000));
			arrayAeroporto.get(0).addViagem(arrayViagem.get(0));
			arrayAeroporto.get(1).addViagem(arrayViagem.get(0));

			// Adicionando Passageiros
			
			arrayPassageiro.add(new Passageiro("Paul", "010.010.010-10", true, arrayViagem.get(0)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configuraMenuStatus() {
		barraMenus = new JMenuBar();
		menuAdd = new JMenu("Adicionar");
		menuUpdate = new JMenu("Atualizar");
		menuDelete = new JMenu("Deletar");
		menuList = new JMenu("Listar");

		menuItemAddEquipeBordo = new JMenuItem("Equipe de Bordo");
		menuItemAddComissario = new JMenuItem("Comissario");
		menuItemAddPiloto = new JMenuItem("Piloto");
		menuItemAddAviao = new JMenuItem("Aviao");
		menuItemAddAeroporto = new JMenuItem("Aeroporto");
		menuItemAddPassageiro = new JMenuItem("Passageiro");
		menuItemAddViagem = new JMenuItem("Viagem");

		menuItemUpdateEquipeBordo = new JMenuItem("Equipe de Bordo");
		menuItemUpdateComissario = new JMenuItem("Comissario");
		menuItemUpdatePiloto = new JMenuItem("Piloto");
		menuItemUpdateAviao = new JMenuItem("Aviao");
		menuItemUpdateAeroporto = new JMenuItem("Aeroporto");
		menuItemUpdatePassageiro = new JMenuItem("Passageiro");
		menuItemUpdateViagem = new JMenuItem("Viagem");

		menuItemDeleteEquipeBordo = new JMenuItem("Equipe de Bordo");
		menuItemDeleteComissario = new JMenuItem("Comissario");
		menuItemDeletePiloto = new JMenuItem("Piloto");
		menuItemDeleteAviao = new JMenuItem("Aviao");
		menuItemDeleteAeroporto = new JMenuItem("Aeroporto");
		menuItemDeletePassageiro = new JMenuItem("Passageiro");
		menuItemDeleteViagem = new JMenuItem("Viagem");

		menuItemListEquipeBordo = new JMenuItem("Equipe de Bordo");
		menuItemListComissario = new JMenuItem("Comissario");
		menuItemListPiloto = new JMenuItem("Piloto");
		menuItemListAviao = new JMenuItem("Aviao");
		menuItemListAeroporto = new JMenuItem("Aeroporto");
		menuItemListPassageiro = new JMenuItem("Passageiro");
		menuItemListViagem = new JMenuItem("Viagem");

		menuAdd.add(menuItemAddEquipeBordo);
		menuAdd.add(menuItemAddComissario);
		menuAdd.add(menuItemAddPiloto);
		menuAdd.add(menuItemAddAviao);
		menuAdd.add(menuItemAddAeroporto);
		menuAdd.add(menuItemAddPassageiro);
		menuAdd.add(menuItemAddViagem);

		menuUpdate.add(menuItemUpdateEquipeBordo);
		menuUpdate.add(menuItemUpdateComissario);
		menuUpdate.add(menuItemUpdatePiloto);
		menuUpdate.add(menuItemUpdateAviao);
		menuUpdate.add(menuItemUpdateAeroporto);
		menuUpdate.add(menuItemUpdatePassageiro);
		menuUpdate.add(menuItemUpdateViagem);

		menuDelete.add(menuItemDeleteEquipeBordo);
		menuDelete.add(menuItemDeleteComissario);
		menuDelete.add(menuItemDeletePiloto);
		menuDelete.add(menuItemDeleteAviao);
		menuDelete.add(menuItemDeleteAeroporto);
		menuDelete.add(menuItemDeletePassageiro);
		menuDelete.add(menuItemDeleteViagem);

		menuList.add(menuItemListEquipeBordo);
		menuList.add(menuItemListComissario);
		menuList.add(menuItemListPiloto);
		menuList.add(menuItemListAviao);
		menuList.add(menuItemListAeroporto);
		menuList.add(menuItemListPassageiro);
		menuList.add(menuItemListViagem);

		barraMenus.add(menuAdd);
		barraMenus.add(menuUpdate);
		barraMenus.add(menuDelete);
		barraMenus.add(menuList);

		setJMenuBar(barraMenus);

		status = new JLabel("Status: Tela Inicial");
		add(status, BorderLayout.SOUTH);
	}

	private void configuraTelas() {
		arrayTelas = new ArrayList<Tela>();

		arrayTelas.add(new TelaAddEquipeBordo(menuItemAddEquipeBordo, arrayEquipeBordo, arrayPiloto, arrayComissario));
		arrayTelas.add(new TelaAddComissario(menuItemAddComissario, arrayComissario));
		arrayTelas.add(new TelaAddPiloto(menuItemAddPiloto, arrayPiloto));
		arrayTelas.add(new TelaAddAviao(menuItemAddAviao, arrayAviao, arrayEquipeBordo));
		arrayTelas.add(new TelaAddAeroporto(menuItemAddAeroporto, arrayAeroporto));
		arrayTelas.add(new TelaAddPassageiro(menuItemAddPassageiro, arrayPassageiro, arrayViagem));
		arrayTelas.add(new TelaAddViagem(menuItemAddViagem, arrayViagem, arrayAviao, arrayAeroporto));

		arrayTelas.add(new TelaUpdateEquipeBordo(menuItemUpdateEquipeBordo, arrayEquipeBordo, arrayPiloto, arrayComissario));
		arrayTelas.add(new TelaUpdateComissario(menuItemUpdateComissario, arrayComissario));
		arrayTelas.add(new TelaUpdatePiloto(menuItemUpdatePiloto, arrayPiloto));
		arrayTelas.add(new TelaUpdateAviao(menuItemUpdateAviao, arrayAviao, arrayEquipeBordo));
		arrayTelas.add(new TelaUpdateAeroporto(menuItemUpdateAeroporto, arrayAeroporto));
		arrayTelas.add(new TelaUpdatePassageiro(menuItemUpdatePassageiro, arrayPassageiro, arrayViagem));
		arrayTelas.add(new TelaUpdateViagem(menuItemUpdateViagem, arrayViagem, arrayAviao));

		arrayTelas.add(new TelaDeleteEquipeBordo(menuItemDeleteEquipeBordo, arrayEquipeBordo));
		arrayTelas.add(new TelaDeleteComissario(menuItemDeleteComissario, arrayComissario));
		arrayTelas.add(new TelaDeletePiloto(menuItemDeletePiloto, arrayPiloto));
		arrayTelas.add(new TelaDeleteAviao(menuItemDeleteAviao, arrayAviao));
		arrayTelas.add(new TelaDeleteAeroporto(menuItemDeleteAeroporto, arrayAeroporto));
		arrayTelas.add(new TelaDeletePassageiro(menuItemDeletePassageiro, arrayPassageiro));
		arrayTelas.add(new TelaDeleteViagem(menuItemDeleteViagem, arrayViagem, arrayPassageiro));

		arrayTelas.add(new TelaListEquipeBordo(menuItemListEquipeBordo, arrayEquipeBordo));
		arrayTelas.add(new TelaListComissario(menuItemListComissario, arrayComissario));
		arrayTelas.add(new TelaListPiloto(menuItemListPiloto, arrayPiloto));
		arrayTelas.add(new TelaListAviao(menuItemListAviao, arrayAviao));
		arrayTelas.add(new TelaListAeroporto(menuItemListAeroporto, arrayAeroporto));
		arrayTelas.add(new TelaListPassageiro(menuItemListPassageiro, arrayPassageiro));
		arrayTelas.add(new TelaListViagem(menuItemListViagem, arrayViagem));

	}

	private void configuraListeners() {

		ActionListener listenerPadrao = (e) -> {
			if (telaAtual != null) {
				telaAtual.limparCampos();
				remove(telaAtual.getTela());

			}

			for (int i = 0; i < arrayTelas.size(); i++) {
				if (arrayTelas.get(i).getSource().equals(e.getSource())) {
					telaAtual = arrayTelas.get(i);
					break;
				}
			}
			add(telaAtual.getTela(), BorderLayout.CENTER);
			status.setText("Status: " + telaAtual.getMensagemStatus());
			setVisible(true);
		};

		menuItemAddEquipeBordo.addActionListener(listenerPadrao);
		menuItemAddComissario.addActionListener(listenerPadrao);
		menuItemAddPiloto.addActionListener(listenerPadrao);
		menuItemAddAviao.addActionListener(listenerPadrao);
		menuItemAddAeroporto.addActionListener(listenerPadrao);
		menuItemAddPassageiro.addActionListener(listenerPadrao);
		menuItemAddViagem.addActionListener(listenerPadrao);

		menuItemUpdateEquipeBordo.addActionListener(listenerPadrao);
		menuItemUpdateComissario.addActionListener(listenerPadrao);
		menuItemUpdatePiloto.addActionListener(listenerPadrao);
		menuItemUpdateAviao.addActionListener(listenerPadrao);
		menuItemUpdateAeroporto.addActionListener(listenerPadrao);
		menuItemUpdatePassageiro.addActionListener(listenerPadrao);
		menuItemUpdateViagem.addActionListener(listenerPadrao);

		menuItemDeleteEquipeBordo.addActionListener(listenerPadrao);
		menuItemDeleteComissario.addActionListener(listenerPadrao);
		menuItemDeletePiloto.addActionListener(listenerPadrao);
		menuItemDeleteAviao.addActionListener(listenerPadrao);
		menuItemDeleteAeroporto.addActionListener(listenerPadrao);
		menuItemDeletePassageiro.addActionListener(listenerPadrao);
		menuItemDeleteViagem.addActionListener(listenerPadrao);

		menuItemListEquipeBordo.addActionListener(listenerPadrao);
		menuItemListComissario.addActionListener(listenerPadrao);
		menuItemListPiloto.addActionListener(listenerPadrao);
		menuItemListAviao.addActionListener(listenerPadrao);
		menuItemListAeroporto.addActionListener(listenerPadrao);
		menuItemListPassageiro.addActionListener(listenerPadrao);
		menuItemListViagem.addActionListener(listenerPadrao);

	}
}
