package DAO;

import DTO.ClienteDTO;
import VIEWS.TelaCliente;
import VIEWS.TelaUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteDAO {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public void inserirUsuario(ClienteDTO objClienteDTO) {
        String sql = "inserir into tb_Cliente(nome_cliente, cpf_cliente, fone_cliente, email_cliente)"
                + "values (?, ?, ?, ?, ?)";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, objClienteDTO.getNome_cliente());
            pst.setString(2, objClienteDTO.getCpf_cliente());
            pst.setString(3, objClienteDTO.getFone_cliente());
            pst.setString(4, objClienteDTO.getEmail_cliente());
            int add = pst.executeUpdate();
            if (add > 0) {
                pesquisarAuto();

                pst.close();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "método Inserir" + e);
        }
    }

    public void pesquisar(ClienteDTO objClienteDTO) {
        String sql = "select * from tb_Cliente where id_Cliente =?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objClienteDTO.getId_cliente());
            rs = pst.executeQuery();

            if (rs.next()) {
                TelaCliente.txtNome.setText(rs.getString(2));
                TelaCliente.txtCpf.setText(rs.getString(3));
                TelaCliente.txtFone.setText(rs.getString(4));
                TelaCliente.txtEmail.setText(rs.getString(5));
                conexao.close();

            } else {
                JOptionPane.showMessageDialog(null, "Usuario nao cadastrado!");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método Pesquisar" + e);

        }
    }

    public void editar(ClienteDTO objClienteDTO) {
        String sql = "update  tb_cliente set nome_cliente=?, fone_cliente=?, email_cliente=? where id_cliente=?";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(5, objClienteDTO.getId_cliente());
            pst.setString(1, objClienteDTO.getNome_cliente());
            pst.setString(2, objClienteDTO.getCpf_cliente());
            pst.setString(3, objClienteDTO.getCpf_cliente());
            pst.setString(4, objClienteDTO.getEmail_cliente());

            int add = pst.executeUpdate();
            if (add > 0) {
                JOptionPane.showMessageDialog(null, "Usuario editado com sucesso!");
                pesquisarAuto();
                conexao.close();
                limparCampos();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Método editar" + e);

        }
    }

    public void pesquisarAuto() {
        String sql = "select * from tb_usuarios";
        conexao = ConexaoDAO.conector();

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) TelaUsuario.TbUsuarios.getModel();
            model.setNumRows(0);

            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("usuario");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                String perfil = rs.getString("perfil");
                model.addRow(new Object[]{id, nome, login, senha, perfil});
            }
            conexao.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método Pesquisar Automático " + e);
        }
    }

    public void limparCampos() {
        TelaCliente.txtIdCliente.setText(null);
        TelaCliente.txtCpf.setText(null);
        TelaCliente.txtFone.setText(null);
        TelaCliente.txtNome.setText(null);
        TelaCliente.txtEmail.setText(null);

    }

}
