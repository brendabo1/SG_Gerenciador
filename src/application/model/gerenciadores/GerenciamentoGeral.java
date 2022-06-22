package application.model.gerenciadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;

import application.model.entidades.EntidadesDoSistema;

/**
 * Classe pai das demais classes de gerenciamento.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
abstract public class GerenciamentoGeral {
	
	private HashSet<String> id_cadastrados = new HashSet<>();
	
	/**
	 * M�todo para gerar identificacao sem repeticoes.
	 * @param preFixo preFixo especifico da entidade.
	 * @return retorna a nova identificacao.
	 */
	public  String gerarID(String preFixo) {
			Random random = new Random();
			String novo_id;
			
			do {
				novo_id = preFixo;
				for (int i = 0; i < 5; i++) 
						novo_id += random.nextInt(0, 9);
			} while (id_cadastrados.contains(novo_id));
			
			return novo_id;
	}
	
	/**
	 * M�todo para adicionar uma entidade em um HashMap, utilizado sua identificacao como chave.
	 * @param <T> Objeto generico que seja do tipo EntidadesDoSistema.
	 * @param map_entidade HashMap generico das entidades.
	 * @param obj objeto generico.
	 * @return retorna true caso adicione e false caso nao adicione.
	 */
	public <T extends EntidadesDoSistema> boolean adicionar(HashMap<String, T> map_entidade, T obj) {
		T obj_anterior = map_entidade.put(obj.getId(), obj);
		if (obj_anterior == null) {
				id_cadastrados.add(obj.getId());
				return true;
		}
		else {
			map_entidade.put(obj.getId(), obj_anterior);
			return false;
		}
	}
	
	/**
	 * Metodo generico para exclusao de entidades da estrutura de dados HashMap.
	 * @param <T> Definicao generica do objeto do tipo EntidadesDoSistema.
	 * @param map_entidade HashMap generico.
	 * @param ID_buscado Identificacao buscada.
	 * @return retorna true caso remova e false caso nao remova.
	 */
	public <T extends EntidadesDoSistema> boolean excluir(HashMap<String, T> map_entidade, String ID_buscado) {
		T obj_anterior = map_entidade.remove(ID_buscado);
		if (obj_anterior != null) {
			id_cadastrados.remove(ID_buscado);
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo generico para listar as entidades armazenadas.
	 * @param <T> Definicao generica do objeto do tipo EntidadesDoSistema.
	 * @param map_entidade HashMap generico.
	 * @return retorna a String de exibicao ou null caso nao haja entidades.
	 */
	public <T extends EntidadesDoSistema> String listar(HashMap<String, T> map_entidade) {
			if (map_entidade.isEmpty())
					return null;
		
			String message = new String();
			for (T obj : map_entidade.values())
					message += obj.toString();
			
			return message;
	}
	

	/**
	 * Metodo generico para a busca de entidades por meio da identificacao.
	 * @param <T> Definicao generica do objeto do tipo EntidadesDoSistema.
	 * @param map_entidade HashMap generico.
	 * @param id_buscada identificacao selecionada.
	 * @return retorna a entidade ou null caso ela nao esteja armazenada.
	 */
	public <T extends EntidadesDoSistema> EntidadesDoSistema buscarEntidade_ID(HashMap<String, T> map_entidade, String id_buscada) {
		EntidadesDoSistema entidade;
		entidade = map_entidade.get(id_buscada);
		if(entidade == null) throw new NoSuchElementException("ID n�o encontrado");
		return entidade;
	}
	
	
	public <T extends EntidadesDoSistema> ArrayList<T> convertHashToArr(HashMap<String, T> hash){
		Collection<T> colecao = hash.values();
		ArrayList<T> lista = new ArrayList<>(colecao);
		return lista;
	}
}
