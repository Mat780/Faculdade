package interface_usuario;

import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import negocio.ServicoAdicional;
import negocio.ServicoContratado;
import persistencia.ServicoAdicionalDAO;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import interface_usuario.JanelaPrincipal.chamarPopUp;

import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;

public class PainelEscolhaDeServico extends Painel {

	private JPanel painel;
	private JList<ServicoContratado> servicosContratados;
	private JList<ServicoAdicional> servicosDisponiveis;
	private DefaultListModel<ServicoAdicional> arrayServicoDisponivel;
	private DefaultListModel<ServicoContratado> arrayServicoContratado;
	
	private JButton botaoContratar;
	private JFormattedTextField inputQuantidade;
	private JButton botaoRemover;
	
	public PainelEscolhaDeServico(MouseListener voltaTelaInicial, MouseListener avancaPagina, MouseListener voltaPagina, chamarPopUp chamadorPopUp) {
		super();
		
		arrayServicoDisponivel = new DefaultListModel<>();
		arrayServicoContratado = new DefaultListModel<>();

		ServicoAdicionalDAO servicoAdicionalDAO = new ServicoAdicionalDAO();
		arrayServicoDisponivel.addAll(servicoAdicionalDAO.getAll());

		painel = new JPanel();
		painel.setBackground(getCorDeFundo());
		
		JLabel labelTitulo = new JLabel("Serviços contratados");
		labelTitulo.setFont(getFonteTitulo());
		labelTitulo.setForeground(getCorTitulo());

		JPanel painelVoltaPagina = new JPanel();
		JPanel painelAvancaPagina = new JPanel();
		JPanel painelRetornaTudo = new JPanel();
		painelVoltaPagina.setBackground(getCorDeFundo());
		painelAvancaPagina.setBackground(getCorDeFundo());
		painelRetornaTudo.setBackground(getCorDeFundo());
		
		JPanel painelDelimitador = new JPanel();
		painelDelimitador.setBackground(getCorDeFundo());
		
		JPanel painelDelimitadorJList = new JPanel();
		painelDelimitadorJList.setBackground(getCorDeFundo());
		painelDelimitadorJList.setLayout(new GridLayout(2, 0, 0, 10));
		
		RoundJPanel painelServicoDisponivel = new RoundJPanel(25, 25, 25, 25);
		painelServicoDisponivel.setBorder(new EmptyBorder(10, 15, 15, 15));
		painelServicoDisponivel.setBackground(getCorDeJanelaInterna());
		painelDelimitadorJList.add(painelServicoDisponivel);
		
		JLabel labelServicoDisponivel = new JLabel("Serviços disponíveis");
		labelServicoDisponivel.setFont(getFonteLabelInterno());
		labelServicoDisponivel.setForeground(getCorTexto());
		
		RoundJPanel painelServicoContratado = new RoundJPanel(25, 25, 25, 25);
		painelServicoContratado.setBackground(getCorDeJanelaInterna());
		painelServicoContratado.setBorder(new EmptyBorder(10, 15, 15, 15));
		painelDelimitadorJList.add(painelServicoContratado);
		
		JLabel labelServicosContratados = new JLabel("Serviços contratados");
		labelServicosContratados.setFont(getFonteLabelInterno());
		labelServicosContratados.setForeground(getCorTexto());
		
		JPanel painelDelimitadorBotoes = new JPanel();
		painelDelimitadorBotoes.setBackground(getCorDeFundo());
		
		JPanel painelDelimitadorRemover = new RoundJPanel(25, 25, 25, 25);
		painelDelimitadorRemover.setBorder(new EmptyBorder(10, 10, 10, 10));
		painelDelimitadorRemover.setBackground(getCorDeJanelaInterna());
		painelDelimitadorRemover.setLayout(new BorderLayout(0, 0));
		
		JPanel painelDelimitadorContratar = new RoundJPanel(25, 25, 25, 25);
		painelDelimitadorContratar.setBorder(new EmptyBorder(10, 10, 10, 10));
		painelDelimitadorContratar.setBackground(getCorDeJanelaInterna());
		painelDelimitadorContratar.setLayout(new BorderLayout(0, 0));
		
		JPanel painelDelimitadorQuantidade = new RoundJPanel(25, 25, 25, 25);
		painelDelimitadorQuantidade.setBorder(new EmptyBorder(10, 10, 10, 10));
		painelDelimitadorQuantidade.setBackground(getCorDeJanelaInterna());
		
		JPanel painelFakeQuantidade = new RoundJPanel(25, 25, 25, 25);
		painelFakeQuantidade.setBackground(Color.WHITE);
		
		JLabel labelQuantidade = new JLabel("Quantidade:");
		labelQuantidade.setFont(getFonteLabelInterno());
		
		inputQuantidade = new JFormattedTextField();
		inputQuantidade.setFont(getFonteInputs());
		
		try {
			MaskFormatter mascara = new MaskFormatter("#####");
			mascara.install(inputQuantidade);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JScrollPane painelScrollServicoDisponivel = new JScrollPane();
		
		GroupLayout gl_painelServicoDisponivel = new GroupLayout(painelServicoDisponivel);
		gl_painelServicoDisponivel.setHorizontalGroup(
			gl_painelServicoDisponivel.createParallelGroup(Alignment.LEADING)
				.addComponent(labelServicoDisponivel, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
				.addComponent(painelScrollServicoDisponivel, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
		);
		gl_painelServicoDisponivel.setVerticalGroup(
			gl_painelServicoDisponivel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelServicoDisponivel.createSequentialGroup()
					.addGap(5)
					.addComponent(labelServicoDisponivel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelScrollServicoDisponivel, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
		);
		
		servicosDisponiveis = new JList<>();
		painelScrollServicoDisponivel.setViewportView(servicosDisponiveis);
		servicosDisponiveis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
				servicosDisponiveis.setFont(getFonteInputs());
				
				servicosDisponiveis.setModel(arrayServicoDisponivel);

		painelServicoDisponivel.setLayout(gl_painelServicoDisponivel);
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_painelServicoContratado = new GroupLayout(painelServicoContratado);
		gl_painelServicoContratado.setHorizontalGroup(
			gl_painelServicoContratado.createParallelGroup(Alignment.LEADING)
				.addComponent(labelServicosContratados, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
		);
		gl_painelServicoContratado.setVerticalGroup(
			gl_painelServicoContratado.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelServicoContratado.createSequentialGroup()
					.addGap(5)
					.addComponent(labelServicosContratados, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
		);
		servicosContratados = new JList<>();
		scrollPane.setViewportView(servicosContratados);
		servicosContratados.setFont(getFonteInputs());
		
		painelServicoContratado.setLayout(gl_painelServicoContratado);
		
		GroupLayout gl_painelFakeQuantidade = new GroupLayout(painelFakeQuantidade);
		gl_painelFakeQuantidade.setHorizontalGroup(
			gl_painelFakeQuantidade.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelFakeQuantidade.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelQuantidade, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(inputQuantidade, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
					.addGap(14))
		);
		gl_painelFakeQuantidade.setVerticalGroup(
			gl_painelFakeQuantidade.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painelFakeQuantidade.createParallelGroup(Alignment.BASELINE)
					.addComponent(inputQuantidade, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
					.addComponent(labelQuantidade, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
		);
		painelFakeQuantidade.setLayout(gl_painelFakeQuantidade);
		GroupLayout gl_painelDelimitadorQuantidade = new GroupLayout(painelDelimitadorQuantidade);
		gl_painelDelimitadorQuantidade.setHorizontalGroup(
			gl_painelDelimitadorQuantidade.createParallelGroup(Alignment.LEADING)
				.addGap(0, 320, Short.MAX_VALUE)
				.addComponent(painelFakeQuantidade, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
		);
		gl_painelDelimitadorQuantidade.setVerticalGroup(
			gl_painelDelimitadorQuantidade.createParallelGroup(Alignment.LEADING)
				.addGap(0, 49, Short.MAX_VALUE)
				.addComponent(painelFakeQuantidade, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
		);
		painelDelimitadorQuantidade.setLayout(gl_painelDelimitadorQuantidade);
		GroupLayout gl_painelDelimitadorBotoes = new GroupLayout(painelDelimitadorBotoes);
		gl_painelDelimitadorBotoes.setHorizontalGroup(
			gl_painelDelimitadorBotoes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorBotoes.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_painelDelimitadorBotoes.createParallelGroup(Alignment.LEADING)
						.addComponent(painelDelimitadorRemover, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelDelimitadorQuantidade, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
						.addComponent(painelDelimitadorContratar, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(52, Short.MAX_VALUE))
		);
		gl_painelDelimitadorBotoes.setVerticalGroup(
			gl_painelDelimitadorBotoes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelDelimitadorBotoes.createSequentialGroup()
					.addContainerGap(103, Short.MAX_VALUE)
					.addComponent(painelDelimitadorContratar, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(50)
					.addComponent(painelDelimitadorQuantidade, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(50)
					.addComponent(painelDelimitadorRemover, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(111))
		);
		
		botaoContratar = new SemVFXJButton("Contratar", Color.WHITE, Color.WHITE);
		botaoRemover = new SemVFXJButton("Remover", Color.WHITE, Color.WHITE);
		
		botaoContratar.setFont(getFonteLabelInterno());
		botaoRemover.setFont(getFonteLabelInterno());
		
		painelDelimitadorContratar.add(botaoContratar, BorderLayout.CENTER);
		painelDelimitadorRemover.add(botaoRemover, BorderLayout.CENTER);
		
		painelDelimitadorBotoes.setLayout(gl_painelDelimitadorBotoes);
		
		GroupLayout gl_painelDelimitador = new GroupLayout(painelDelimitador);
		gl_painelDelimitador.setHorizontalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1030, Short.MAX_VALUE)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addComponent(painelDelimitadorJList, GroupLayout.PREFERRED_SIZE, 597, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelDelimitadorBotoes, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_painelDelimitador.setVerticalGroup(
			gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
				.addGap(0, 527, Short.MAX_VALUE)
				.addGroup(gl_painelDelimitador.createSequentialGroup()
					.addGroup(gl_painelDelimitador.createParallelGroup(Alignment.LEADING)
						.addComponent(painelDelimitadorJList, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
						.addComponent(painelDelimitadorBotoes, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE))
					.addGap(5))
		);
		painelDelimitador.setLayout(gl_painelDelimitador);
		
		GroupLayout gl_painel = new GroupLayout(painel);
		gl_painel.setHorizontalGroup(
			gl_painel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_painel.createSequentialGroup()
							.addContainerGap()
							.addComponent(painelVoltaPagina, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 1100, Short.MAX_VALUE)
							.addComponent(painelAvancaPagina, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_painel.createSequentialGroup()
							.addGap(40)
							.addComponent(labelTitulo, GroupLayout.PREFERRED_SIZE, 517, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 623, Short.MAX_VALUE)
							.addComponent(painelRetornaTudo, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(gl_painel.createSequentialGroup()
					.addContainerGap(118, Short.MAX_VALUE)
					.addComponent(painelDelimitador, GroupLayout.PREFERRED_SIZE, 1030, GroupLayout.PREFERRED_SIZE)
					.addGap(112))
		);
		gl_painel.setVerticalGroup(
			gl_painel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
						.addComponent(painelRetornaTudo, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_painel.createSequentialGroup()
							.addComponent(labelTitulo, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addGroup(gl_painel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_painel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 535, Short.MAX_VALUE)
							.addGroup(gl_painel.createParallelGroup(Alignment.TRAILING)
								.addComponent(painelVoltaPagina, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(painelAvancaPagina, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
						.addGroup(gl_painel.createSequentialGroup()
							.addGap(6)
							.addComponent(painelDelimitador, GroupLayout.PREFERRED_SIZE, 527, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		painelRetornaTudo.setLayout(new BorderLayout(0, 0));
		
		JLabel labelRetornaTudo = new JLabel("");
		labelRetornaTudo.setIcon(getIconeRetornaTudo());
		labelRetornaTudo.addMouseListener(voltaTelaInicial);
		painelRetornaTudo.add(labelRetornaTudo, BorderLayout.CENTER);
		painelAvancaPagina.setLayout(new BorderLayout(0, 0));
		
		JLabel labelAvancaPagina = new JLabel("");
		labelAvancaPagina.setIcon(getIconeProximaPagina());
		labelAvancaPagina.addMouseListener(avancaPagina);
		painelAvancaPagina.add(labelAvancaPagina, BorderLayout.CENTER);
		painelVoltaPagina.setLayout(new BorderLayout(0, 0));
		
		JLabel labelVoltaPagina = new JLabel("");
		labelVoltaPagina.setIcon(getIconeVoltarPagina());
		labelVoltaPagina.addMouseListener(voltaPagina);
		painelVoltaPagina.add(labelVoltaPagina, BorderLayout.CENTER);
		
		configurarBotoes(chamadorPopUp);

		painel.setLayout(gl_painel);

	}

	private void configurarBotoes(chamarPopUp chamadorPopUp) {

		botaoContratar.addActionListener(e -> {

			int index = servicosDisponiveis.getSelectedIndex();
			boolean jaEstavaContratado = false;

			if (index == -1) {
				chamadorPopUp.rodar(this, "Serviço a ser adicionado não foi selecionado", "Para continuar, selecione um serviço para adicionar.", "Selecionar serviço");
				return;
			}

			if (inputQuantidade.getText().isEmpty() || inputQuantidade.getText().isBlank()) {
				chamadorPopUp.rodar(this, "Quantidade não informada", "Para continuar, informe uma quantidade para adicionar", "Selecionar quantidade");
				return;
			}
			
			int quantidade = Integer.parseInt(inputQuantidade.getText().strip());
			int quantidadeOriginal = quantidade;
			inputQuantidade.setText("");
			
			ServicoAdicional aux = servicosDisponiveis.getSelectedValue();
			ServicoContratado auxContratado = null;
			servicosDisponiveis.clearSelection();
			
			for (int i = 0; i < arrayServicoContratado.size(); i++) {
				if (aux.equals(arrayServicoContratado.get(i).getServicoAdicional())) {
					auxContratado = arrayServicoContratado.remove(i);
					jaEstavaContratado = true;
					i = arrayServicoContratado.size();
				}
			}
			
			if (auxContratado == null) {
				auxContratado = new ServicoContratado(quantidade, aux);
			
			} else {
				quantidadeOriginal = auxContratado.getQuantidade();
				quantidade += quantidadeOriginal;
				auxContratado.setQuantidade(quantidade);	
			}

			if (aux.getServicoUnico()) {
				if (quantidade != 1) {
					chamadorPopUp.rodar(this, "Esse serviço é único", "Portanto pode ser contratado apenas uma vez", "Continuar");
					return;
				}

				arrayServicoDisponivel.removeElement(aux);

			} else if (quantidade > 10) {
				chamadorPopUp.rodar(this, "Quantidade inválida", "Para continuar, coloque uma quantidade válida", "Mudar quantidade");

				if (jaEstavaContratado == false) return; 
				
				auxContratado.setQuantidade(quantidadeOriginal);
			}

			arrayServicoContratado.addElement(auxContratado);
			servicosContratados.setModel(arrayServicoContratado);

		});

		botaoRemover.addActionListener(e -> {
			int index = servicosContratados.getSelectedIndex();

			if (index == -1) {
				chamadorPopUp.rodar(this, "Serviço a ser removido não foi selecionado", "Para continuar, selecione um serviço para remover.", "Selecionar serviço");
				return;
			}

			if (inputQuantidade.getText().isEmpty() || inputQuantidade.getText().isBlank()) {
				chamadorPopUp.rodar(this, "Quantidade não informada", "Para continuar, informe uma quantidade para remover", "Selecionar quantidade");
				return;
			}
			
			ServicoContratado auxContratado = servicosContratados.getSelectedValue();
			servicosContratados.clearSelection();
			
			int quantidade = Integer.parseInt(inputQuantidade.getText().strip());
			int quantidadeOriginal = auxContratado.getQuantidade();
			quantidade = quantidadeOriginal - quantidade;
			inputQuantidade.setText("");
			
			if (quantidade < 0) {
				chamadorPopUp.rodar(this, "Quantidade inválida", "Para continuar, coloque uma quantidade válida", "Mudar quantidade");
				return;
				
			} else if (quantidade == 0){
				if (auxContratado.getServicoAdicional().getServicoUnico()) {
					ServicoAdicional aux = auxContratado.getServicoAdicional();
					arrayServicoDisponivel.addElement(aux);
					servicosDisponiveis.setModel(arrayServicoDisponivel);
				}
				arrayServicoContratado.removeElement(auxContratado);

			} else {
				auxContratado.setQuantidade(quantidade);
			}

			servicosContratados.setModel(arrayServicoContratado);

		});

	}

	public ArrayList<ServicoContratado> getServicosContratados() {
		ArrayList<ServicoContratado> arrayContratados = new ArrayList<>();

		for (int i = 0; i < arrayServicoContratado.size(); i++) {
			arrayContratados.add(arrayServicoContratado.get(i));
		}

		return arrayContratados;
	}
	
	@Override
	public JPanel getPainel() {
		return painel;
	}

	@Override
	public void limparCampos() {
		arrayServicoDisponivel = new DefaultListModel<>();
		arrayServicoContratado = new DefaultListModel<>();

		ServicoAdicionalDAO servicoAdicionalDAO = new ServicoAdicionalDAO();
		arrayServicoDisponivel.addAll(servicoAdicionalDAO.getAll());

		servicosDisponiveis.setModel(arrayServicoDisponivel);
		servicosContratados.setModel(arrayServicoContratado);

		inputQuantidade.setText("");
	}
}
