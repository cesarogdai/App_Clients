package cesar.ogdai.clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cesar.ogdai.clientes.entidades.User;
import cesar.ogdai.clientes.utils.Utilidades;

public class ClientDetails extends AppCompatActivity {
    private TextView name, phone, model,action;
    private Button whtapp, edit,call;
    public String number = "";

    public String Name, Phone, Model, Action, Date;
    public Integer idDrop;
    public static int opcion = 0;
    String numberreal;
    String message = "Hola, que tal";
    public String datos;
    SQLHelper conn;
    public String fecha;

    String newText="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        setTitle(R.string.client_details);
        conn = new SQLHelper(getApplicationContext(),"bd_clientes", null, 1);


        name = (TextView)findViewById(R.id.user_name);
        phone = (TextView)findViewById(R.id.user_phone);
        model = (TextView) findViewById(R.id.user_model);
        action = (TextView) findViewById(R.id.user_action);
        whtapp = (Button) findViewById(R.id.btn_whatsapp);
        edit = (Button)findViewById(R.id.btn_edit);
        call = (Button) findViewById(R.id.btn_call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{


                    String phoneNumber = "+52"+ number;
                    Intent callIntent = new Intent();
                    callIntent.setAction(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+phoneNumber));
                    //Toast.makeText(getApplicationContext(), ""+number,Toast.LENGTH_SHORT).show();
                    startActivity(callIntent);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Debe habilitar permisos", Toast.LENGTH_SHORT).show();
                }

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence choice[] = new CharSequence[]{"Borrar", "Editar"};
                final CharSequence delete[] = new CharSequence[]{"Si"};

                final CharSequence editoptions[] = new CharSequence[]{"Numero", "Modelo","Accion", "Fecha"};

                final AlertDialog.Builder builder = new AlertDialog.Builder(ClientDetails.this);
                builder.setTitle("¿Qué desea hacer?");
                builder.setItems(choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientDetails.this);
                            builder1.setTitle("¿Desea borrarlo?");
                            builder1.setItems(delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which == 0){
                                        dropClient(idDrop);
                                        Toast.makeText(getApplicationContext(), "Se ha borrado", Toast.LENGTH_SHORT).show();
                                    }else{
                                        finish();
                                    }
                                }
                            });
                            builder1.show();
                        }else if(which == 1){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientDetails.this);
                            builder1.setTitle("¿Qué desea editar?");
                            builder1.setItems(editoptions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0){
                                        //Numero

                                        opcion = 1;
                                        //   Toast.makeText(getApplicationContext(), ""+opcion, Toast.LENGTH_SHORT).show();
                                        loadFragment();

                                    }else if(which == 1){
                                        //Model
                                        opcion = 2;
                                        // Toast.makeText(getApplicationContext(), ""+opcion, Toast.LENGTH_SHORT).show();
                                        //
                                        loadFragment();


                                    }else if(which == 2){
                                        //accion
                                        opcion = 3;
                                        //Toast.makeText(getApplicationContext(), ""+opcion, Toast.LENGTH_SHORT).show();
                                        loadFragment();


                                    }else if(which == 3){
                                        //DATE
                                        opcion = 4;
                                        loadFragment();

                                    }
                                }
                            });
                            builder1.show();

                        }else{
                            finish();
                        }
                    }
                });
                builder.show();
            }
        });

        whtapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberreal = "52"+ number;
                try{

                    getPackageManager().getPackageInfo("com.whatsapp.w4b", PackageManager.GET_META_DATA);
                    String url = "https://api.whatsapp.com/send?text="+message+","+"&phone="+numberreal;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    //   Toast.makeText(getApplicationContext(), ""+numberreal, Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Whatsapp business no está instalado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle objeto = getIntent().getExtras();
        User user = null;

        if(objeto != null){
            user = (User) objeto.getSerializable("client");
            //ID
            idDrop= user.getId();
            //NAME
            Name = user.getName();
            name.setText(user.getName().toString());

            //PHONE
            Phone = user.getPhone();
            phone.setText(user.getPhone().toString());

            //MODEL
            Model = user.getModel();
            model.setText(user.getModel().toString());

            //ACTION
            Action = user.getAction();
            action.setText(user.getAction().toString());

            //DATE
            Date = user.getDatepicked();
            //   Toast.makeText(getApplicationContext(), "Date: "+ user.getDatepicked(), Toast.LENGTH_SHORT).show();

            //WHATSAPP
            number = user.getPhone().toString();
        }
    }




    private void loadFragment(){
        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        final View myview = inflater.inflate(R.layout.client_edit, null);

        dialog.setView(myview);
        final AlertDialog dialogg = dialog.create();
        dialogg.setCancelable(false);
        dialogg.show();

        Button buscar, cancel;
        final EditText newdatos;
        final CalendarView newDate;
        newDate = myview.findViewById(R.id.calendarEdit);

        newDate.setVisibility(View.GONE);

        newdatos = myview.findViewById(R.id.clientEdit);
        buscar = myview.findViewById(R.id.clientAccept);
        cancel = myview.findViewById(R.id.clientCancel);

        if(opcion == 1){
            newdatos.setHint("Ingresa el nuevo numero");


        }else if(opcion == 2){
            newdatos.setHint("Ingresa el nuevo modelo");

        }else if(opcion == 3){
            newdatos.setHint("Ingresa la nueva accion");

        }else if(opcion == 4){
            newdatos.setVisibility(View.INVISIBLE);
            newDate.setVisibility(View.VISIBLE);
            newDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    // date = year +"-"+(month+1)+"-"+dayOfMonth;
                    if(dayOfMonth <= 9){
                        Date = year + "-" + (month + 1) +"-"+ 0+dayOfMonth;
                    }else{
                        Date = year +"-"+(month+1)+"-"+dayOfMonth;
                    }

                }
            });
            //   editDate(Date);

        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogg.dismiss();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datos = newdatos.getText().toString();

                if(opcion == 1){
                    //NUmero
                    if(datos.length() == 10){
                        editNumero();
                    }else{
                        Toast.makeText(getApplicationContext(), "Revise el numero por favor", Toast.LENGTH_SHORT).show();
                    }

                }else if(opcion == 2){
                    //Model
                    if(datos.length() > 0){
                        editModel();
                    }else{
                        Toast.makeText(getApplicationContext(), "EL campo esta vacio", Toast.LENGTH_SHORT).show();
                    }

                }else if(opcion == 3){
                    //accion
                    if(datos.length() > 0){
                        editAccion();
                    }else{
                        Toast.makeText(getApplicationContext(), "EL campo esta vacio", Toast.LENGTH_SHORT).show();
                    }
                }else if(opcion == 4){
                    //DATE
                    editDate();
                }
            }
        });

    }


    private void dropClient(int id){
        this.idDrop = id;
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] params = {datos};
        try{
            db.execSQL("DELETE FROM " +Utilidades.Table_Clients+ " WHERE "+
                    Utilidades.ID_FIELD+"="+idDrop);
            Toast.makeText(getApplicationContext(), "Se ha borrado", Toast.LENGTH_SHORT).show();
            db.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "No se pudo realizar", Toast.LENGTH_SHORT).show();
        }
    }

    private void editNumero(){
        try{
            String id = idDrop.toString();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] params = {id};
            ContentValues values = new ContentValues();
            values.put(Utilidades.PHONE_FIELD,datos);
            db.update(Utilidades.Table_Clients, values, Utilidades.ID_FIELD+"=?",params);
            Toast.makeText(getApplicationContext(), "Se ha actualizado", Toast.LENGTH_SHORT).show();

            db.close();
            finish();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "No se pudo realizar", Toast.LENGTH_SHORT).show();
        }

    }

    private void editModel (){

        try{
            String id = idDrop.toString();
            SQLiteDatabase db = conn.getReadableDatabase();
            String[] params = {id};
            ContentValues values = new ContentValues();
            values.put(Utilidades.MODEL, datos);
            db.update(Utilidades.Table_Clients, values, Utilidades.ID_FIELD+"=?", params);
            Toast.makeText(getApplicationContext(), "Se ha actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            finish();


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No se pudo realizar", Toast.LENGTH_SHORT).show();
        }

    }
    private void editAccion(){

        try{
            String id = idDrop.toString();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] params = {id};
            ContentValues values = new ContentValues();
            values.put(Utilidades.ACTION, datos);
            db.update(Utilidades.Table_Clients, values, Utilidades.ID_FIELD+"=?", params);
            Toast.makeText(getApplicationContext(), "Se ha actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            finish();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "No se pudo realizar", Toast.LENGTH_SHORT).show();

        }

    }

    private void editDate(){

        try{
            String id = idDrop.toString();
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] params = {id};
            ContentValues values = new ContentValues();
            values.put(Utilidades.DATE_PICKED, Date);
            db.update(Utilidades.Table_Clients, values, Utilidades.ID_FIELD+"=?", params);
            Toast.makeText(this, "Se ha actualizado", Toast.LENGTH_SHORT).show();
            db.close();
            finish();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "No se pudo realizar", Toast.LENGTH_SHORT).show();
        }

    }


}