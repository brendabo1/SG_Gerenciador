package application.model.gerenciadores;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
/*
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
*/
import application.model.BancoDeDados;
import application.model.entidades.CarrinhoDeCompra;
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
		public GerenciamentoVenda(BancoDeDados banco, GerenciamentoItemCardapio gerItemCardapio, GerenciamentoLote gerLote) {
			this.map_vendas = banco.getMap_vendas();
			this.gerItemCardapio = gerItemCardapio;
			this.gerLote = gerLote;
		}
		
		/**
		 * Cadastra uma venda com todos os requisitos válidos
		 * @param itens_comprados Estrura contendo os itens comprados
		 * @param formaDePagamento Forma de pagamento da venda
		 * @return venda se o cadstro for bem sucedido e null se não cadastrar.
		 */
		public Venda cadastrar(HashMap<String, CarrinhoDeCompra> itens_comprados, FormasDePagamento formaDePagamento) {
			String nova_id = gerarID(Venda.getPreFixo());
			Venda nova_venda = new Venda(nova_id, itens_comprados, formaDePagamento);
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
		
		
		 /**
		 * Gera um arquivo PDF com os dados recebidos
		 * @param titulo Titulo do arquivo
		 * @param listaVendas Lista de Vendas selecionadas para compor o arquivo
		 * @throws FileNotFoundException
		 * @throws DocumentException
		 */
		/*
		public void gerarPDF(String titulo, ArrayList<Venda> listaVendas) throws FileNotFoundException, DocumentException {
			Document docpdf = new Document(PageSize.A4, 10f, 10f, 10f, 10f);
			PdfWriter.getInstance(docpdf, new FileOutputStream("src\\PDF_Venda.pdf")); 
			docpdf.open();
			Paragraph pTitulo = new Paragraph(15F , titulo, FontFactory.getFont(FontFactory.HELVETICA, 18F));
			Paragraph pLinha = new Paragraph(12F , listaVendas.get(0).linhaTituloToString(), FontFactory.getFont(FontFactory.HELVETICA, 15F));
			pTitulo.setAlignment(Element.ALIGN_CENTER);
			docpdf.add(pTitulo);
			docpdf.add(pLinha);
			for(Venda f:listaVendas) {
				Paragraph pMessage = new Paragraph(15F , f.toString(), FontFactory.getFont(FontFactory.HELVETICA, 15F));
				pTitulo.setAlignment(Element.ALIGN_JUSTIFIED);
				docpdf.add(pMessage);
			}
				
			docpdf.close();
			
		}*/
		
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
		 * Cria ums lista de vendas reslizadas no mes estabilecido
		 * @param mes Mes escolhido
		 * @return Lista de vendas realizadas no mes
		 * @throws NoSuchElementException
		 * @throws IllegalArgumentException
		 */
		public ArrayList<Venda> listaVendasNoMes(int mes)throws NoSuchElementException, IllegalArgumentException{
			ArrayList<Venda> listaVendas = this.convertHashToArr(this.map_vendas);
			ArrayList<Venda> vendasNoMes = new ArrayList<>();
			
			for(Venda venda: listaVendas) {
				if(venda.getData().getMonthValue() == mes){
					vendasNoMes.add(venda);
				}
			}
			return vendasNoMes;
		}
		/**
		 * Gera um arquivo PDF contendo as vendas realizadas no mes escolhido
		 * @param mes Mes escolhido
		 * @throws FileNotFoundException
		 * @throws DocumentException
		 */
		/*
		 public void gerarPDFPorMes(int mes) throws FileNotFoundException, DocumentException {
			ArrayList<Venda> vendas_periodo = this.listaVendasNoMes(mes);
			String titulo = "Vendas no Mês: " + mes;
			this.gerarPDF(titulo, vendas_periodo);
		 }*/
		 /**
		  * Cria uma lista contendo as vendas por categoria de item do cardapio
		  * @param categoria Categoria do cardapio escolhida
		  * @return Lista contendo as vendas contendo a categoria selecionada
		  */
		 
		 public ArrayList<Venda> listaVendasporCategoria(CategoriasDeItens categoria){
			 //erros no Comparator e collection.sort
			 ArrayList<Venda> listaVendas = this.convertHashToArr(this.map_vendas);
			 ArrayList<Venda> vendasPorCategoria = new ArrayList<>();
			 for(Venda venda: listaVendas) {
				 ArrayList<CarrinhoDeCompra> listaPratosComprados = this.convertHashToArr(venda.getItens_comprados());
				 for(CarrinhoDeCompra item: listaPratosComprados) {
					if(item.getItem_comprado().getCategoria() == categoria) {
						vendasPorCategoria.add(venda);
					}
				 }
			 }	 
			
			 return vendasPorCategoria;	
	
		}
		/**
		 * Gera um arquivo PDF contendo as vendas realizadas com a categoria do cardapio escolhida 
		 * @param categoria Categoria de itens do cardapio escolhida
		 * @throws FileNotFoundException
		 * @throws DocumentException
		 */
		 /*
		public void gerarPDFCategoriaPrato(CategoriasDeItens categoria) throws FileNotFoundException, DocumentException {
				ArrayList<Venda> vendasCategoria = this.listaVendasporCategoria(categoria);
				String titulo = "Vendas no Mês: " + categoria;
				this.gerarPDF(titulo, vendasCategoria);
		}*/


		public HashMap<String, Venda> getMap_vendas() {
			return map_vendas;
		}

		 
		 
		 
}
