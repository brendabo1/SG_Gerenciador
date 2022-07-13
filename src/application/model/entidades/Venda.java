package application.model.entidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import application.model.entidades.enums.FormasDePagamento;

/**
 * Entidade referente a representacao de vendas no sistema.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
public class Venda extends EntidadesDoSistema implements Comparable<Venda>{
	private LocalDate data = LocalDate.now();
	private LocalTime hora = LocalTime.now();
	private HashMap<String, CarrinhoDeCompra> itens_comprados;
	private Double preco_total = 0.0;
	private FormasDePagamento forma_de_pagamento;
	private Cliente cliente;
	final private static String preFixo = "VEN";
	
		/**
		 * Cria uma instancia de venda.
		 * @param id identificacao de venda.
		 * @param itens_comprados HashMap dos itens comprados.
		 * @param forma_de_pagamento forma de pagamento da venda.
		 */
		public Venda(String id,  HashMap<String, CarrinhoDeCompra> itens_comprados, FormasDePagamento forma_de_pagamento, Cliente cliente) {
			this.id = id;
			this.itens_comprados = itens_comprados;
			this.forma_de_pagamento = forma_de_pagamento;
			this.cliente = cliente;
			this.preco_total = calcularPrecoTotal();
		}
		
			
		@Override
		public String toString() {
			String hora = this.hora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			String message = String.format("%s  DATA: %s  HORA: %s  \nItem:\n", this.id, data, hora);
			
			for (CarrinhoDeCompra item: this.itens_comprados.values()) {
				message += item.getId() + item.getItem_comprado().getNome() +  "   R$" + item.getItem_comprado().getPreco() + item.getQuantidade_comprada() +"\n";
			}
			
			message += "PREÇO TOTAL  R$: " + this.preco_total + 
					"\nFORMA DE PAGAMENTO: " + this.forma_de_pagamento + "\n";
			
			return message;
		}
		
		/**
		 * Organiza o titulo sobre a entidade
		 * @return string formatada para titulo
		 */
		public String linhaTituloToString() {
			String message = String.format("\n%2s %19s %22s %12s %s %s %s", "ID Venda", "DATA", "HORA","ID", "ITEM", "PRECO UNT", "QNT");
			return message;
		}
		
		/**
		 * Calcula o preço total através da soma dos preços dos itens comprados
		 * @return
		 */
		private Double calcularPrecoTotal  () {
			for (CarrinhoDeCompra compra : itens_comprados.values()) {
				this.preco_total += compra.getPreco_compras();
			}
			return preco_total;
		}	
		
		
		
		public  HashMap<String, CarrinhoDeCompra> getItens_comprados() {
			return itens_comprados;
		}
		
		public void setItens_comprados( HashMap<String, CarrinhoDeCompra> itens_comprados) {
			this.itens_comprados = itens_comprados;
		}
		
		public Double getPreco_total() {
			return preco_total;
		}
		public void setPreco_total(Double preco_total) {
			this.preco_total = preco_total;
		}
		
		public FormasDePagamento getForma_de_pagamento() {
			return forma_de_pagamento;
		}
		public void setForma_de_pagamento(FormasDePagamento forma_de_pagamento) {
			this.forma_de_pagamento = forma_de_pagamento;
		}
		
		
		public static String getPreFixo() {
			return preFixo;
		}
		
		/**
		 * Formata a data no padrão do calendario gregoriano	
		 * @param data objeto de acordo com o calendario 
		 * @return string formatada
		 */
		public String formatarData(LocalDate data) {
			String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			return dataFormatada;
			}
		/**
		 * Formata a data no padrão de hora, minuto e segundo
		 * @param hora objeto de acordo com o padrao de hora 
		 * @return string hora formatada
		 */
		public String formatarHora(LocalTime hora) {
			String horaFormatada = data.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			return horaFormatada;
		}
		
		
		public LocalDate getData() {
			return data;
		}
		public void setData(LocalDate data) {
			this.data = data;
		}
		
		
		public LocalTime getHora() {
			return hora;
		}
		public void setHora(LocalTime hora) {
			this.hora = hora;
		}
		
		
		public Cliente getCliente() {
			return cliente;
		}
		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}
		
		
		public static String getPrefixo() {
			return preFixo;
		}
		
		
		@Override
		public int compareTo(Venda outraVenda) {
			if (this.data.isAfter(outraVenda.getData()))
				return -1;
			if (this.data.isBefore(outraVenda.getData()))
				return 1;
			return 0;
		}
}
