package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Product {
    
    private final StringProperty mTitle;
    private final StringProperty mImageUrl;
    
    private final DoubleProperty mPrice;
    private final IntegerProperty mPosition;
    
    private final StringProperty mStockTime;
    private final BooleanProperty mVisible;
    
    public Product(String title, String imageUrl, double price, int position, String stockTime, boolean visible) {
        mTitle = new SimpleStringProperty(title);
        mImageUrl = new SimpleStringProperty(imageUrl);
        
        mPrice = new SimpleDoubleProperty(price);
        mPosition = new SimpleIntegerProperty(position);
        
        mStockTime = new SimpleStringProperty(stockTime);
        mVisible = new SimpleBooleanProperty(visible);
    }
    
    public void setTitle(String title) {
        mTitle.set(title);
    }
    
    public StringProperty getTitle() {
        return mTitle;
    }
    
    public void setImageUrl(String imageUrl) {
        mImageUrl.set(imageUrl);
    }
    
    public StringProperty getImageUrl() {
        return mImageUrl;
    }
    
    public void setPrice(double price) {
        mPrice.set(price);
    }
    
    public DoubleProperty getPrice() {
        return mPrice;
    }
    
    public void setPosition(int position) {
        mPosition.set(position);
    }
    
    public IntegerProperty getPosition() {
        return mPosition;
    }
    
    public void setStockTime(String stockTime) {
        mStockTime.set(stockTime);
    }
    
    public StringProperty getStockTime() {
        return mStockTime;
    }
    
    public void setVisible(boolean visible) {
        mVisible.set(visible);
    }
    
    public BooleanProperty getVisible() {
        return mVisible;
    }
}
