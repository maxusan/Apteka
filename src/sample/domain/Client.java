package sample.domain;

public class Client {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private  long card;
    public Client()
    {
        super();
    }
    public Client(long id, String firstName, String lastName, String phone, long card){
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.card = card;
    }
    public Client( String firstName, String lastName, String phone, long card){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.card = card;
    }

    public long getId(){return id; }
    public void setId(long id){ this.id = id; }
    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName;}
    public String getLastName(){ return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public String getPhone(){return phone; }
    public void setPhone(String phone){ this.phone = phone; }
    public long getCard(){return card;}
    public void setCard(long card){this.card = card;}

    @Override
    public String toString(){
        return  "id:" + id +" ["+ firstName + " " + lastName + "]";
    }
}