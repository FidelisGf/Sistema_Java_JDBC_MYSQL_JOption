package DAO;

import CONNECTION.ConnectionFactory;
import Model.*;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Date;

public class RelatorioDAO {
    private Connection connection;
    public RelatorioDAO() {
        this.connection  = new ConnectionFactory().getConnection();
    }

    public int VerificaLogin(){
        try {
            int id = 0;
            File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\ControleLog\\Logado.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()){
                id = Integer.valueOf(bufferedReader.readLine());
            }
            bufferedReader.close();
            fileReader.close();
            return id;
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
    public void fazerLogAdicionar(Produto produto){
        int id = VerificaLogin();
        System.out.println(id);
        try {
            String sql = "SELECT * FROM funcionarios WHERE funcionarios.IdFuncionario = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            Funcionario funcionario = new Funcionario();
            Date date = new Date();
            while (resultSet.next()){
                funcionario.setNome(resultSet.getString("Nome"));
            }
            statement.close();
            File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\ControleLog\\log.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            try {
                if(!file.exists()){
                    file.createNewFile();
                }
                printWriter.println("USUARIO : " + funcionario.getNome() + " INSERIU : " + produto.getNome() + " Na CATEGORIA : " + produto.getCategoria().getId() + " no HORARIO : " + new Timestamp(date.getTime()));
                printWriter.close();
                fileWriter.close();
            }catch (IOException e){
                throw new RuntimeException();
            }
        }catch (SQLException | IOException e){
            throw new RuntimeException();
        }
    }
    public void fazerlogEditarNomeProduto(Produto produto, String NomePassado){
        int id = VerificaLogin();
        try {
            String sql = "SELECT * FROM funcionarios WHERE funcionarios.IdFuncionario = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            Funcionario funcionario = new Funcionario();
            Date date = new Date();
            while (resultSet.next()){
                funcionario.setNome(resultSet.getString("Nome"));
            }
            statement.close();
            File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\ControleLog\\log.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            if(file.exists()){
                file.createNewFile();
                printWriter.println("USUARIO : " + funcionario.getNome() + "  EDITOU o nome do Produto : " + NomePassado + "  Para : " + produto.getNome() + " Na CATEGORIA : " + produto.getCategoria().getId() + " no HORARIO : " + new Timestamp(date.getTime()));
                printWriter.close();
                fileWriter.close();
            }
        }catch (SQLException | IOException e ){
            throw new RuntimeException();
        }
    }
    public void fazerLogExcluirProduto(Produto produto){
        int id = VerificaLogin();
        try {
            String sql = "SELECT * FROM funcionarios WHERE funcionarios.IdFuncionario = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            Funcionario funcionario = new Funcionario();
            Date date = new Date();
            while (resultSet.next()) {
                funcionario.setNome(resultSet.getString("Nome"));
            }
            statement.close();

            File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\ControleLog\\log.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            if (file.exists()) {
                file.createNewFile();
                printWriter.println("USUARIO : " + funcionario.getNome() + " EXCLUIU : o Produto " + " " + produto.getNome() + " Na CATEGORIA : " + produto.getCategoria().getId() + " no HORARIO : " + new Timestamp(date.getTime()));
                printWriter.close();
                fileWriter.close();
            }
        }catch (SQLException | IOException e){
            throw new RuntimeException();
        }
}
    public String listarAcoes(){
        String linha = "";
        try {
            File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\ControleLog\\log.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if(bufferedReader.ready()){
                do {
                    linha += bufferedReader.readLine() + "\n";
                }while (bufferedReader.ready());
            }
            return linha;
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
    public void logPedidos(Carrinho carrinho, Pagamento pagamento, Avaliacao avaliacao){
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        try {
            //int id = set_N_Pedido();
            int id = set_N_Pedido();
            File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\pedidos.txt");
            FileWriter fileWriter = new FileWriter(file,true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            if(!file.exists()){
                file.createNewFile();
            }
            printWriter.println("Pedido Nº - >  " + id + "  Com os seguintes itens :");
            for(Produto produto : carrinho.getLista_do_carrinho()){
                printWriter.println("Produto - > " + produto.getNome() + " No Valor de - > " + produto.getPreco());
            }
            printWriter.println("Valor Total do Pedido - > " + carrinho.getValor_Total());
            printWriter.println("----------------");
            printWriter.close();
            fileWriter.close();
            carrinhoDAO.Finalizar_Carrinho();
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
    File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\idPedidos.txt");
    public int get_N_Pedido(){
        int id = 0;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if(!file.exists()){
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);
                pw.println("1");
                pw.close();
                fw.close();
            }
            if(bufferedReader.ready()){
                id = Integer.valueOf(bufferedReader.readLine());
            }
            fileReader.close();
        }catch (IOException e){
            throw  new RuntimeException();
        }
        return id;
    }
    public int set_N_Pedido(){
        int id = get_N_Pedido();
        try {
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(id + 1);
            pw.close();
            fw.close();
        }catch (IOException e){
            throw new RuntimeException();
        }
        return id;
    }
    public String listarVendas(){
        String texto = "";
        try {
            File file = new File("C:\\Users\\Fifo\\Desktop\\TotDAo\\pedidos.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if(bufferedReader.ready()){
                do {
                    texto += bufferedReader.readLine() + "\n";
                }while (bufferedReader.ready());
            }
            return texto;
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
}