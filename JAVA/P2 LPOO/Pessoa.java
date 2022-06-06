public abstract class Pessoa implements Valor{
	private String cpf;
	private Atividade[] listaAtividades;

	public Pessoa(String cpf, Atividade[] listaAtividades){
		setCpf(cpf);
		setListaAtividades(listaAtividades);
	}

	private void setCpf(String cpf) {
		if(cpf.length() == 11){
			this.cpf = cpf;
		} else {
			System.out.printf("%s%s\n", "CPF inválido: " , cpf);
			System.out.println("CPFs válidos tem 11 números");
		}
		
	}

	private void setListaAtividades(Atividade[] listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public String getCpf() {
		return cpf;
	}

	public Atividade[] getListaAtividades() {
		return listaAtividades;
	}

	@Override
	public void AddAtividade(Atividade atividade) {
		Atividade[] listaAtividades = getListaAtividades();
		int contador; 

		for(contador = 0; listaAtividades.length > contador ; contador++){
			if(listaAtividades[contador] == null) break;
		}

		if(contador < listaAtividades.length){
			listaAtividades[contador] = atividade;
			setListaAtividades(listaAtividades);
		} else {
			System.out.println("Lista de atividades cheia, portanto a tentativa de adicionar uma atividade foi cancelada");
		}
		
	}
}
