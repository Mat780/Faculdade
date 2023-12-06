package interface_usuario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import controladoras.ControladoraOrcamentoDeBuffetCompleto;
import controladoras.ControladoraOrcamentoLocacaoDeEspaco;
import excecoes.ExcecaoDDDInvalido;
import excecoes.ExcecaoParametroPreenchidoErrado;

import negocio.Cliente;
import negocio.Data;
import negocio.Salgado;
import negocio.Doce;
import negocio.Bolo;
import negocio.OrcamentoBuffetCompleto;
import negocio.OrcamentoEvento;
import negocio.OrcamentoLocacaoDeEspaco;
import negocio.ServicoContratado;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 7430438618817551574L;
	
	private Painel painelAtual;
	private PainelConfirmarOuCancelarOrcamento painelConfirmarOuCancelarOrcamento;
	private PainelCriarOuAtualizarCliente painelCriarOuAtualizarCliente;
	private PainelEscolhaDeOrcamento painelEscolhaDeOrcamento;
	private PainelInicialOrcamento painelInicialOrcamento;
	private PainelOpcoesOrcamentoDeBuffetCompleto painelOpcoesOrcamentoDeBuffetCompleto;
	private PainelEscolhaDeServico painelEscolhaDeServico;
	
	private boolean orcandoBuffetCompleto;

	@FunctionalInterface
	interface chamarPopUp {
		void rodar(Painel painel, String msgPrincipal, String msgInstrucao, String msgBotao);
	}

	public JanelaPrincipal() {
		setSize(new Dimension(1280, 720));
		setResizable(false);
		getContentPane().setMinimumSize(new Dimension(1280, 720));
		getContentPane().setMaximumSize(new Dimension(1280, 720));
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//* Cria os listeners para as telas
		MouseListener listenerRetornaTelaInicial = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {retornarTelaInicial();}

			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		};
		
		MouseListener listenerAvancarOrcamento = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {proximaPaginaOrcamento();}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		};
		
		MouseListener listenerVoltarOrcamento = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {voltarPaginaOrcamento();}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		};

		ActionListener listenerEntrarEmOrcamentoBuffetCompleto = e -> entrarEmOrcamento(true);
		
		ActionListener listenerEntrarEmOrcamentoLocacaoDeEspaco = e -> entrarEmOrcamento(false);

		ActionListener listenerConfirmarOrcamento = e -> cadastrarOrcamento();

		chamarPopUp chamadorPopUp = (Painel painel, String msgPrincipal, String msgInstrucao, String msgBotao) -> {
			ActionListener listenerVoltar = e -> trocarPainel(painel);
			trocarPainel(new PopUpErroGenerico(listenerVoltar, msgPrincipal, msgInstrucao, msgBotao));
		};
		
		painelEscolhaDeOrcamento = new PainelEscolhaDeOrcamento(listenerRetornaTelaInicial, listenerEntrarEmOrcamentoBuffetCompleto, listenerEntrarEmOrcamentoLocacaoDeEspaco);
		
		painelInicialOrcamento = new PainelInicialOrcamento(listenerRetornaTelaInicial, listenerAvancarOrcamento);
		
		ArrayList<Salgado> salgados = ControladoraOrcamentoDeBuffetCompleto.getAllSalgados();
		ArrayList<Doce> doces = ControladoraOrcamentoDeBuffetCompleto.getAllDoces();
		ArrayList<Bolo> bolos = ControladoraOrcamentoDeBuffetCompleto.getAllBolos();
		painelOpcoesOrcamentoDeBuffetCompleto = new PainelOpcoesOrcamentoDeBuffetCompleto(listenerRetornaTelaInicial, listenerAvancarOrcamento, listenerVoltarOrcamento, salgados, doces, bolos);
		
		painelEscolhaDeServico = new PainelEscolhaDeServico(listenerRetornaTelaInicial, listenerAvancarOrcamento, listenerVoltarOrcamento, chamadorPopUp);
		
		painelCriarOuAtualizarCliente = new PainelCriarOuAtualizarCliente(listenerRetornaTelaInicial, listenerAvancarOrcamento, listenerVoltarOrcamento);

		painelConfirmarOuCancelarOrcamento = new PainelConfirmarOuCancelarOrcamento(listenerRetornaTelaInicial, listenerVoltarOrcamento, listenerConfirmarOrcamento);
		
		trocarPainel(painelEscolhaDeOrcamento);

		setVisible(true);
	}

	private void trocarPainel(Painel painel) {
		if (painelAtual != null) getContentPane().remove(painelAtual.getPainel());
		
		getContentPane().add(painel.getPainel(), BorderLayout.CENTER);
		getContentPane().repaint();
		
		painelAtual = painel;
		setVisible(true);
	}
	
	private void retornarTelaInicial() {
		
		painelConfirmarOuCancelarOrcamento.limparCampos();
		painelCriarOuAtualizarCliente.limparCampos();
		painelEscolhaDeOrcamento.limparCampos();
		painelInicialOrcamento.limparCampos();
		painelOpcoesOrcamentoDeBuffetCompleto.limparCampos();
		
		trocarPainel(painelEscolhaDeOrcamento);
	}
	
	private void proximaPaginaOrcamento() {
		
		if (painelAtual instanceof PainelInicialOrcamento) { 
			
			ActionListener listenerVoltar = e -> trocarPainel(painelInicialOrcamento);

			PopUpErroGenerico popUp = painelInicialOrcamento.verificarPreenchimento(listenerVoltar);

			if (popUp != null) {
				trocarPainel(popUp);
				return;
			}
			
			if (orcandoBuffetCompleto == true) {
				trocarPainel(painelOpcoesOrcamentoDeBuffetCompleto);
					
			} else {
				trocarPainel(painelEscolhaDeServico);
			}

		} else if (painelAtual instanceof PainelOpcoesOrcamentoDeBuffetCompleto) {
			
			ActionListener listenerVoltar = e -> trocarPainel(painelOpcoesOrcamentoDeBuffetCompleto);

			PopUpErroGenerico popUp = painelOpcoesOrcamentoDeBuffetCompleto.verificarPreenchimento(listenerVoltar);

			if (popUp != null) {
				trocarPainel(popUp);
				return;
			}

			trocarPainel(painelCriarOuAtualizarCliente);

		} else if (painelAtual instanceof PainelEscolhaDeServico) {
			trocarPainel(painelCriarOuAtualizarCliente);

		} else if (painelAtual instanceof PainelCriarOuAtualizarCliente) {

			ActionListener listenerVoltar = e -> trocarPainel(painelCriarOuAtualizarCliente);

			PopUpErroGenerico popUp;

			boolean usuarioJaPesquisado = painelCriarOuAtualizarCliente.getUsuarioJaPesquisado();

			if (usuarioJaPesquisado == false) {

				popUp = painelCriarOuAtualizarCliente.pesquisarCliente(listenerVoltar);

				if (popUp != null) {
					trocarPainel(popUp);
				}

				return;
			}

			popUp = painelCriarOuAtualizarCliente.verificarPreenchimento(listenerVoltar);

			if (popUp != null) {
				trocarPainel(popUp);
				return;
			}

			String nome = painelCriarOuAtualizarCliente.getNome();
			String cpf = painelCriarOuAtualizarCliente.getCPF();
			String endereco = painelCriarOuAtualizarCliente.getAlternado();
			String cep = painelCriarOuAtualizarCliente.getCEP();
			String celular = painelCriarOuAtualizarCliente.getCelular();
			String email = painelCriarOuAtualizarCliente.getEmail();

			String rg = painelCriarOuAtualizarCliente.getRG();
			String telefoneResidencial = painelCriarOuAtualizarCliente.getTelefoneResidencial();
			String telefoneComercial = painelCriarOuAtualizarCliente.getTelefoneComercial();

			Cliente cliente = new Cliente(nome, cpf, endereco, cep, celular, email);						

			if (rg.isBlank() == false && rg.isEmpty() == false) {
				cliente.setRg(rg);
			}
			
			if (telefoneResidencial.equals("       -    ") == false) {
				try {
					cliente.setTelefoneResidencial(telefoneResidencial);
					
				} catch (ExcecaoDDDInvalido | ExcecaoParametroPreenchidoErrado | NumberFormatException erro) {
					popUp = new PopUpErroGenerico(listenerVoltar, "Telefone Residencial", "Para continuar, insira um telefone válido", "Mudar Telefone Residencial");
					trocarPainel(popUp);
					return;
				}
			}
			
			if (telefoneComercial.equals("        -    ") == false) {
				try {
					cliente.setTelefoneComercial(telefoneComercial);
					
				} catch (ExcecaoDDDInvalido | ExcecaoParametroPreenchidoErrado | NumberFormatException erro) {
					popUp = new PopUpErroGenerico(listenerVoltar, "Telefone Comercial", "Para continuar, insira um celular válido", "Mudar Telefone Comercial");
					trocarPainel(popUp);
					return;
				}
			}

			int numeroDeConvidados = painelInicialOrcamento.getInputNumeroDeConvidados();
			int numeroDeColaboradores = painelInicialOrcamento.getInputNumeroDeColaboradores();
			Data data = painelInicialOrcamento.getData();
			String horaDeInicio = painelInicialOrcamento.getInputHoraDeInicio();
			
			OrcamentoEvento orcamento = null;

			if (orcandoBuffetCompleto == true) {
				boolean teraCerveja = painelOpcoesOrcamentoDeBuffetCompleto.getTeraCerveja();
				ArrayList<Salgado> salgados = painelOpcoesOrcamentoDeBuffetCompleto.getSalgadosEscolhidos();
				ArrayList<Doce> doces = painelOpcoesOrcamentoDeBuffetCompleto.getDocesEscolhidos();
				Bolo boloEscolhido = painelOpcoesOrcamentoDeBuffetCompleto.getBoloEscolhido();

				orcamento = ControladoraOrcamentoDeBuffetCompleto.criarOrcamento(numeroDeConvidados, numeroDeColaboradores, data, cliente, horaDeInicio, teraCerveja, salgados, doces, boloEscolhido);

			} else {
				ArrayList<ServicoContratado> servicosContratados = painelEscolhaDeServico.getServicosContratados();

				orcamento = ControladoraOrcamentoLocacaoDeEspaco.criarOrcamento(numeroDeConvidados, numeroDeColaboradores, horaDeInicio, data, cliente);

				ControladoraOrcamentoLocacaoDeEspaco.anexarServicos((OrcamentoLocacaoDeEspaco) orcamento, servicosContratados);
			}

			painelConfirmarOuCancelarOrcamento.setOrcamento(orcamento);
			trocarPainel(painelConfirmarOuCancelarOrcamento);
		}
	}
	
	private void voltarPaginaOrcamento() {
		
		if (orcandoBuffetCompleto == true) {

			if (painelAtual instanceof PainelConfirmarOuCancelarOrcamento) {
				trocarPainel(painelCriarOuAtualizarCliente);
				
			} else if (painelAtual instanceof PainelCriarOuAtualizarCliente) {
				trocarPainel(painelOpcoesOrcamentoDeBuffetCompleto);
			
			} else if (painelAtual instanceof PainelOpcoesOrcamentoDeBuffetCompleto) {
				trocarPainel(painelInicialOrcamento);
			
			} else {
				System.err.println("Ao voltar à página, orçando um buffet completo, o painel atual é um erro");
			}
			
		} else {
			if (painelAtual instanceof PainelConfirmarOuCancelarOrcamento) {
				trocarPainel(painelCriarOuAtualizarCliente);
			
			} else if (painelAtual instanceof PainelCriarOuAtualizarCliente) {
				trocarPainel(painelEscolhaDeServico);
			
			} else if (painelAtual instanceof PainelEscolhaDeServico) {
				trocarPainel(painelInicialOrcamento);
			
			} else {
				System.err.println("Ao voltar à página, orçando uma locação de espaço, o painel atual é um erro");
			}
		
		}
	}

	private void entrarEmOrcamento(boolean eOrcamentoBuffetCompleto) {
		orcandoBuffetCompleto = eOrcamentoBuffetCompleto;

		painelInicialOrcamento.mudarTipoOrcamento(eOrcamentoBuffetCompleto);

		trocarPainel(painelInicialOrcamento);
	}

	private void cadastrarOrcamento() {
		if (orcandoBuffetCompleto) {
			OrcamentoBuffetCompleto orcamento = (OrcamentoBuffetCompleto) painelConfirmarOuCancelarOrcamento.getOrcamento();
			ControladoraOrcamentoDeBuffetCompleto.cadastrarOrcamento(orcamento);

		} else {
			OrcamentoLocacaoDeEspaco orcamento = (OrcamentoLocacaoDeEspaco) painelConfirmarOuCancelarOrcamento.getOrcamento();
			ControladoraOrcamentoLocacaoDeEspaco.cadastrarOrcamento(orcamento); 
		}

		retornarTelaInicial();
		
	}
	
}
