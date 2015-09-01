package utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DirectoryListLoader {
    
    private static FileFilter directoryFilter = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            return pathname.getName().indexOf(".") == -1 ? true : false;
        }
    };
    
    public static ObservableList<LocatedFile> getDirectoryList(File file) {
        File[] files = file.listFiles(directoryFilter);
        ObservableList<LocatedFile> fileList = FXCollections.observableArrayList();
        for (File f : files)
            fileList.add(new LocatedFile(f.getAbsolutePath()));
        fileList.addAll();  
        return fileList;
    }
}
