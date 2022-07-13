package application.model.gerenciadores;

import java.util.HashMap;

import application.model.BancoDeDados;
import application.model.entidades.Cliente;

public class GerenciamentoCliente extends GerenciamentoGeral {
		private HashMap<String, Cliente> map_clientes;
		
			public GerenciamentoCliente (BancoDeDados banco) {
				this.map_clientes = banco.getMap_clientes();
			}
			
		public Cliente cadastrar (String nome, String CPF, String email, String telefone) {
			String novo_id = gerarID(Cliente.getPrefixo());
			Cliente novo_cliente = new Cliente(nome, CPF, email, telefone, novo_id);
			if (adicionar(map_clientes, novo_cliente))
				return novo_cliente;
			return null;
		}
		
		public boolean editarNome(String novo_nome, Cliente cliente) {
			cliente.setNome(novo_nome);
			return cliente.getNome().equals(novo_nome);
		}
		
		public boolean editarCPF(String novo_CPF, Cliente cliente) {
			cliente.setCPF(novo_CPF);
			return cliente.getCPF().equals(novo_CPF);
		}
		
		public boolean editarEmail(String novo_Email, Cliente cliente) {
			cliente.setEmail(novo_Email);
			return cliente.getEmail().equals(novo_Email);
		}
		
		public boolean editarTelefone(String novo_telefone, Cliente cliente) {
			cliente.setTelefone(novo_telefone);
			return cliente.getTelefone().equals(novo_telefone);
		}
		
		public HashMap<String, Cliente> getMap_clientes() {
			return map_clientes;
		}
}
