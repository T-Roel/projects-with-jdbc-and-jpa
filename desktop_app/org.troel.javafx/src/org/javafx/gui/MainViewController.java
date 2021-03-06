package org.javafx.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.javafx.application.Main;
import org.javafx.gui.utils.Alerts;
import org.jdbc.services.DepartmentService;
import org.jdbc.services.SellerService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/org/javafx/gui/SellerList.fxml", (SellerListController controller) -> {
			controller.setSellerService(new SellerService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/org/javafx/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/org/javafx/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	private synchronized <T> void loadView(String absoluteName, Consumer<T> initAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error while trying to load view", e.getMessage(), AlertType.ERROR);
		}
	}
}
