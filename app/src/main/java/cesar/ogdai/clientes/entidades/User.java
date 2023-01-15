package cesar.ogdai.clientes.entidades;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String name;
    private String phone;
    private String datepicked;
    private String action;
    private String model;
    public User(Integer id, String name, String phone, String datepicked, String action, String model){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.datepicked = datepicked;
        this.action = action;
        this.model = model;
    }
    public User(){

    }

    public Integer getId(){return  id;}
    public void setId(Integer id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone = phone;}
    public String getModel(){return model;}
    public void setModel(String model){this.model = model;}

    public String getDatepicked(){return datepicked;}
    public void setDatepicked(String datepicked){this.datepicked = datepicked;}

    public String getAction(){return action;}
    public void setAction(String action){this.action = action;}


}
