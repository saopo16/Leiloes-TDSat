/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.SQLException;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();

        try {
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

            // Obtenha o ID gerado para o novo produto
            ResultSet generatedKeys = prep.getGeneratedKeys();
            if (generatedKeys.next()) {
                produto.setId(generatedKeys.getInt(1));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            // Feche as conexões e recursos
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                // Lidar com exceções, se necessário
            }
        }
    }
public void closeConnections() {
        try {
            if (resultset != null) {
                resultset.close();
            }
            if (prep != null) {
                prep.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   public ArrayList<ProdutosDTO> listarProdutos() {
    try {
        conn = new conectaDAO().connectDB(); // Certifique-se de ajustar conforme necessário

        String query = "SELECT * FROM produtos";
        prep = conn.prepareStatement(query);
        resultset = prep.executeQuery();

        listagem.clear();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            listagem.add(produto);
        }

    } catch (SQLException e) {
        e.printStackTrace(); // Aqui, você pode querer lidar com a exceção de maneira mais apropriada.
    } finally {
        // Certifique-se de fechar as conexões no bloco finally
        // Isso garante que a conexão seja fechada, independentemente de ocorrer uma exceção ou não
        closeConnections();
    }
    

    return listagem;
}
   public void venderProduto(int id) {
    String sql = "UPDATE produtos SET status=? WHERE id =?";

    try {
        conn = new conectaDAO().connectDB(); // Obtenha a conexão antes de executar a atualização
        PreparedStatement prep = conn.prepareStatement(sql);
        prep.setString(1, "VENDIDO");
        prep.setInt(2, id);
        prep.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto Atualizado com sucesso");
    } catch (SQLException ex) {
        System.out.println("Não foi possível atualizar produto" + ex.getMessage());
    } finally {
        // Certifique-se de fechar a conexão no bloco finally
        closeConnections();
    }
}

    
    
    
        
}

