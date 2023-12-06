package negocio;

import persistencia.SalgadoSelecionadoDAO;

public class SalgadoSelecionado {
	private int quantidade;
	private Salgado salgado;

	public SalgadoSelecionado(int quantidade, Salgado salgado) {
		setQuantidade(quantidade);
		setSalgado(salgado);
	}

	private void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	private void setSalgado(Salgado salgado) {
		this.salgado = salgado;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Salgado getSalgado() {
		return salgado;
	}

	public double getValor() {
		return salgado.getValorUnitario() * quantidade;
	}
	
	public boolean cadastrar(int idBuffet) {
		SalgadoSelecionadoDAO salgadoSelecionadoDAO = new SalgadoSelecionadoDAO();
		return salgadoSelecionadoDAO.criarBuffet(this, idBuffet);
	}

}
