package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import logic.Program;
import logic.RequestUtil;
import maincontol.ProgramNavigator;

public class DictionaryLoader implements Initializable {
	
		@FXML
		private ListView<String> lvDictList;
		@FXML
		private Button btnBack;
		@FXML
		private Button btnLoad;
		@FXML
		private Button btnRefresh;
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			lvDictList.setItems(Program.getDictionaries());
			boolean isFolder = false;
			File[] filed = new File(Program.dir + ".").listFiles();
				for(File f:filed)
					if(f.getName() == Program.dir + "dictionaries")
						isFolder = true;
				if(!isFolder){
					File direct = new File(Program.dir + "dictionaries");
					direct.mkdir();
				}
		}
		//dictionary load
		@FXML
		private void loadDictionary(ActionEvent event){
			Integer index = lvDictList.getSelectionModel().getSelectedIndex();
			// here may be part that can add "loaded" to item
			if(index != -1)
				try {
					RequestUtil.postGetFile(
								String.valueOf(1), 
								String.valueOf(index+1), 
								lvDictList.getSelectionModel().getSelectedItem()
							);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			Program.user.chooseDictionary(index);
		}
		@FXML
		private void refteshList(ActionEvent event){
			lvDictList.setItems(Program.getDictionaries());
		}
		@FXML
		private void back(ActionEvent event){
			ProgramNavigator.loadPane(ProgramNavigator.MENU);
		}
}
