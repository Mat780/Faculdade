package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import negocio.Salgado;
import negocio.ServicoAdicional;
import excecoes.ExcecaoValorNaoSetado;

public class ServicoAdicionalDAO implements DAO<ServicoAdicional, Integer>{

	@Override
	public ServicoAdicional get(Integer chavePrimaria) {
		int id = chavePrimaria.intValue();

		String sql = "SELECT * FROM servicoadicional WHERE id = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					ServicoAdicional servicoAdicionalEncontrado = new ServicoAdicional(rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor"), rs.getBoolean("servicoUnico"));
					return servicoAdicionalEncontrado;
				}
			}
			return null;

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean existeEssaChavePrimaria(Integer chavePrimaria) {
		int chavePrimariaInt = chavePrimaria.intValue();
		String sql = "SELECT * FROM servicoadicional WHERE id = ?";
		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, chavePrimariaInt);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
			return true;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return true;
		}
	}

	@Override
	public ArrayList<ServicoAdicional> getAll() {
		String sql = "SELECT * FROM servicoadicional";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<ServicoAdicional> servicosAdicionaisEncontrados = new ArrayList<ServicoAdicional>();
				while (rs.next()) {
					ServicoAdicional servicoAdicionalEncontrado = new ServicoAdicional(rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor"), rs.getBoolean("servicoUnico"));
					servicosAdicionaisEncontrados.add(servicoAdicionalEncontrado);
				}
				return servicosAdicionaisEncontrados;
			}

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(ServicoAdicional servicoAdicional) {
		String sql = "INSERT INTO servicoadicional VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			Random random = new Random();
			int id = random.nextInt(2147483646);
			while (existeEssaChavePrimaria(id)) {
				id = random.nextInt(2147483646);
			}
			statement.setInt(1, id);
			statement.setString(2, servicoAdicional.getNome());
			statement.setDouble(3, servicoAdicional.getValor());
			statement.setBoolean(4, false);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("ServicoAdicional inserido com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean atualizar(ServicoAdicional servicoAdicional) {
		String sql = "UPDATE servicoadicional SET nome = ?, valorUnitario = ? WHERE id = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, servicoAdicional.getNome());
			statement.setDouble(2, servicoAdicional.getValor());
			statement.setInt(3, servicoAdicional.getId());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("ServicoAdicional atualizado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} catch (ExcecaoValorNaoSetado e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean deletar(Integer chavePrimaria) {
		String sql = "DELETE FROM servicoadicional WHERE id = ?";
		int id = chavePrimaria.intValue();
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("ServicoAdicional deletado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

}
