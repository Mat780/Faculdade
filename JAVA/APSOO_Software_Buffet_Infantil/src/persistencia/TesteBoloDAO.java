package persistencia;
import java.util.ArrayList;

import negocio.Bolo;

public class TesteBoloDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method 
		System.out.print(ConexaoBanco.getConexao());
		System.out.print(ConexaoBanco.status);
		
		BoloDAO dao = new BoloDAO();
		
		Bolo b = new Bolo(1, "Bolo muito gostoso", 150.75);
		
		b.setPeso(2.0);
		
		dao.criar(b);
		
		System.out.println(dao.get(1).getDescricao());
		
		Bolo b2 = new Bolo(b.getId(),"Bolo mais ou menos",b.getValor());

		b2.setPeso(b.getPeso());
		
		dao.atualizar(b2);
		
		System.out.println(dao.get(1).getDescricao());
		
		
		ArrayList<Bolo> list = dao.getAll();
		
		for (int i=0; i<list.size();i++) {
			Bolo a = list.get(i);
			
			System.out.println(a.getPeso());
		}
		
//		dao.deletar(2);
	}

}
