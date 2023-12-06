package negocio;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Pagamento {
	private double valorTotal;
	private String formaDePagamento;
	private ArrayList<Parcela> parcelas = new ArrayList<>();
	
	public Pagamento(double valorTotal, String formaDePagamento, int quantidadeDeParcelas) {
		setValorTotal(valorTotal);
		setFormaDePagamento(formaDePagamento);
		criaParcelas(quantidadeDeParcelas);
	}
	
	private void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	private void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}
	
	private void criaParcelas(int quantidadeDeParcelas) {
		double valorPago = valorTotal / quantidadeDeParcelas;
		int dia = 0, mes = 0, ano = 0; 
		
		GregorianCalendar dataAtual = new GregorianCalendar();
		dataAtual.set(Calendar.MONTH, dataAtual.get(Calendar.MONTH)+1);
		dia = dataAtual.get(Calendar.DAY_OF_MONTH);
		mes = dataAtual.get(Calendar.MONTH)+1;
		ano = dataAtual.get(Calendar.YEAR);
		
		for (int i = 0; i < quantidadeDeParcelas; i++) {
			
			if (mes == 13) {
				mes = 1;
				ano = ano + 1;
				dataAtual.set(ano, mes-1, dia);
			}
			
			if (dia >= 29) {
				
				if (mes == 2 && ((dataAtual.getActualMaximum(Calendar.DAY_OF_YEAR) == 366 && dia > 29) || 
					(dataAtual.getActualMaximum(Calendar.DAY_OF_YEAR) == 365 && dia > 28))) {
					dia = 1;
				} else if (dataAtual.getActualMaximum(Calendar.DAY_OF_MONTH) < dia) {
					dia = 1;
					mes = mes + 1;
				}
			}
			
			if (mes == 13) {
				mes = 1;
				ano = ano + 1;
			}
			
			dataAtual.set(ano, mes-1, dia);
			Data dataVencimento = new Data(dia, mes, ano);
			
			mes = mes + 1;

			Parcela parcela = new Parcela(valorPago, dataVencimento);
			parcelas.add(parcela);
		}
	
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public ArrayList<Parcela> getParcelas() {
		return parcelas;
	}
}
