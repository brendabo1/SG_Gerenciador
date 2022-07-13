package application.model.facades;

import application.model.BancoDeDados;
import application.model.entidades.Cliente;
import application.model.gerenciadores.GerenciamentoCliente;
import application.model.validacoes.ValidaCliente;

public class FacadeCliente {
			private GerenciamentoCliente gerCliente;
			private ValidaCliente valCliente;
			
			FacadeCliente (BancoDeDados banco) {
				this.gerCliente = new GerenciamentoCliente(banco);
				this.valCliente = new ValidaCliente();
			}
			
			Cliente cadastrarCliente(String nome, String CPF, String email, String telefone) {
				if (!this.valCliente.isNomeValido(nome))
					throw new IllegalArgumentException("Digite um nome com no m�nimo 3 caracteres");
				
				if (!this.valCliente.isCPFValido(CPF))
					throw new IllegalArgumentException("O CPF deve conter unicamente 11 d�gitos.");
				
				if (!this.valCliente.isEmailValido(email))
					throw new IllegalArgumentException("E-mail Inv�lido");
				
				if (!this.valCliente.isTelefoneValido(telefone))
					throw new IllegalArgumentException("O telefone deve ter no m�nimo 8 d�gitos.");
				
				Cliente cliente = this.gerCliente.cadastrar(nome, CPF, email, telefone);
				if (cliente == null)
					throw new IllegalArgumentException("N�o foi poss�vel cadastrar o cliente.");
				
				return cliente;
			}
			
			void editarNomeCliente(String novo_nome, Cliente cliente) {
				if (!this.valCliente.isNomeValido(novo_nome))
					throw new IllegalArgumentException("Digite um nome com no m�nimo 3 caracteres");
				
				if (!this.gerCliente.editarNome(novo_nome, cliente))
					throw new IllegalArgumentException("N�o foi poss�vel editar o nome.");
			}
			
			void editarCPFcliente(String novo_cpf, Cliente cliente) {
				if (!this.valCliente.isCPFValido(novo_cpf))
					throw new IllegalArgumentException("O CPF deve conter unicamente 11 d�gitos.");
				
				if (!this.gerCliente.editarCPF(novo_cpf, cliente))
					throw new IllegalArgumentException("N�o foi poss�vel editar o CPF.");
			}
			
			void editarEmailCliente(String novo_email, Cliente cliente) {
				if (!this.valCliente.isEmailValido(novo_email))
					throw new IllegalArgumentException("E-mail Inv�lido");
				
				if (!this.gerCliente.editarEmail(novo_email, cliente))
					throw new IllegalArgumentException("N�o foi poss�vel editar o e-mail.");
			}
			
			void editarTelefoneCliente(String novo_telefone, Cliente cliente) {
				if (!this.valCliente.isTelefoneValido(novo_telefone))
					throw new IllegalArgumentException("O telefone deve ter no m�nimo 8 d�gitos.");
				
				if (!this.gerCliente.editarTelefone(novo_telefone, cliente))
					throw new IllegalArgumentException("N�o foi poss�vel editar o telefone.");
			}
			
			void excluirCliente(String ID) {
				if (!this.gerCliente.excluir(this.gerCliente.getMap_clientes(), ID))
					throw new IllegalArgumentException("N�o foi poss�vel excluir o cliente.");
			}
}