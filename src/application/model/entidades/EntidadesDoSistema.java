package application.model.entidades;

/**
 * Classe pai das demais entidades básicas do sistema.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
abstract public class EntidadesDoSistema {
		protected String id;		
		
		@Override
		abstract public String toString();

		/**
		 * Método para acesso do ID de identificação.
		 * @return ID da entidade.
		 */
		public String getId() {
			return id;
		}
		
		/**
		 * Método para alterar o ID da entidade.
		 * @param id Novo ID.
		 */
		public void setId(String id) {
			this.id = id;
		}
}
