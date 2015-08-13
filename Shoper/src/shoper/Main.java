package shoper;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {
   
    private Stage mMainStage;
    
    public static Main mMain;
    
    public static void main(String[] args) {    
        mMain = new Main();
        launch(args);
    } 
    
    @Override
    public void start(Stage primaryStage) {
        mMainStage = primaryStage;
        mMainStage.setTitle("Shoper");
        
        mMainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
        showPersonOverview();
    }
    
    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/layout/products.fxml"));
            Pane pane = (Pane) loader.load();                     
            Scene scene = new Scene(pane);
            mMainStage.setScene(scene);
            mMainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static <T extends Parent> void startStage(Class<T> paneClass, String layoutPatch) {
        try {
            Stage stage = new Stage();            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(layoutPatch));
            T pane = (T) loader.load();         
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getTitle() {
        return mMainStage.getTitle();
    }
    
    public void setTitle(String title) {
        mMainStage.setTitle(title);
    }
}
