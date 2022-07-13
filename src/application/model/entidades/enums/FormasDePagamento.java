package application.model.entidades.enums;

public enum FormasDePagamento {
	PIX("Pix"),
	CREDITO("Credito"),
	DEBITO("Debito"),
	DINHEIRO("Dinheiro");
		
	private String descricao;
	
	FormasDePagamento(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
	    return this.descricao;
	}
}
