package sample.domain;

public class Sales {
    private long id;
    private Medicine medicine;
    private Client client;
    private String salePrice;

    public Sales(){
        super();
    }
    public Sales(long id, Medicine medicine, Client client, String salePrice){
        super();
        this.id = id;
        this.medicine = medicine;
        this.client = client;
        this.salePrice = salePrice;
    }
    public Sales(Medicine medicine, Client client, String salePrice){
        super();
        this.medicine = medicine;
        this.client = client;
        this.salePrice = salePrice;
    }
    public long getId(){return id;}
    public void setId(long id){this.id = id;}
    public Medicine getMedicine(){return medicine;}
    public void setMedicine(Medicine medicine){this.medicine = medicine;}
    public Client getClient(){return client;}
    public void setClient(Client client){this.client = client;}
    public String getSalePrice(){return salePrice;}
    public void setSalePrice(String salePrice){this.salePrice = salePrice;}

    @Override
    public String toString(){
        return "ID - " + id + ", Medicine id - " + medicine.getId() + ", Medicine title - " + medicine.getTitle() + ", Medicine manufacturer - " + medicine.getManufacturer() + ", Client name - " + client.getFirstName() + ", Client surname - " + client.getLastName();
    }
}
