package persistencia;

import java.util.ArrayList;

public interface DAO<T, PK> {
	
	public T get(PK chavePrimaria);
	public boolean existeEssaChavePrimaria(PK chavePrimaria);
	public ArrayList<T> getAll();
	public boolean criar(T objeto);
	public boolean atualizar(T objeto);
	public boolean deletar(PK chavePrimaria);
	
}
