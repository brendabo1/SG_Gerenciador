package application.model.facades;

import java.time.LocalDate;
import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Cliente;
import application.model.entidades.Fornecedor;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Lote;
import application.model.entidades.Produto;
import application.model.entidades.Usuario;
import application.model.entidades.Venda;
import application.model.entidades.enums.Cargo;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;
import application.model.entidades.enums.UnidadeMedida;


public class FacadePrincipal {
		private FacadeFornecedor facFornecedor;
		private FacadeUsuario facUsuario;
		private FacadeProduto facProduto;
		private FacadeCliente facCliente;
		private FacadeItemCardapio facItem;	
		private FacadeLote facLote;
		private FacadeVendas facVenda;
				
		public FacadePrincipal (BancoDeDados banco) {
			this.facFornecedor = new FacadeFornecedor(banco);
			this.facUsuario = new FacadeUsuario(banco);
			this.facProduto = new FacadeProduto(banco);
			this.facCliente = new FacadeCliente(banco);
			this.facItem = new FacadeItemCardapio(banco);
			this.facLote = new FacadeLote(banco);
			this.facVenda = new FacadeVendas(banco);
		}
	
						
		public FacadeFornecedor getFacFornecedor() {
			return facFornecedor;
		}

		public void setFacFornecedor(FacadeFornecedor facFornecedor) {
			this.facFornecedor = facFornecedor;
		}

		public FacadeUsuario getFacUsuario() {
			return facUsuario;
		}

		public void setFacUsuario(FacadeUsuario facUsuario) {
			this.facUsuario = facUsuario;
		}

		public Fornecedor cadastrarFornecedor (String nome, String CNPJ, String endereco) {
			return this.facFornecedor.cadastrarFornecedor(nome, CNPJ, endereco);
		}
		
		public void editarNomeFornecedor (String novo_nome, Fornecedor fornecedor) {
			this.facFornecedor.editarNomeFornecedor(novo_nome, fornecedor);
		}
		
		public void editarCNPJfornecedor (String novo_CNPJ, Fornecedor fornecedor) {
			this.facFornecedor.editarCNPJfornecedor(novo_CNPJ, fornecedor);
		}
		
		public void editarEnderecoFornecedor (String novo_endereco, Fornecedor fornecedor) {
			this.facFornecedor.editarEnderecoFornecedor(novo_endereco, fornecedor);
		}
		
		public void excluirFornecedor (String ID) {
			this.facFornecedor.excluirFornecedor(ID);
		}
		
		public void adicionarProdutoNoFornecedor (Produto novo_produto, Fornecedor fornecedor) {
			this.facFornecedor.adicionarProdutoEmFornecedor(novo_produto, fornecedor);
		}
		
		public void removerProdutoNoFornecedor (String id_selecionado, Fornecedor fornecedor) {
			this.facFornecedor.removerProdutoDeFornecedor(id_selecionado, fornecedor);
		}
		
		
		public Usuario cadastrarUsuario (String nome, String senha, Cargo cargo) {
			return this.facUsuario.cadastrarUsuario(nome, senha, cargo);
		}
		
		public void editarNomeUsuario (String novo_nome, Usuario usuario) {
			this.facUsuario.editarNomeUsuario(novo_nome, usuario);
		}
		
		public void editarSenhaUsuario (String novo_senha, Usuario usuario) {
			this.facUsuario.editarSenhaUsuario(novo_senha, usuario);
		}
		
		public void editarCargoUsuario (Cargo novo_cargo, Usuario usuario) {
			this.facUsuario.editarCargoUsuario(novo_cargo, usuario);
		}
		
		public void loginUsuario (String ID, String senha) {
			this.facUsuario.loginUsuario(ID, senha);
		}
		
		public void excluirUsuario (String ID) {
			this.facUsuario.excluirUsuario(ID);
		}
		
		public Produto cadastrarProduto (String nome, Fornecedor fornecedor, UnidadeMedida uniMedida, Double conteudo) {
			return this.facProduto.cadastrarProduto(nome, fornecedor, uniMedida, conteudo);
		}
		
		public void editarNomeProduto (String novo_nome, Produto produto) {
			this.facProduto.editarNomeProduto(novo_nome, produto);
		}
		
		
		public void editarConteudoProduto (Double novo_conteudo, Produto produto) {
			this.facProduto.editarConteudoDoProduto(novo_conteudo, produto);
		}
		
		public void editarUnidadeMedidaDoProduto (UnidadeMedida nova_medida, Produto produto) {
			this.facProduto.editarUnidadeDeMedidaDoProduto(nova_medida, produto);
		}
		
		public void excluirProduto (String ID) {
			this.facProduto.excluirProduto(ID);
		}
		
		public Cliente cadastrarCliente(String nome, String CPF, String email, String telefone) {
			return this.facCliente.cadastrarCliente(nome, CPF, email, telefone);
		}
		
		public void editarNomeCliente(String novo_nome, Cliente cliente) {
			this.facCliente.editarNomeCliente(novo_nome, cliente);
		}
		
		public void editarCPFcliente(String novo_cpf, Cliente cliente) {
			this.facCliente.editarCPFcliente(novo_cpf, cliente);
		}
		
		public void editarEmailCliente(String novo_email, Cliente cliente) {
			this.facCliente.editarEmailCliente(novo_email, cliente);
		}
		
		public void editarTelefoneCliente(String novo_telefone, Cliente cliente) {
			this.facCliente.editarTelefoneCliente(novo_telefone, cliente);
		}
		
		public void excluirCliente(String ID) {
			this.facCliente.excluirCliente(ID);
		}
		
		public ItemCardapio cadastrarItemDoCardapio (String nome, HashMap<String, IngredienteDoItem> ingredientes, Double preco, CategoriasDeItens categoria) {
			return this.facItem.cadastrarItemCardapio(nome, ingredientes, preco, categoria);
		}
		
		public IngredienteDoItem criarIngredienteDoItem (Produto produto, Double quantidade) {
			return this.facItem.criarIngrediente(produto, quantidade);
		}
		
		public void editarNomeDoItem(String novo_nome, ItemCardapio item) {
			this.facItem.editarNomeItem(novo_nome, item);
		}
		
		public void editarPrecoDoItem(Double novo_preco, ItemCardapio item) {
			this.facItem.editarPrecoItem(novo_preco, item);
		}
		
		public void editarCategoriaDoItem(CategoriasDeItens nova_categoria, ItemCardapio item) {
			this.facItem.editarCategoriaDoItem(nova_categoria, item);
		}
		
		public void editarProdutoDoIngrediente(Produto novo_produto, String ID_Ingrediente, ItemCardapio item) {
			this.facItem.editarProdutoDoIngrediente(novo_produto, ID_Ingrediente, item);
		}
		
		public void editarQuantidadeDoIngrediente(Double nova_quantidade, String ID_Ingrediente, ItemCardapio item) {
			this.facItem.editarQuantidadeDoIngrediente(nova_quantidade, ID_Ingrediente, item);
		}
		
		public void adicionarIngredienteNoItem(IngredienteDoItem novo_ingrediente, ItemCardapio item) {
			this.facItem.adicionarIngredienteNoItem(novo_ingrediente, item);
		}
		
		public void removerIngredienteNoItem(String ID_Ingrediente, ItemCardapio item) {
			this.facItem.removerIngredienteNoItem(ID_Ingrediente, item);
		}
		
		public void excluirItemDoCardapio(String ID) {
			this.facItem.excluirItemDoCardapio(ID);
		}
		
		public Lote cadastrarLote(Produto produto, Double quantidade_comprada, Double preco_unitario, LocalDate validade) {
			return this.facLote.cadastrarLote(produto, quantidade_comprada, preco_unitario, validade);
		}
		
		public void editarProdutoDoLote(Produto novo_produto, Lote lote) {
			this.facLote.editarProdutoDoLote(novo_produto, lote);
		}
		
		public void editarQuantidadeDoLote(Double nova_quantidade, Lote lote) {
			this.facLote.editarQuantidadeCompradaNoLote(nova_quantidade, lote);
		}
		
		public void editarPrecoUnitarioDoLote(Double novo_preco, Lote lote) {
			this.facLote.editarPrecoUnitarioDoLote(novo_preco, lote);
		}
		
		public void editarValidadeDoLote(LocalDate nova_validade, Lote lote) {
			this.facLote.editarValidadeDoLote(nova_validade, lote);
		}
		
		public void excluirLote(String ID) {
			this.facLote.excluirLote(ID);
		}
		
		public Venda cadastrarVenda(HashMap<String, CarrinhoDeCompra> itens_comprados, FormasDePagamento formaDePagamento, Cliente cliente) {
			return this.facVenda.cadastrarVenda(itens_comprados, formaDePagamento, cliente);
		}
		
		public CarrinhoDeCompra criarCarrinhoDeCompras(ItemCardapio item_comprado, Integer quantidade_comprada) {
			return this.facVenda.criarCarrinhoDeCompra(item_comprado, quantidade_comprada);
		}
		
		public void editarFormaDePagamentoDaVenda(FormasDePagamento nova_forma, Venda venda) {
			this.facVenda.editarFormaDePagamentoDaVenda(nova_forma, venda);
		}
		
		public void editarClienteDaVenda(Cliente novo_cliente, Venda venda) {
			this.facVenda.editarClienteDaVenda(novo_cliente, venda);
		}
		
		public void adicionarItemCompradoNaVenda(ItemCardapio item_comprado, Integer quantidade_comprada, Venda venda) {
			this.facVenda.adicionarItemCompradoNaVenda(item_comprado, quantidade_comprada, venda);
		}
		
		public void removerItemCompradoNaVenda(String id_carrinhoCompras, Integer quantidade_retirada,Venda venda) {
			this.facVenda.retirarItemCompradoDaVenda(id_carrinhoCompras, quantidade_retirada, venda);
		}
		
		public void excluirVenda(String ID) {
			this.facVenda.excluirVenda(ID);
		}
		
		public void gerarPDFdeVendasGerais() {
			this.facVenda.gerarPDFvendasGerais();
		}
		
		public void gerarPDFdeVendasPorPeriodo(LocalDate data_1, LocalDate data_2) {
			this.facVenda.gerarPDFvendasPorPeriodo(data_1, data_2);
		}
		
		public void gerarPDFdeVendasPorCategoria(CategoriasDeItens categoria) {
			this.facVenda.gerarPDFvendasPorCategoria(categoria);
		}
		
		public void editarDataDeVenda(LocalDate nova_data, Venda venda) {
			this.facVenda.editarDataDeVenda(nova_data, venda);
		}
		
		public void gerarPDFdeEstoque() {
			this.facLote.gerarPDFdeEstoque();
		}
		
		public void gerarPDFdeFornecedores() {
			this.facFornecedor.gerarPDFdeFornecedores();
		}
		
}
