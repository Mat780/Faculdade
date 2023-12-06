package negocio;

public class Parcela {
	private double valorPago;
	private Data dataVencimento;
	private Data dataPagamento;
	
	public Parcela(double valorPago, Data dataVencimento) {
		setValorPago(valorPago);
		setDataVencimento(dataVencimento);
	}
	
	private void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
	
	private void setDataVencimento(Data dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public void pagarParcela(Data dataPagamento) {
		this.dataPagamento= dataPagamento;
	}
	
	public double getValorPago() {
		return valorPago;
	}
	
	public Data getDataVencimento() {
		return dataVencimento;
	}
	
	public Data getDataPagamento() {
		return dataPagamento;
	}
}
