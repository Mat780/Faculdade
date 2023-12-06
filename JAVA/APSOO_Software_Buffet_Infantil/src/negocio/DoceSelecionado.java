package negocio;

import persistencia.DoceSelecionadoDAO;

public class DoceSelecionado {
	private int quantidade;
	private Doce doce;
	
	public DoceSelecionado(int quantidade, Doce doce) {
		setQuantidade(quantidade);
		setDoce(doce);
	}
	
	private void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	private void setDoce(Doce doce) {
		this.doce = doce;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Doce getDoce() {
		return doce;
	}
	
	public double getValor() {
		double valorDoce;
		
		valorDoce = doce.getValorUnitario() * quantidade;
		return valorDoce;
	}
	
	public void cadastrar(int idBuffet) {
		DoceSelecionadoDAO doceSelecionadoDAO = new DoceSelecionadoDAO();
		doceSelecionadoDAO.criarBuffet(this, idBuffet);
	}

	
}
