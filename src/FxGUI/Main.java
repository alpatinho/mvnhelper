package FxGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Bota o titulo na janela
        primaryStage.setTitle("MVN Helper V_0.1");

        //carrega a cena (conteudo da pagina) no root
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

        //carrega a cena no stage primario
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}