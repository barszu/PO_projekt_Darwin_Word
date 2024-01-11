package project.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import project.backend.backend.global.GlobalOptions;
import project.frontend.controllers.ConfigLoaderController;
import project.frontend.controllers.HomeController;
import project.frontend.fileManagers.ConfigFileManager;
import project.backend.backend.model.enums.MapType;
import project.backend.backend.model.enums.MutationType;

import java.io.IOException;
import java.util.List;

public class ConfigLoaderPageApp {

    ConfigLoaderController configLoaderController;

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
//                scene.getStylesheets().add("styles.css");
        stage.show();
//        stage.showAndWait();


        configLoaderController.renderConfigs();
    }


}
