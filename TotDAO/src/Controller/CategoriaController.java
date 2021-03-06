package Controller;

import DAO.CategoriaDAO;
import Model.Categoria;

import java.util.List;

public class CategoriaController {
    public void InsereCategoria(Categoria categoria, int id){
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaDAO.InsereCategoria(categoria, id);
    }
    public List<Categoria> listartodos(int id){
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        return categoriaDAO.listartodos(id);
    }
    public int escolherCategoria(int id){
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        return categoriaDAO.escolhe_Categoria(id);
    }
    public void excluirCategoria(int op){
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaDAO.excluirCategoria(op);
    }
    public boolean verificarNome(String nome){
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        return categoriaDAO.VerificarNome(nome);
    }
    public Categoria verCategoria(int idUnidade, int IdCategoria){
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        return  categoriaDAO.verCategoria(idUnidade, IdCategoria);
    }
}
