package controllers;

import java.io.InputStream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.Product;
import shoper.Main;
import utils.LocatedImage;
import utils.ProductWriter;


public class ProductsController {   
    
    @FXML private TableView<Product> mProductTable;   
    @FXML private TableColumn<Product, String> mProductsTitles;  
    
    @FXML private TextField mTitleField;    
    @FXML private TextField mStockTimeField;    
    @FXML private TextField mPriceField;    
    
    @FXML private CheckBox mVisibleCheckBox;     
    @FXML private ImageView mImageView;   
    
    @FXML private Button mPublishButton;
    @FXML private Button mAddBotton; 
    @FXML private Button mRemoveBotton;
    
    private ObservableList<Product> productList;
    
    private ChangeListener mShowListener = (observable, oldValue, newValue) -> {
            showProduct((Product) newValue);
    };
    
    @FXML
    private void initialize() {
        productList = getProductList();        
        mProductTable.setItems(productList);
        mProductsTitles.setCellValueFactory(cellData -> cellData.getValue().getTitle());        
        mProductTable.getSelectionModel().selectedItemProperty().addListener(mShowListener);      
        mProductTable.getSelectionModel().select(productList.get(0));
    }   
    
    @FXML
    private void remove() {
        int selectIndex = mProductTable.getSelectionModel().getSelectedIndex();
        mProductTable.getItems().remove(selectIndex);
    }
    
    @FXML
    private void add() {
        mProductTable.getSelectionModel().clearSelection();
        showEmptyProduct();
    }

    @FXML
    private void save() {
        Product product = mProductTable.getSelectionModel().getSelectedItem();
        if (product == null) 
           saveNewProduct();
        else
            saveExistingProduct(product);
    }
    
    @FXML
    private void loadimage() {
        Main.startStage(Pane.class, "/layout/imageloader.fxml");
    }
    
    private void saveNewProduct() {
        System.out.println("New");
        if (checkProductField()) {
            System.out.println("ok");
        }
    }
    
    private void saveExistingProduct(Product product) {
        System.out.println("Existing");
        if (checkProductField()) 
            ProductWriter.write(product);
    }
    
    private boolean checkProductField() {
        if (mTitleField.getText().equals(""))
            return false;
        
        if (mStockTimeField.getText().equals(""))
            return false;
        
        if (mPriceField.getText().equals(""))
            return false;
     
        if (((LocatedImage) mImageView.getImage()).isDefault())
            return false;
        
        return true;
    }
    
    private void showProduct(Product product) {
        mTitleField.setText(product.getTitle().get());
        mStockTimeField.setText(product.getStockTime().get());
        mPriceField.setText(product.getPrice().get()+"");

        mVisibleCheckBox.setSelected(product.getVisible().get());        
        mImageView.setImage(getImage(product));
    }
    
    private void showEmptyProduct() {
        mTitleField.setText("");
        mStockTimeField.setText("");
        mPriceField.setText("");

        mVisibleCheckBox.setSelected(false);        
        mImageView.setImage(getDefaultImage());
    }
    
    private LocatedImage getImage(Product product) {
        String url = product.getImageUrl().get();
        
        InputStream fileStream = null;
        LocatedImage image;
        
        if (url.isEmpty())
            return getDefaultImage();
        else
            fileStream = Main.class.getResourceAsStream("/files/"+url);
        
        image = new LocatedImage(fileStream);
        return image;                   
    }
    
    private LocatedImage getDefaultImage() {
        InputStream fileStream = Main.class.getResourceAsStream("/files/image.jpg");
        LocatedImage image = new LocatedImage(fileStream, true);
        return image;   
    }
    
    public static ObservableList<Product> getProductList() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        
        list.add(new Product("Кросовки", "cross.jpg", 2000, 0, "2 мая", true));
        list.add(new Product("Футболка", "", 500, 1, "25 мая", true));
        list.add(new Product("Кепка", "", 700, 2, "12 мая", true));
        list.add(new Product("Толстовка", "", 1200, 3, "10 мая", true));
        
        return list;
    }
}
