package cesar.ogdai.clientes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cesar.ogdai.clientes.entidades.User;
import cesar.ogdai.clientes.utils.Utilidades;

public class SearchModel extends AppCompatActivity {

    ListView listViewModel;
    ArrayList<String> listInfo;
    ArrayList<User> listUser;
    public String modelName = "";
    SQLHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_model);
        setTitle(R.string.search_model);
        modelName = getIntent().getStringExtra("model");
    //    Toast.makeText(getApplicationContext(), "model: "+modelName, Toast.LENGTH_SHORT).show();
        conn = new SQLHelper(getApplicationContext(), "bd_clientes", null,1);

        consult();


        listViewModel =(ListView) findViewById(R.id.listViewModel);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listInfo);
        listViewModel.setAdapter(adapter);

        listViewModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = "id: "+listUser.get(position).getId()+"\n";
                info +=  "name: "+listUser.get(position).getName()+"\n";
                info += "phone: "+listUser.get(position).getPhone()+"\n";
                info += "model: "+listUser.get(position).getModel()+"\n";
                info += "action: "+listUser.get(position).getAction()+"\n";

                User user = listUser.get(position);

                Intent intent = new Intent(SearchModel.this, ClientDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", user);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }






    private void consult(){

        SQLiteDatabase db = conn.getReadableDatabase();

        User user = null;
        listUser = new ArrayList<User>();

        //QUERY TO SELECT

        //   Cursor cursor = db.rawQuery("SELECT "+Utilidades.NAME_FIELD+", "+Utilidades.PHONE_FIELD+
        //        ", "+ Utilidades.MODEL+ ","+Utilidades.ACTION+" FROM " +Utilidades.Table_Clients + " WHERE "+
        //      Utilidades.DATE_PICKED +" ="+currentDate,null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.Table_Clients + " WHERE "+Utilidades.MODEL + " LIKE  '%"+
                modelName+"%'",null);

        while (cursor.moveToNext()){
            user = new User();
            user.setId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setPhone(cursor.getString(2));
            user.setModel(cursor.getString(3));
            user.setAction(cursor.getString(4));


            listUser.add(user);
        }
        getList();
    }

    private void getList(){
        listInfo= new ArrayList<String>();

        for(int i = 0; i<listUser.size(); i++){
            listInfo.add(listUser.get(i).getName()+ " - "+
                    listUser.get(i).getModel()+" - "+ listUser.get(i).getPhone());
        }
    }
    @Override
    protected void onStart(){
        super.onStart();


    }


}