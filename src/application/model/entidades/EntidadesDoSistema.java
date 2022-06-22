package application.model.entidades;

/**
 * Classe pai das demais entidades b�sicas do sistema.
 * @author Elmer Carvalho
 *@author Brenda Barbosa
 */
abstract public class EntidadesDoSistema {
		protected String id;		
		
		@Override
		abstract public String toString();

		/**
		 * M�todo para acesso do ID de identifica��o.
		 * @return ID da entidade.
		 */
		public String getId() {
			return id;
		}
		
		/**
		 * M�todo para alterar o ID da entidade.
		 * @param id Novo ID.
		 */
		public void setId(String id) {
			this.id = id;
		}
}
