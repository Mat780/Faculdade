package persistencia;

import java.util.ArrayList;

import negocio.Doce;

public class TesteDoceDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method 
		System.out.println(ConexaoBanco.getConexao());
		System.out.println(ConexaoBanco.status);
		
		//criar DAO
		
		DoceDAO dao = new DoceDAO();
		
		//criar
		Doce d = new Doce(1,"Brigadeiro",1.5);
		
		dao.criar(d);
		
		//atualizar
		
		Doce d1 = new Doce(d.getId(),"Brigadeiro Gurmet",d.getValorUnitario());
		
		dao.atualizar(d1);
		
		//buscar
		
		System.out.println("Resultado busca: "+dao.get(d1.getId()).getDescricao());
		
		//buscar todos
		
		Doce d2 = new Doce(2,"Beijingo",2);
		dao.criar(d2);
	
		ArrayList<Doce> list = dao.getAll();
		
		System.out.println("\nlista:");
		for (int i=0; i<list.size();i++) {
			Doce a = list.get(i);
			
			System.out.println(a.getDescricao());
		}
		
		//deletar
		dao.deletar(d2.getId());
	}

}
