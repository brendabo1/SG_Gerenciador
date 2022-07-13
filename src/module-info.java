module SistemaAutomacao {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires itextpdf;
	requires org.junit.platform.suite.api;
	requires junit;
	requires org.junit.jupiter.api;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens application.controllers to javafx.fxml;
	
	
	exports application;
	exports application.model.entidades;
	exports application.model.entidades.enums;
}
