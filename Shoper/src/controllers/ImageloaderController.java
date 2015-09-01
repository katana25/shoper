package controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import shoper.Main;
import utils.DirectoryListLoader;
import utils.GridPaneImageLoader;
import utils.LocatedFile;
import utils.LocatedImage;


public class ImageloaderController implements Initializable {

    @FXML private ChoiceBox mChoiceBox; 
    @FXML private ImageView mBack;
    @FXML private TextField mSelectText;
    @FXML private ScrollPane mScrollPane;
    @FXML private GridPane mGridPane;
    
    public static final int GRIDPANE_COLUMNSNUMBER = 4;
    
    private ObservableList<LocatedFile> mDirectoryList;
    private File mLastFile;
    
    private ChangeListener changeListener = (observableValue, oldValue, newValue) -> {
        Number value = (Number) newValue;
        changeDirectory(mDirectoryList.get(value.intValue()));
    }; 
    
    
    public static void selectImage(String path) {
        ProductsController.getmProductsController().selectImage(path);
        Main.stopPage();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configViews();
        configElements();
    }  
    
    private void configViews() {
        mBack.setImage(loadBackImage());
        mSelectText.cursorProperty().setValue(Cursor.DEFAULT);
        mScrollPane.setContent(mGridPane);

        mGridPane.setPadding(new Insets(10, 10, 10, 10));
        alignPaneCellsSize();
    }
    
    private void configElements() {
        mDirectoryList = FXCollections.observableArrayList();
        mChoiceBox.getSelectionModel().selectedIndexProperty().addListener(changeListener);
        changeDirectory(new File("/"));
    }
    
    private void changeDirectory(File file) {  
        mLastFile = new LocatedFile(file.getAbsolutePath());
        mSelectText.setText(file.toString());
        
        Platform.runLater(new GridPaneImageLoader(file, mGridPane));
        
        mDirectoryList = DirectoryListLoader.getDirectoryList(file);
        mChoiceBox.setItems(mDirectoryList);
    }    
    
    private Image loadBackImage() {       
        InputStream fileStream = null;
        fileStream = Main.class.getResourceAsStream("/files/back.png");
        Image image = new Image(fileStream);
        return image;                   
    }
    
    private void alignPaneCellsSize() {
        for (RowConstraints constr : mGridPane.getRowConstraints())
                constr.setMinHeight(120);
    }
    
    @FXML
    private void back() {
        changeDirectory(mLastFile.getParentFile()); 
    }
    
    @FXML
    protected void onScrollHandler(ScrollEvent scrollEvent) {
        System.out.println("QSQS");
        mGridPane.setPadding(new Insets(10, 10, 10, 10));
    }
}

