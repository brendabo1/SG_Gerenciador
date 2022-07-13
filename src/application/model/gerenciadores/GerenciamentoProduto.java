package application.model.gerenciadores;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;
import application.model.entidades.enums.UnidadeMedida;

/**
 * Classe para aplicacao de operacoes na entidade Produto.
 * @author Elmer Carvalho
 * @author Brenda Barbosa
 */
public class GerenciamentoProduto extends GerenciamentoGeral{
	private HashMap<String, Produto> map_produtos;
	private GerenciamentoFornecedor gerFornecedor;

	/**
	 * Construtor do gerenciamento de produto.
	 * @param banco objeto que armazena as estruturas de dados
	 * @param gerFornecedor
	 */
	public GerenciamentoProduto(BancoDeDados banco) {
		this.map_produtos = banco.getMap_produtos();
		this.gerFornecedor = new GerenciamentoFornecedor(banco);
	}
	
	/**
	 * Cadastra um novo produto no sistema
	 * @param nome Nome do produto
	 * @param fornecedor Objeto fornecedor do produto
	 * @param unidade Unidade de Medida correspondente
	 * @param quantidade_conteudo grandeza numerica referente ao conteudo do produto
	 * @return O produto cadastrado ou null caso o cadastro nao seja realizado
	 */
	public Produto cadastrar(String nome, Fornecedor fornecedor, UnidadeMedida unidade, Double quantidade_conteudo) {
		String novo_id = gerarID(Produto.getPreFixo());
		Produto novo_produto = new Produto(novo_id, nome,  unidade, quantidade_conteudo);
		if (adicionar(map_produtos, novo_produto) && gerFornecedor.adicionarProdutoEmFornecedor(novo_produto, fornecedor))
			return novo_produto;
		return null;
	}
	/**
	 * Edita o nome do produto cadastrado
	 * @param novo_nome Novo nome vinculado ao prosuto
	 * @param produto Objeto produto a ser editado
	 * @return
	 */
	public boolean editarNome(String novo_nome, Produto produto) {
		produto.setNome(novo_nome);
		return produto.getNome().equals(novo_nome);
	}

	/**
	 * Edita a unidade de medida vinculado ao produto
	 * @param nova_unidade Nova unidade de medida a ser vinculada ao produto
	 * @param produto Produto a ser editado
	 * @return
	 */
	public boolean editarUnidadeMedida(UnidadeMedida nova_unidade, Produto produto) {
		produto.setUnidade_medida(nova_unidade);
		return produto.getUnidade_medida().equals(nova_unidade);
	}
	/**
	 * Edita a quantidade numerica do conteudo vinculado ao produto
	 * @param nova_quantidadeConteudo Nova quantidade a ser vinculada ao produto
	 * @param produto Produto a ser editado
	 * @return
	 */
	public boolean editarQuantidadeConteudo (Double nova_quantidadeConteudo, Produto produto) {
		produto.setConteudo_produto(nova_quantidadeConteudo);
		return produto.getConteudo_produto().equals(nova_quantidadeConteudo);
	}
	
	public HashMap<String, Produto> getMap_produtos() {
		return map_produtos;
	}

	public void setMap_produtos(HashMap<String, Produto> map_produtos) {
		this.map_produtos = map_produtos;
	}
}
