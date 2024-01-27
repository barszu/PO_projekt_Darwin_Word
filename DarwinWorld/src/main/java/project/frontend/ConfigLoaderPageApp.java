package project.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.frontend.controllers.ConfigLoaderController;
import project.frontend.controllers.HomeController;

import java.io.IOException;

public class ConfigLoaderPageApp {

    ConfigLoaderController configLoaderController; // modyfikator dostępu?

    HomeController homeController;

    public ConfigLoaderPageApp(HomeController homeController) {
        this.homeController = homeController;
    }

    public void show(Stage stage) throws IOException { //play -> show window
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/configLoaderGUI.fxml"));
        Parent root = loader.load();

        this.configLoaderController = loader.getController();
        configLoaderController.setHomeController(homeController);

        stage.setTitle("Options Load");
        Scene scene = new Scene(root);
        stage.setScene(scene);
//                scene.getStylesheets().add("grip-cell-style.css");
        stage.show();
//        stage.showAndWait();


        configLoaderController.renderConfigs();
    }


}
