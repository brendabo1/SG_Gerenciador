package application.model.gerenciadores;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
import application.model.entidades.Cliente;
import application.model.entidades.IngredienteDoItem;
import application.model.entidades.ItemCardapio;
import application.model.entidades.Lote;
import application.model.entidades.Venda;
import application.model.entidades.enums.CategoriasDeItens;
import application.model.entidades.enums.FormasDePagamento;

/**
 * Classe para aplicacao das opercoes de venda no sistema
 * @author Brenda Barbosa
 * @author Elmer Carvalho
 */

public class GerenciamentoVenda extends GerenciamentoGeral {
	private HashMap<String, Venda> map_vendas;
	private GerenciamentoItemCardapio gerItemCardapio;
	private GerenciamentoLote gerLote;
	
	/**
	 * Cria o gerenciaciador das vendas
	 * @param banco Estrutura responsável por armazenar os dados
	 * @param gerItemCardapio classe responsavel por gerenciar os itens do cardapio do estabeleciemnto
	 * @param gerLote classe responsavel por gerenciar os lotes de produtos do estabeleciemnto
	 */
	public GerenciamentoVenda(BancoDeDados banco) {
		this.map_vendas = banco.getMap_vendas();
		this.gerItemCardapio = new GerenciamentoItemCardapio(banco);
		this.gerLote = new GerenciamentoLote(banco);
	}
	
	/**
	 * Cadastra uma venda com todos os requisitos válidos
	 * @param itens_comprados Estrura contendo os itens comprados
	 * @param formaDePagamento Forma de pagamento da venda
	 * @return venda se o cadstro for bem sucedido e null se não cadastrar.
	 */
	public Venda cadastrar(HashMap<String, CarrinhoDeCompra> itens_comprados, FormasDePagamento formaDePagamento, Cliente cliente) {
		String nova_id = gerarID(Venda.getPreFixo());
		Venda nova_venda = new Venda(nova_id, itens_comprados, formaDePagamento, cliente);
		if (adicionar(this.map_vendas, nova_venda))
			return nova_venda;
		return null;
	}
	/**
	 * Cria um carrinho de compra contendo para os dados do item comprado
	 * @param item_comprado Item do cardapio escolhido
	 * @param quantidade_comprada quantidade do item a ser comprada
	 * @return um novo carrinho de compra
	 */
	public CarrinhoDeCompra criarCarrinhoDeCompra (ItemCardapio item_comprado, Integer quantidade_comprada) {
			String novo_id = gerarID(CarrinhoDeCompra.getPreFixo());
			return new CarrinhoDeCompra(novo_id, item_comprado, quantidade_comprada);
	}
	
	/**
	 * Edita a forma de pagamento da venda
	 * @param formaDePagamento Nova forma de pagamento escolhida
	 * @param venda Venda a ser editada
	 * @return true se editar e false caso a venda não seja editada
	 */
	public boolean editarFormaDePagamento (FormasDePagamento formaDePagamento, Venda venda) {
		venda.setForma_de_pagamento(formaDePagamento);
		return venda.getForma_de_pagamento().equals(formaDePagamento);
	}
	
	public boolean editarCliente(Cliente novo_cliente, Venda venda) {
		venda.setCliente(novo_cliente);
		return venda.getCliente().equals(novo_cliente);
	}
	
	/**
	 * Adiciona um Item comprado a venda
	 * @param item_comprado Item do cardapio escolhido para edicao
	 * @param quantidade_comprada Quantidade do item
	 * @param venda Venda a ser editada
	 * @return true se adicionado ou false caso não seja adicionado
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 */
	public boolean adicionarItemComprado (ItemCardapio item_comprado, Integer quantidade_comprada, Venda venda) throws NoSuchElementException, IllegalArgumentException{
		if (!gerItemCardapio.verificarSePodeSerVendido(item_comprado, quantidade_comprada))
				return false;
		
		for (IngredienteDoItem ingrediente :  item_comprado.getIngredientes().values()) {
			String nome_produto = ingrediente.getProduto().getNome().toLowerCase();
			gerLote.consumirLote(ingrediente.getQuantidade_usada() * quantidade_comprada, nome_produto);
		}
		
		for (CarrinhoDeCompra compra : venda.getItens_comprados().values()) {
			if (compra.getItem_comprado().getId() == item_comprado.getId()) {
				compra.setQuantidade_comprada(compra.getQuantidade_comprada() + quantidade_comprada);
				return true;
			}
		}
		
		String novo_id = gerarID(CarrinhoDeCompra.getPreFixo());
		CarrinhoDeCompra nova_compra = new CarrinhoDeCompra(novo_id, item_comprado, quantidade_comprada);
		venda.getItens_comprados().put(novo_id, nova_compra);
		return true;
	}
	
	/**
	 * Remove um Item comprado da venda
	 * @param id_carrinhoCompras Identificacao do carrinho de compras a ser editado
	 * @param quantidade_retirada Qauntidade a ser removida
	 * @param venda Venda que sera editada
	 * @return true se editado ou false se nao editado
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 */
	public boolean removerItemComprado (String id_carrinhoCompras, Integer quantidade_retirada,Venda venda) throws NoSuchElementException, IllegalArgumentException{
		CarrinhoDeCompra compra = venda.getItens_comprados().get(id_carrinhoCompras);
		
		if (compra == null || quantidade_retirada <= 0 || quantidade_retirada > compra.getQuantidade_comprada())
				return false;

		
		HashMap<String, IngredienteDoItem> ingredientes_usados = compra.getItem_comprado().getIngredientes();
		for (IngredienteDoItem ingrediente : ingredientes_usados.values()) {
			String nome_produto = ingrediente.getProduto().getNome().toLowerCase();
			String id_lote = gerLote.getAgrupamentoDeLotes().get(nome_produto).get(0);
			Lote lote = gerLote.getMap_estoque().get(id_lote); 
			
			Double valor_devolvido = ingrediente.getQuantidade_usada() * quantidade_retirada;
			lote.setQuantidade_em_armazenamento(lote.getQuantidade_em_armazenamento() + valor_devolvido);
		}
		compra.setQuantidade_comprada(compra.getQuantidade_comprada() - quantidade_retirada);
		if (compra.getQuantidade_comprada() == 0)
			venda.getItens_comprados().remove(compra.getId());
		return true;
	}
	
	
	 
		
	
	//Vai te dar uma STRING de TODAS as VENDAS
	public String gerarListaDeVendasNoGeral() {
		if (this.map_vendas.isEmpty())
			return null;
		
		String texto = new String();
		for (Venda venda : this.map_vendas.values())
			texto += venda.toString() + "\n";
		
		return texto;
	}
	
	/**
	 * Gera um arquivo PDF contendo as vendas realizadas com a categoria do cardapio escolhida 
	 * @param categoria Categoria de itens do cardapio escolhida
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	
	public String gerarListaDeVendaPorTipoDePrato(CategoriasDeItens categoria_itens) {
		if (this.map_vendas.isEmpty())
			return null;
		
		String texto = new String();
		for (Venda venda : this.map_vendas.values()) {
			for (CarrinhoDeCompra carrinho : venda.getItens_comprados().values()) {
				if (carrinho.getItem_comprado().getCategoria().equals(categoria_itens))
					texto += carrinho.toString() + "\n";
			}
		}
		
		if (texto.isEmpty())
			return null;
			
		return texto;
	}
	
	public boolean editarData(LocalDate nova_data, Venda venda) {
		venda.setData(nova_data);
		return venda.getData().isEqual(nova_data);
	}
	
	
	
	
	/**
	 * Gera um arquivo PDFcontendo a lista de todas as vendas realizadas
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	/*
	public void gerarPDFVendaGeral() throws FileNotFoundException, DocumentException {
		ArrayList<Venda> listaVendasGeral = this.convertHashToArr(this.map_vendas);
		this.gerarPDF("VENDAS", listaVendasGeral);
		
	 }*/
	
	
	
	
	
	 /**
	  * Cria uma lista contendo as vendas por categoria de item do cardapio
	  * @param categoria Categoria do cardapio escolhida
	  * @return Lista contendo as vendas contendo a categoria selecionada
	  */
	 
	
	
	 /*
	public void gerarPDFCategoriaPrato(CategoriasDeItens categoria) throws FileNotFoundException, DocumentException {
			ArrayList<Venda> vendasCategoria = this.listaVendasporCategoria(categoria);
			String titulo = "Vendas no Mês: " + categoria;
			this.gerarPDF(titulo, vendasCategoria);
	}
*/

	public HashMap<String, Venda> getMap_vendas() {
		return map_vendas;
	}

	 
	public String gerarListaDeVendasPorPeriodo (LocalDate data_1, LocalDate data_2) {
		LocalDate data_menor, data_maior;
		
		if (data_1.isBefore(data_2)) {
			data_menor = data_1;
			data_maior = data_2;
		} else if (data_1.isAfter(data_2)) {
			data_menor = data_2;
			data_maior = data_1;
		} else 
			data_menor = data_maior = null;
		
		List<Venda> vendas_no_periodo = new ArrayList<>();
		
		for (Venda venda : this.map_vendas.values()) {
			LocalDate data_da_venda = venda.getData();
			if (data_menor == null && data_maior == null) {
				if (data_da_venda.isEqual(data_1))
					vendas_no_periodo.add(venda);
			}
			else {
				if (data_da_venda.isBefore(data_maior) || data_da_venda.isEqual(data_maior) && data_da_venda.isAfter(data_menor) || data_da_venda.isEqual(data_menor))
					vendas_no_periodo.add(venda);
			}
		}
		
		if (vendas_no_periodo.isEmpty())
			return null;
		
		Collections.sort(vendas_no_periodo);
		String texto = new String();
		for (Venda venda : vendas_no_periodo)
			texto += venda.toString() + "\n";
		
		return texto;
	}

		 
		 
		 
}
