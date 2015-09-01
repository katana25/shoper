package utils;

import java.io.File;
import java.net.URI;

public class LocatedFile extends File {

    public LocatedFile(String path) {
        super(path);
    }
    
    public LocatedFile(String parentName, String childName) {
        super(parentName, childName);
    }
    
    public LocatedFile(File parentFile, String childName) {
        super(parentFile, childName);
    }
    
    public LocatedFile(URI uri) {
        super(uri);
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
