package controllers;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.Program;
import maincontol.ProgramNavigator;
import model.User;

public class StartController implements Initializable {
	
	@FXML
	private AnchorPane startPane;
	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnEnter;
	@FXML
	void showMenu(ActionEvent event) {
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		
		if(Program.checkConnection()){
			if(Program.checkUser(email, password))
				ProgramNavigator.loadPane(ProgramNavigator.MENU);
		}else
			ProgramNavigator.loadPane(ProgramNavigator.MENU);
		System.out.println(email + " " + password);
		}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtEmail.requestFocus();
		Platform.runLater(new Runnable() {
		     @Override
		     public void run() {
		    	 txtEmail.requestFocus();
		     }
		});
	}
	
}
