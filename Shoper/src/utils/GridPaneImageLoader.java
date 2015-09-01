package utils;

import controllers.ImageloaderController;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class GridPaneImageLoader implements Runnable {
    
    private File mFile;
    @FXML private GridPane mGridPane;
    
    private FileFilter imageFilter = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            String[] extensions = {".jpg", ".jpeg", ".png"};
            for (String extension : extensions)
                if (pathname.getName().indexOf(extension) != -1)
                    return true;
            return false;
        }
    };
    
    public GridPaneImageLoader(File file, GridPane gridPane) {
        mFile = file;
        mGridPane = gridPane;
    }
    
    @Override
    public void run() {
        synchronized(mGridPane) {
            loadImages();
            Thread.currentThread().interrupt();
        }      
    }
    
    private void loadImages() {
        File[] imageFiles = mFile.listFiles(imageFilter);
        int rowPosition = 0;
        int columnPosition = 0;      
        
        for (int i=0; i<imageFiles.length; i++) {                                 
            if (columnsEnd(columnPosition)) {
                rowPosition++;
                columnPosition = 0;
            }
            
            ImageView imageView = createImageView(imageFiles[i]);
            mGridPane.add(imageView, columnPosition, rowPosition);            
            columnPosition++;
        }        
    }  
    
    private boolean columnsEnd(int columnPosition) {
       return columnPosition == ImageloaderController.GRIDPANE_COLUMNSNUMBER; 
    }
    
    private ImageView createImageView(File imageFile) {
        LocatedImage image = loadImage(imageFile.getAbsolutePath());
        ImageView imageView = new ImageView(image);    
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setSmooth(true);   
        imageView.setOnMouseClicked((event) -> {
            ImageloaderController.selectImage(imageFile.getAbsolutePath());
        });
        return imageView;
    }
    
    private LocatedImage loadImage(String path) {  
        InputStream fileStream = null;      
        try {
            fileStream = new BufferedInputStream(new FileInputStream(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageloaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        LocatedImage image = new LocatedImage(fileStream, 100, 100, false, true);
        return image;                   
    }      
}
