package persistencia;
import java.util.ArrayList;

import negocio.Salgado;

public class TesteSalgadoDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(ConexaoBanco.getConexao());
		System.out.println(ConexaoBanco.status);
		
		SalgadoDAO dao = new SalgadoDAO();
		
		Salgado s = new Salgado(1,"Coxinha",200);
		
		dao.criar(s);
		
		Salgado s2 = new Salgado(s.getId(),"Coxinha de frango",s.getValorUnitario());
		
		dao.atualizar(s2);
		
		System.out.println(dao.get(1).getDescricao());
		
		Salgado s3 = new Salgado(2,"Pão Italiano",100);
		dao.criar(s3);
		
		ArrayList<Salgado> list = dao.getAll();
		
		for (int i=0; i<list.size();i++) {
			Salgado a = list.get(i);
			
			System.out.println(a.getDescricao());
		}
		
		dao.deletar(s3.getId());

	}

}
