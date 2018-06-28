import Core.Enums;
import Core.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MvnHelper extends Application{


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        new Util().getMap();
        primaryStage.getIcons().add(new Image("file:" + Enums.ConfigPath.LOGO_MVNHELPER.getCaminho()));
        primaryStage.setTitle(Enums.Mensagens.MVN_HELPER_VERSION.getTitulo());

        Parent root = FXMLLoader.load(getClass().getResource("/MvnHelper.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

}