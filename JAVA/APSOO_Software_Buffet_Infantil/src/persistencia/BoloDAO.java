package persistencia;

import java.sql.ResultSet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import excecoes.ExcecaoValorNaoSetado;
import negocio.Bolo;

public class BoloDAO implements DAO<Bolo, Integer> {

	@Override
	public Bolo get(Integer objetoInteger) {
		int id = objetoInteger.intValue();

		String sql = "SELECT * FROM bolo WHERE id = ?";

		try {

			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Bolo boloEncontrado = new Bolo(rs.getInt("id"), rs.getString("descricao"),
							rs.getDouble("valorUnitario"));
					boloEncontrado.setPeso(rs.getDouble("peso"));
					return boloEncontrado;
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
	public ArrayList<Bolo> getAll() {
		String sql = "SELECT * FROM bolo";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);

			try (ResultSet rs = statement.executeQuery()) {
				ArrayList<Bolo> bolosEncontrados = new ArrayList<Bolo>();
				while (rs.next()) {
					Bolo boloEncontrado = new Bolo(rs.getInt("id"), rs.getString("descricao"),
							rs.getDouble("valorUnitario"));
					boloEncontrado.setPeso(rs.getDouble("peso"));
					bolosEncontrados.add(boloEncontrado);
				}
				return bolosEncontrados;
			}
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean criar(Bolo bolo) {
		String sql = "INSERT INTO bolo VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			Random random = new Random();
			int id = random.nextInt(2147483646);
			while (existeEssaChavePrimaria(id)) {
				id = random.nextInt(2147483646);
			}
			statement.setInt(1, id);
			statement.setString(2, bolo.getDescricao());
			statement.setDouble(3, bolo.getValorUnitario());
			statement.setDouble(4, bolo.getPeso());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Bolo inserido com sucesso!");
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
	public boolean atualizar(Bolo bolo) {
		String sql = "UPDATE bolo SET descricao = ?, valorUnitario = ?, peso = ? WHERE id = ?";

		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setString(1, bolo.getDescricao());
			statement.setDouble(2, bolo.getValorUnitario());
			statement.setDouble(3, bolo.getPeso());
			statement.setInt(4, bolo.getId());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Cliente atualizado com sucesso!");
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
		String sql = "DELETE FROM bolo WHERE id = ?";
		int id = objetoInteger.intValue();
		try {
			PreparedStatement statement = ConexaoBanco.getConexao().prepareStatement(sql);
			statement.setInt(1, id);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Bolo deletado com sucesso!");
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
		String sql = "SELECT * FROM bolo WHERE id = ?";
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
