public abstract class Pessoa {
    private String nome;
    private String cpf;
    private boolean abordo;

    public Pessoa(String nome, String cpf) throws OnlyLetterException, InvalidCpfException{
        setNome(nome);
        setCpf(cpf);
        setAbordo(false);
    }
    
    public void setNome(String nome)throws OnlyLetterException{// ERRO: Somente letras são permitidas
        if(nome.matches("[a-zA-Z ]+")){ // Se conter só letras
			this.nome = nome;
		} else { // Senão = ERRO
			throw new OnlyLetterException("nome");
		}
    }

    public void setCpf(String cpf) throws InvalidCpfException{ // ERRO: Tem que ser um cpf válido ou seja 14 caracteres (mostrar forma de cadastro XXX.XXX.XXX-XX)
        if(cpf.length() != 14){
            if(cpf.length() < 14){
                throw new InvalidCpfException("Número insuficiente de caracteres. Um CPF costuma ter 14 caracteres.");
                
            } else {
                throw new InvalidCpfException("Número excessivo de caracteres. Um CPF costuma ter 14 caracteres.");
                
            }

        } else if(nome.equals("000.000.000-00")){
            throw new InvalidCpfException("CPF inválido, este CPF");

        } else if(cpf.charAt(3) == '.' && cpf.charAt(7) == '.' && cpf.charAt(11) == '-'){
            String temp = cpf;
            temp = temp.replace(".", "0");
            temp = temp.replace(".", "0");
            temp = temp.replace("-", "1");

            try{
                Double.parseDouble(temp);

            } catch(Exception e){
                throw new InvalidCpfException("CPF aceita somente números");
            }
            
            this.cpf = cpf;

        } else {
            throw new InvalidCpfException("Formato inválido do CPF. Por favor, digite seu CPF conforme o modelo a seguir: XXX.XXX.XXX-XX");
        }
        
    }

    public void setAbordo(boolean abordo){
        this.abordo = abordo;
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public boolean getAbordo(){
        return abordo;
    }
    
    @Override
    public boolean equals(Object obj){
        Pessoa parametro = (Pessoa) obj;

        if(this.getCpf().equals(parametro.getCpf())){
            return true;
        } else {
            return false;
        }

    }

    
}

