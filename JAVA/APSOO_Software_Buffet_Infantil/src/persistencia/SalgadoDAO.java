package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Salgado;

public class SalgadoDAO implements DAO<Salgado, Integer> {

	@Override
	public Salgado get(Integer objetoInteger) {
		int id = objetoInteger.intValue();

		String sql = "SELECT * FROM salgado WHERE id = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Salgado salgadoEncontrado = new Salgado(rs.getInt("id"), rs.getString("descricao"),
							rs.getDouble("valorUnitario"));
					return salgadoEncontrado;
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
	public ArrayList<Salgado> getAll() {
		String sql = "SELECT * FROM salgado";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<Salgado> salgadosEncontrados = new ArrayList<Salgado>();
				while (rs.next()) {
					Salgado salgadoEncontrado = new Salgado(rs.getInt("id"), rs.getString("descricao"),
							rs.getDouble("valorUnitario"));
					salgadosEncontrados.add(salgadoEncontrado);
				}
				return salgadosEncontrados;
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
	public boolean criar(Salgado salgado) {
		String sql = "INSERT INTO salgado VALUES (?, ?, ?)";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			Random random = new Random();
			int id = random.nextInt(2147483646);
			while (existeEssaChavePrimaria(id)) {
				id = random.nextInt(2147483646);
			}
			statement.setInt(1, id);
			statement.setString(2, salgado.getDescricao());
			statement.setDouble(3, salgado.getValorUnitario());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Salgado inserido com sucesso!");
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
	public boolean atualizar(Salgado salgado) {
		String sql = "UPDATE salgado SET descricao = ?, valorUnitario = ? WHERE id = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, salgado.getDescricao());
			statement.setDouble(2, salgado.getValorUnitario());
			statement.setInt(3, salgado.getId());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Salgado atualizado com sucesso!");
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
	public boolean deletar(Integer objetoInteger) {
		String sql = "DELETE FROM salgado WHERE id = ?";
		int id = objetoInteger.intValue();
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Salgado deletado com sucesso!");
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean existeEssaChavePrimaria(Integer chavePrimaria) {
		int chavePrimariaInt = chavePrimaria.intValue();
		String sql = "SELECT * FROM salgado WHERE id = ?";
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
}
