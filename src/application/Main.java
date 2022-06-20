package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {			
			//AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("views/TelaLogin.fxml"));
			Parent root = FXMLLoader.load(getClass().getResource("views/TelaLogin.fxml"));
			
			Scene sceneLogin = new Scene(root,500,300);
			primaryStage.setScene(sceneLogin);
			sceneLogin.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setTitle("SG Gerenciador");
			primaryStage.getIcons().add(new Image("file:resources/local_localstoreicon.png"));
			primaryStage.setScene(sceneLogin);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
