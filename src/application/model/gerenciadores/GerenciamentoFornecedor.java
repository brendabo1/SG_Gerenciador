package application.model.gerenciadores;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;

/**
 * Classe para aplicacao de operacoes sobre o fornecedor.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class GerenciamentoFornecedor extends GerenciamentoGeral {
		private HashMap<String, Fornecedor> map_fornecedores;
	
		/**
		 * Construtor do gerenciamento de fornecedor.
		 * @param banco classe que armazena as estruturas de dados essenciais.
		 */
		public GerenciamentoFornecedor(BancoDeDados banco) {
			this.map_fornecedores = banco.getMap_fornecedores();
		}
		
		/**
		 * Método para cadastro de fornecedor.
		 * @param nome nome do fornecedor.
		 * @param CNPJ cnpj do fornecedor
		 * @param endereco endereco do fornecedor.
		 * @return retorna o fornecedor caso o cadastre e null caso nao possa cadastrar.
		 */
		public Fornecedor cadastrar(String nome, String CNPJ, String endereco) {
			String novo_id = gerarID(Fornecedor.getPreFixo());
			Fornecedor novo_fornecedor = new Fornecedor(novo_id, CNPJ, nome, endereco);
			if (adicionar(map_fornecedores, novo_fornecedor))
				return novo_fornecedor;
			return null;
		}
						
		/**
		 * Método para editar nome do fornecedor.
		 * @param novo_nome novo nome do fornecedor.
		 * @param fornecedor fornecedor que sera alterado.
		 * @return retorna true caso altere e false caso nao altere.
		 */
		public boolean editarNome(String novo_nome, Fornecedor fornecedor) {
			fornecedor.setNome(novo_nome);
			return fornecedor.getNome().equals(novo_nome);
		}
		
		/**
		 * Método para editar cnpj de fornecedor.
		 * @param novo_CNPJ novo cnpj de fornecedor.
		 * @param fornecedor fornecedor que sera alterado.
		 * @return retorna true caso altere e false caso nao altere.
		 */
		public boolean editarCNPJ(String novo_CNPJ, Fornecedor fornecedor) {
			fornecedor.setCNPJ(novo_CNPJ);
			return fornecedor.getCNPJ().equals(novo_CNPJ);
		}
		
		/**
		 * Método para editar o endereco do fornecedor.
		 * @param novo_endereco novo endereco do fornecedor.
		 * @param fornecedor fornecedor que sera alterado.
		 * @return retorna true caso altere e false caso nao altere.
		 */
		public boolean editarEndereco(String novo_endereco, Fornecedor fornecedor) {
			fornecedor.setEndereco(novo_endereco);
			return fornecedor.getEndereco().equals(novo_endereco);
		}
		
		/**
		 * Método para adicionar um produto na lista de produtos fornecidos.
		 * @param novo_produto novo produto que sera fornecido.
		 * @param fornecedor fornecedor que recebera o novo produto.
		 * @return retorna true caso altere e false caso nao altere.
		 */
		public boolean adicionarProdutoEmFornecedor (Produto novo_produto, Fornecedor fornecedor) {
			fornecedor.getMap_produtosFornecidos().put(novo_produto.getId(), novo_produto);
			return (fornecedor.getMap_produtosFornecidos().get(novo_produto.getId()).equals(novo_produto));
		}
		
		/**
		 * Método para remover um produto da lista de produtos fornecidos.
		 * @param id_selecionado identificacao do produto selecionado.
		 * @param fornecedor fornecedor que sera alterado.
		 * @return returna true caso remova e  false caso nao remova.
		 */
		public boolean removerProdutoEmFornecedor (String id_selecionado, Fornecedor fornecedor) {
			Produto produto_removido = fornecedor.getMap_produtosFornecidos().remove(id_selecionado);
			return (produto_removido != null);
		}

		public HashMap<String, Fornecedor> getMap_fornecedores() {
			return map_fornecedores;
		}
		
		public String gerarStringDeTodosOsFornecedores() {
			String texto = new String();
			
			texto += "Quantidade de Fornecedores: " + this.map_fornecedores.values().size() + "\n";
			for (Fornecedor fornecedor : this.map_fornecedores.values())
				texto += fornecedor.toString() + "\n";
			
			if (texto.isEmpty())
				return null;
			
			return texto;
		}
}
