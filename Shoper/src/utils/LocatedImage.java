package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.image.Image;


public class LocatedImage extends Image {
    
    private boolean mDefault = false;
    private InputStream mInputStream;
    
    public LocatedImage(InputStream stream) {
        super(stream);
        mInputStream = stream;
    }
    
    public String getPathc() {
        return null;
    }
    
    public LocatedImage(InputStream stream, boolean defaultImage) {
        super(stream);
        mDefault = defaultImage;
    }
    
    public boolean isDefault() {
        return mDefault;
    }
}
