package negocio;

public class ServicoContratado {
	
	private int quantidade;
	private ServicoAdicional servicoAdicional;
	
	public ServicoContratado(int quantidade, ServicoAdicional servicoAdicional) {
		setQuantidade(quantidade);
		setServicoAdicional(servicoAdicional);
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	private void setServicoAdicional(ServicoAdicional servicoAdicional) {
		this.servicoAdicional = servicoAdicional;
	}

	public int getQuantidade() {
		return quantidade;
	}


	public ServicoAdicional getServicoAdicional() {
		return servicoAdicional;
	}

	@Override
	public String toString() {
		return servicoAdicional.getNome() + String.format(" (%.2f) - ", servicoAdicional.getValor()) + quantidade;
	}
}
