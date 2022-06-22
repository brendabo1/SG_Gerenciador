package application.model;

import java.util.ArrayList;
import java.util.HashMap;

import application.model.entidades.Fornecedor;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.entidades.Usuario;
import application.model.entidades.Venda;

/**
 *  Classe respons�vel por armazenar todas as estruturas de dados e suas respectivas informacoes a serem manipuladas
 * @author Brenda Babrosa
 * @author Elmer Cravalho
 */
public class BancoDeDados {
	
		private HashMap<String, Produto> map_produtos = new HashMap<>();
		private HashMap<String, ItemCardapio> map_itensCardapio = new HashMap<>();
		private HashMap<String, Fornecedor> map_fornecedores = new HashMap<>();
		private HashMap<String, Venda> map_vendas = new HashMap<>();
		private HashMap<String, Usuario> map_usuarios = new HashMap<>();
		private HashMap<String, Lote> map_estoque = new HashMap<>();
		private HashMap<String, ArrayList<String>> agrupamentoDeLotes = new HashMap<>();

		
	
	public HashMap<String, Produto> getMap_produtos() {
		return map_produtos;
	}
	public void setMap_produtos(HashMap<String, Produto> map_produtos) {
		this.map_produtos = map_produtos;
	}
	
	
	public HashMap<String, ItemCardapio> getMap_itensCardapio() {
		return map_itensCardapio;
	}
	public void setMap_itensCardapio(HashMap<String, ItemCardapio> map_itensCardapio) {
		this.map_itensCardapio = map_itensCardapio;
	}
	
	
	public HashMap<String, Fornecedor> getMap_fornecedores() {
		return map_fornecedores;
	}
	public void setMap_fornecedores(HashMap<String, Fornecedor> map_fornecedores) {
		this.map_fornecedores = map_fornecedores;
	}
	
	
	public HashMap<String, Venda> getMap_vendas() {
		return map_vendas;
	}
	public void setMap_vendas(HashMap<String, Venda> map_vendas) {
		this.map_vendas = map_vendas;
	}
	
	
	public HashMap<String, Usuario> getMap_usuarios() {
		return map_usuarios;
	}
	public void setMap_usuarios(HashMap<String, Usuario> map_usuarios) {
		this.map_usuarios = map_usuarios;
	}
	
	
	public HashMap<String, Lote> getMap_estoque() {
		return map_estoque;
	}
	public void setMap_estoque(HashMap<String, Lote> map_estoque) {
		this.map_estoque = map_estoque;
	}
	
	
	public HashMap<String, ArrayList<String>> getAgrupamentoDeLotes() {
		return agrupamentoDeLotes;
	}
	public void setAgrupamentoDeLotes(HashMap<String, ArrayList<String>> agrupamentoDeLotes) {
		this.agrupamentoDeLotes = agrupamentoDeLotes;
	}
}
