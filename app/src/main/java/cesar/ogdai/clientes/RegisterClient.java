package cesar.ogdai.clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import cesar.ogdai.clientes.utils.Utilidades;

public class RegisterClient extends AppCompatActivity {
    EditText name, model ,phone, action;
    Button accept, cancel;
   public String date;
    CalendarView datePicked;

    String Name, Model, Phone, Action;
   // public int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        setTitle(R.string.register_client);
        //Edit Text
        name = (EditText)findViewById(R.id.edt_name);
        model = (EditText)findViewById(R.id.edt_model);
        phone = (EditText)findViewById(R.id.edt_phone);
        action = (EditText) findViewById(R.id.edt_action);




        //Buttons
        accept = (Button) findViewById(R.id.btn_accept);
        cancel = (Button) findViewById(R.id.btn_cancel);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   registerClient();
               // Toast.makeText(getApplicationContext(), "Date Selected: "+ date, Toast.LENGTH_SHORT).show();



            }
        });



        //CalendarView
        datePicked = (CalendarView) findViewById(R.id.calendar);
        datePicked.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
           //  i = month +1;
            /* Calendar c = Calendar.getInstance();
             c.set(Calendar.YEAR, year);
             c.set(Calendar.MONTH,month);
             c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
             date = DateFormat.getDateInstance().format(c.getTime());

             */
           // date = dayOfMonth+"-"+ (month+1) + "-"  +year;
                String day = "";
                if(dayOfMonth <= 9) {
                    date = year + "-" + (month +1)+"-"+0+""+dayOfMonth;
                }else{
                    date = year + "-" + (month +1) + "-" +dayOfMonth;
                }
          //  date = year +"/"+(month+1)+"/"+dayOfMonth;

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
             //   Toast.makeText(getApplicationContext(), "date: "+date, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void registerClient(){

        Name = name.getText().toString();
        Phone = phone.getText().toString();
        Action = action.getText().toString();
        Model = model.getText().toString();



       if(TextUtils.isEmpty(Name) && TextUtils.isEmpty(Phone) &&
               TextUtils.isEmpty(Model) && TextUtils.isEmpty(Action) )
        {
            Toast.makeText(getApplicationContext(), "Un campo o mas están vacios", Toast.LENGTH_SHORT).show();

        }else {
           SQLHelper conn = new SQLHelper(this, "bd_clientes", null, 1);

           //using sql normally
           SQLiteDatabase db = conn.getWritableDatabase();

           String insert = "INSERT INTO "+
                   Utilidades.Table_Clients +" ("+

                   Utilidades.NAME_FIELD+"," +
                   Utilidades.PHONE_FIELD+","+
                   Utilidades.MODEL+","+
                   Utilidades.ACTION+","+
                   Utilidades.DATE_PICKED+" )"+ "VALUES ('"+name.getText().toString()+"','"+
                   phone.getText().toString()+"','"+model.getText().toString().toLowerCase()+"', '"
                   +action.getText().toString()
                   + "', '"+date+"')";

           db.execSQL(insert);

         //  Toast.makeText(getApplicationContext(), " Se ha cargado"+date, Toast.LENGTH_SHORT).show();

           db.close();
           finish();
           Toast.makeText(getApplicationContext(), "Se ha cargado", Toast.LENGTH_SHORT).show();

        }



    }



}