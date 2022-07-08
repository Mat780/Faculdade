public class Comissario extends Pessoa {
    private String tarefa;

    public Comissario(String nome, String cpf, String tarefa)
    throws InvalidTaskException,
           OnlyLetterException,
           InvalidCpfException
    {
        super(nome, cpf);
        setTarefa(tarefa);
    }

    public Comissario() throws OnlyLetterException, InvalidCpfException {
        super("Selecionar", "000.000.000-00");
	}

	public void setTarefa(String tarefa) throws InvalidTaskException { // Limpeza, instruções, serviço de bordo e recepção// colocar um dropbox de seleção na interface gráfica
        if(tarefa.equals("Limpeza")) this.tarefa = tarefa;
        else if(tarefa.equals("Instruções")) this.tarefa = tarefa;
        else if(tarefa.equals("Serviço")) this.tarefa = tarefa;
        else if(tarefa.equals("Recepção")) this.tarefa = tarefa;
        else { // ERRO (Criar uma exceção para casos em que a tarefa não se enquadre dentro das 4)
            throw new InvalidTaskException(tarefa);
        }
    }

    public String getTarefa(){
        return tarefa;
    }
    
    public String toSelect() {
        return "CPF: " + getCpf();
    }

    @Override
    public String toString() {
        if(getCpf().equals("000.000.000-00")){
            return "Selecionar";
        } else {
            return getNome() + " que possui CPF: " + getCpf() + " faz a tarefa de " + getTarefa();

        }
    }

    public Comissario clonar() throws InvalidTaskException, OnlyLetterException, InvalidCpfException{
        return new Comissario(getNome(), getCpf(), getTarefa());
    }
    

}
