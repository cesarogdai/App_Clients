package cesar.ogdai.clientes.utils;

public class Utilidades {

    //Constants field contacts

    public static final String Table_Clients = "clients";
    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String PHONE_FIELD = "phone";
    public static final String DATE_PICKED  = "date";
    public static final String MODEL = "model";
    public static final String ACTION = "action";

    //create table sentence
    public static final String CREATE_TABLE_CLIENTS = "CREATE TABLE "+Table_Clients+" ("+
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME_FIELD + " TEXT, "+ PHONE_FIELD +
            " TEXT, "+MODEL+" TEXT, "+ACTION+ " TEXT, "+ DATE_PICKED + " TEXT)";
}
