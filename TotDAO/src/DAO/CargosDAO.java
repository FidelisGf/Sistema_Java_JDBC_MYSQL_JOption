package DAO;

import CONNECTION.ConnectionFactory;
import Model.Cargos;
import Model.Categoria;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CargosDAO {
    private Connection connection;
    public CargosDAO() {
        this.connection  = new ConnectionFactory().getConnection();
        criarTabelaCargos();
    }
    public void criarTabelaCargos(){
        try {
            String sql = "CREATE TABLE IF not exists cargos " +
                    "(IdCargo BIGINT not NULL AUTO_INCREMENT, " +
                    " PRIMARY KEY (IdCargo) , " +
                    " NomeCargo VARCHAR(255), " +
                    " Salario FLOAT , " +
                    " IdEmpresa BIGINT , " +
                    " DATA TIMESTAMP , " +
                    " FOREIGN KEY (IdEmpresa) REFERENCES empresa(IDEmpresa))" ;
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        }catch (SQLException e){
            throw  new RuntimeException();
        }
    }
    public void InserirCargo(Cargos cargos, int idEmpresa){
        try {
            System.out.println(cargos);
            String sql = "INSERT INTO cargos" + "(NomeCargo, Salario, IdEmpresa)" + "VALUES(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, cargos.getNomeCargo());
            statement.setFloat(2, cargos.getSalarioDoCargo());
            statement.setInt(3, idEmpresa);
            statement.execute();
            int id2 = 0;
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                id2 = resultSet.getInt(1);
            }
            JOptionPane.showMessageDialog(null, "Novo cargo Registrado no Id - >  " + id2);
            statement.close();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
    public List<Cargos> ListarTodas(int id){
        try {
            List<Cargos> list = new ArrayList<>();
            String sql = "SELECT * FROM cargos WHERE  cargos.IdEmpresa = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            Cargos cargos;
            while (resultSet.next()){
                cargos = new Cargos(resultSet.getInt("IdCargo"), resultSet.getString("NomeCargo"), resultSet.getFloat("Salario"));
                list.add(cargos);
            }
            return list;
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

}
