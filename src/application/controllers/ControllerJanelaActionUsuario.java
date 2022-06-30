package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerJanelaActionUsuario {
	@FXML
    private TextField campoNome;

    @FXML
    private TextField campoSenha;

    @FXML
    private ChoiceBox<?> choiceBoxCargo;

    @FXML
    private Label labelTitulo;
    
    @FXML
    private Label msgErroNome;

    @FXML
    private Label msgErroSenha;

    
    
    
}
