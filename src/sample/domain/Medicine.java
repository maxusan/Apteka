package sample.domain;

import java.lang.reflect.Type;

public class Medicine {
    private long id;
    private String title;
    private String manufacturer;
    private String type;
    private int amount;
    private int price;
    public Medicine()
    {
        super();
    }
    public Medicine(long id, String title, String manufacturer, String type, int amount, int price){
        super();
        this.id = id;
        this.title = title;
        this.manufacturer = manufacturer;
        this.type = type;
        this.amount = amount;
        this.price = price;
    }
    public Medicine( String title, String manufacturer, String type, int amount, int price){
        super();
        this.title = title;
        this.manufacturer = manufacturer;
        this.type = type;
        this.price = price;
        this.amount = amount;
    }
    public long getId(){ return id; }
    public void setId(long id){ this.id = id; }
    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }
    public String getManufacturer(){ return manufacturer; }
    public void setManufacturer(String manufacturer){ this.manufacturer = manufacturer; }
    public String getType(){return type;}
    public void setType(String type){this.type = type;}
    public int getAmount(){ return amount; }
    public void setAmount(int amount){ this.amount = amount; }
    public int getPrice(){ return price; }
    public void setPrice(int price){ this.price = price; }
    @Override
    public String toString(){
        return "id:" + id +" ["+ title + "-" + manufacturer + "]" ;
    }
}
