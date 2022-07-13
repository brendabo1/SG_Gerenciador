package application.model.facades;

import java.time.LocalDate;
import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Cliente;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Venda;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;
import application.model.gerenciadores.GerenciamentoVenda;

public class FacadeVendas {
			private GerenciamentoVenda gerVenda;
			
			FacadeVendas(BancoDeDados banco) {
				this.gerVenda = new GerenciamentoVenda(banco);
			}
			
			
		Venda cadastrarVenda(HashMap<String, CarrinhoDeCompra> itens_comprados, FormasDePagamento formaDePagamento, Cliente cliente) {
			Venda venda = this.gerVenda.cadastrar(itens_comprados, formaDePagamento, cliente);
			if (venda == null)
				throw new IllegalArgumentException("Não foi possível cadastrar a venda.");
			
			return venda;
		}
		
		CarrinhoDeCompra criarCarrinhoDeCompra(ItemCardapio item_comprado, Integer quantidade_comprada) {
			CarrinhoDeCompra carrinho = this.gerVenda.criarCarrinhoDeCompra(item_comprado, quantidade_comprada);
			if (carrinho == null)
				throw new IllegalArgumentException("Não foi possível criar um carrinho de compras.");
			
			return carrinho;
		}
		
		void editarFormaDePagamentoDaVenda(FormasDePagamento nova_forma, Venda venda) {
			if (!this.gerVenda.editarFormaDePagamento(nova_forma, venda))
				throw new IllegalArgumentException("Não foi possível editar a forma de pagamento.");
		}
		
		void editarClienteDaVenda(Cliente novo_cliente, Venda venda) {
			if (!this.gerVenda.editarCliente(novo_cliente, venda))
				throw new IllegalArgumentException("Não foi possível editar o cliente da venda.");
		}
		
		void adicionarItemCompradoNaVenda(ItemCardapio item_comprado, Integer quantidade_comprada, Venda venda) {
			if (!this.gerVenda.adicionarItemComprado(item_comprado, quantidade_comprada, venda))
				throw new IllegalArgumentException("Não foi possível adicionar um item na venda.");
		}
		
		void retirarItemCompradoDaVenda(String id_carrinhoCompras, Integer quantidade_retirada,Venda venda) {
			if (!this.gerVenda.removerItemComprado(id_carrinhoCompras, quantidade_retirada, venda))
				throw new IllegalArgumentException("Não foi possível remover o item na venda.");
		}
		
		void excluirVenda(String ID) {
			if (!this.gerVenda.excluir(this.gerVenda.getMap_vendas(), ID))
				throw new IllegalArgumentException("Não foi possível excluir a venda");
		}
		
		void gerarPDFvendasGerais() {
			String texto = this.gerVenda.gerarListaDeVendasNoGeral();
			if (texto == null)
				throw new IllegalArgumentException("Não foi possível gerar relatório de vendas.");
			
			this.gerVenda.gerarPDF("Relatorio Vendas", texto);
		}
		
		void gerarPDFvendasPorPeriodo(LocalDate data_1, LocalDate data_2) {
			String texto = this.gerVenda.gerarListaDeVendasPorPeriodo(data_1, data_2);
			if (texto == null)
				throw new IllegalArgumentException("Não foi possível gerar o relatório de vendas.");
			
			this.gerVenda.gerarPDF("Vendas Por Periodo", texto);
		}
		
		void gerarPDFvendasPorCategoria(CategoriasDeItens categoria) {
			String texto = this.gerVenda.gerarListaDeVendaPorTipoDePrato(categoria);
			if (texto == null)
				throw new IllegalArgumentException("Não foi possível gerar o relatório de vendas.");
			
			this.gerVenda.gerarPDF("Vendas Por Categoria", texto);
		}
		
		void editarDataDeVenda(LocalDate nova_data, Venda venda) {
			if (!this.gerVenda.editarData(nova_data, venda))
				throw new IllegalArgumentException("Não foi possível editar a data.");
		}
}
