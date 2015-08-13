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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import shoper.Main;


public class ImageloaderController implements Initializable {

    @FXML private ChoiceBox mChoiceBox; 
    @FXML private ImageView mBack;
    @FXML private TextField mSelectText;
    @FXML private ScrollPane mScrollPane;
    @FXML private GridPane mGridPane;
    
    private ObservableList<File> mDirectoryList;
    private File mFile;
    
    private FileFilter directoryFilter = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().indexOf(".") == -1 ? true : false;
        }
    };
    
    private FileFilter imageFilter = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            boolean isImage = false;
            if (pathname.getName().indexOf(".jpg") != -1)
                isImage = true;
            if (pathname.getName().indexOf(".jpeg") != -1)
                isImage = true;
            if (pathname.getName().indexOf(".png") != -1)
                isImage = true;
            return isImage;
        }
    };
    
    private ChangeListener changeListener = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue ov, Number value, Number newValue) {
            File file = mDirectoryList.get(newValue.intValue());
            changeDirectory(file);
        }
    }; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mDirectoryList = FXCollections.observableArrayList();
        changeDirectory(new File("/"));  
        mChoiceBox.getSelectionModel().selectedIndexProperty().addListener(changeListener);
        mBack.setImage(getImage());
        mSelectText.cursorProperty().setValue(Cursor.DEFAULT);
        mScrollPane.setContent(mGridPane);
    }  
    
    private void changeDirectory(File file) {
        mFile = new File(file.getAbsolutePath());
        mDirectoryList = createPathsObject(file);
        mChoiceBox.setItems(mDirectoryList);
    }
    
    private ObservableList<File> createPathsObject(File file) {
        mSelectText.setText(file.toString());
        File[] files = file.listFiles(directoryFilter);
        generatemage(file);
        ObservableList<File> fileList = FXCollections.observableArrayList();
        fileList.addAll(Arrays.asList(files));     
        return fileList;
    }
    
    private void generatemage(File file) {
        mGridPane.setPadding(new Insets(10, 10, 10, 10));
        File[] files = file.listFiles(imageFilter);
        int row = 0;
        int column = 0;
        int chk = 0;
        for (int i=0; i<files.length; i++) {
            ImageView image = new ImageView(getImage(files[i].getAbsolutePath()));
            
            
            image.setFitWidth(100);
            image.setFitHeight(100);
            image.setSmooth(true);
            
            if (column == 4) {
                row+=10;
                column = 0;
            }
            mGridPane.add(image, column, row);
            //double height = mGridPane.getHeight()+100;
            //mGridPane.resize(500, height);
            column++;
        }
    }
    
    private Image getImage() {       
        InputStream fileStream = null;
        fileStream = Main.class.getResourceAsStream("/files/back.png");
        Image image = new Image(fileStream);
        return image;                   
    }
    
    private Image getImage(String path) {       
        InputStream fileStream = null;
        System.out.println(path);
        try {
            fileStream = new BufferedInputStream(new FileInputStream(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageloaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = new Image(fileStream);
        return image;                   
    }
    
    @FXML
    private void back() {
        changeDirectory(mFile.getParentFile()); 
    }
}
