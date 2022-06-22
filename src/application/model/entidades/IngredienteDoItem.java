package application.model.entidades;

/**
 * Entidade responsavel por compor os Itens do Cardapio.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class IngredienteDoItem extends EntidadesDoSistema{
	private Produto produto;
	private Double quantidade_usada;
	final private static String preFixo = "ING";

	/**
	 * Construtor do ingrediente.
	 * @param produto Entidade produto utilizado.
	 * @param quantidade_usada Quantidade do produto.
	 * @param id Identifica��o do Ingrediente. 
	 */
	 public IngredienteDoItem (Produto produto, Double quantidade_usada, String id) {
		this.id = id;
		this.produto = produto;
		this.quantidade_usada = quantidade_usada;
	}
	 
	 
	@Override
	public String toString() {
		String message = String.format("%10s %10s %6f %5s", produto.getId(), produto.getNome(), quantidade_usada, produto.getUnidade_medida());
		return message;
	}
	

	public String linhaTituloToString() {
		String message = String.format("%10s %10s %3s %6s", "ID", "NOME", "QNT", "MEDIDA");
		return message;
	}

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getQuantidade_usada() {
		return quantidade_usada;
	}
	public void setQuantidade_usada(Double quantidade_usada) {
		this.quantidade_usada = quantidade_usada;
	}


	public static String getPreFixo() {
		return preFixo;
	}
}
