package application.model.facades;


import application.model.BancoDeDados;
import application.model.entidades.Fornecedor;
import application.model.entidades.Produto;
import application.model.gerenciadores.GerenciamentoFornecedor;
import application.model.validacoes.ValidaFornecedor;

public class FacadeFornecedor {
		private GerenciamentoFornecedor gerFornecedor;
		private ValidaFornecedor valFornecedor;
		
		FacadeFornecedor (BancoDeDados banco) {
			this.gerFornecedor = new GerenciamentoFornecedor(banco);
			this.valFornecedor = new ValidaFornecedor();
		}
		
	Fornecedor cadastrarFornecedor (String nome, String CNPJ, String endereco) {
		if (!this.valFornecedor.isNomeValido(nome)) 
			throw new IllegalArgumentException("Digite um nome com no m�nimo 4 caracteres.");
		
		if (!this.valFornecedor.isCnpjValido(CNPJ)) 
			throw new IllegalArgumentException("Digite um CNPJ com somente 14 n�meros.");
		
		if (!this.valFornecedor.isEnderecoValido(endereco)) 
			throw new IllegalArgumentException("Digite um endere�o com no m�nimo 4 caracteres.");
		
		if (this.valFornecedor.isFornecedorExistente(CNPJ, this.gerFornecedor.getMap_fornecedores()))
			throw new IllegalArgumentException("CNPJ j� cadastrado.");
		
		Fornecedor novo_fornecedor = this.gerFornecedor.cadastrar(nome, CNPJ, endereco);
		if (novo_fornecedor == null)
			throw new ExceptionInInitializerError("Houve um problema, cadastro n�o realizado. Tente mais tarde.");
		return novo_fornecedor;
	}
	
	void editarNomeFornecedor (String novo_nome, Fornecedor fornecedor) {
		if (!this.valFornecedor.isNomeValido(novo_nome)) 
			throw new IllegalArgumentException("Digite um nome com no m�nimo 4 caracteres.");	
		
		 if (!this.gerFornecedor.editarNome(novo_nome, fornecedor))
			 throw new ExceptionInInitializerError("N�o foi poss�vel editar o nome");
	}
	
	void editarCNPJfornecedor (String novo_cnpj, Fornecedor fornecedor) {
		if (!this.valFornecedor.isCnpjValido(novo_cnpj)) 
			throw new IllegalArgumentException("Digite um CNPJ com somente 14 n�meros.");
		
		if (this.valFornecedor.isFornecedorExistente(novo_cnpj, this.gerFornecedor.getMap_fornecedores()))
			throw new IllegalArgumentException("CNPJ j� cadastrado.");
		
		if (!this.gerFornecedor.editarCNPJ(novo_cnpj, fornecedor))
			throw new ExceptionInInitializerError("N�o foi poss�vel editar o CNPJ.");
	}
	
	void editarEnderecoFornecedor (String novo_endereco, Fornecedor fornecedor) {
		if (!this.valFornecedor.isEnderecoValido(novo_endereco)) 
			throw new IllegalArgumentException("Digite um endere�o com no m�nimo 4 caracteres.");
		
		if (!this.gerFornecedor.editarEndereco(novo_endereco, fornecedor))
			throw new ExceptionInInitializerError("N�o foi poss�vel editar o endere�o.");
	}
	
	void excluirFornecedor(String ID) {
		if (!this.gerFornecedor.excluir(this.gerFornecedor.getMap_fornecedores(), ID))
			throw new ExceptionInInitializerError("N�o foi poss�vel remover o Fornecedor");
	}
	
	void adicionarProdutoEmFornecedor (Produto novo_produto, Fornecedor fornecedor) {
		if (!this.gerFornecedor.adicionarProdutoEmFornecedor(novo_produto, fornecedor))
			throw new ExceptionInInitializerError("N�o foi poss�vel adicionar o produto no fornecedor.");
	}
	
	void removerProdutoDeFornecedor (String id_selecionado, Fornecedor fornecedor) {
		if (!this.gerFornecedor.removerProdutoEmFornecedor(id_selecionado, fornecedor))
			throw new ExceptionInInitializerError("N�o foi poss�vel remover um produto com este ID do fornecedor");
	}
	
	void gerarPDFdeFornecedores() {
		String texto = this.gerFornecedor.gerarStringDeTodosOsFornecedores();
		if (texto == null)
			throw new IllegalArgumentException("N�o foi poss�vel gerar o relat�rio de fornecedores.");
		
		this.gerFornecedor.gerarPDF("Relatorio Fornecedores", texto);
	}
	
	
}
