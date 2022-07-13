package application.model.entidades;

import java.util.Objects;

import application.model.entidades.enums.UnidadeMedida;

/**
 * Entidade referente ao produto fornecido pela entidade fornecedor.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class Produto extends EntidadesDoSistema {		
	private String nome;
	private UnidadeMedida unidade_medida;
	private double conteudo_produto;
	final private static String preFixo = "PRO";		

	/**
	 * Construtor da entidade produto.
	 * @param id identificacao do produto.
	 * @param nome nome do produto.
	 * @param fornecedor fornecedor do produto.
	 * @param unidade unidade de medida do produto.
	 * @param conteudo_produto grandeza numerica referente a unidade de medida do produto.
	 */
	public Produto(String id, String nome, UnidadeMedida unidade, Double conteudo_produto) {
		this.id = id;
		this.nome = nome;
		this.unidade_medida = unidade;
		this.conteudo_produto = conteudo_produto;
	}

	
	@Override
	public String toString() {
		String message = String.format("\n%s %6s %10s %s", this.id, this.nome, this.conteudo_produto, this.unidade_medida.toString());
		return message;
	}


	public String linhaTituloToString() {
		String message = String.format("\n%2s %19s %15s", "ID", "NOME", "FORNECEDOR");
		return message;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(conteudo_produto, other.conteudo_produto)
			    && Objects.equals(nome, other.nome)
				&& unidade_medida == other.unidade_medida;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public UnidadeMedida getUnidade_medida() {
		return unidade_medida;
	}
	public void setUnidade_medida(UnidadeMedida unidade_medida) {
		this.unidade_medida = unidade_medida;
	}


	public Double getConteudo_produto() {
		return conteudo_produto;
	}
	public void setConteudo_produto(Double conteudo_produto) {
		this.conteudo_produto = conteudo_produto;
	}


	public static String getPreFixo() {
		return preFixo;
	}
}
