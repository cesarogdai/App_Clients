package cesar.ogdai.clientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cesar.ogdai.clientes.entidades.User;
import cesar.ogdai.clientes.utils.Utilidades;

public class SearchPhone extends AppCompatActivity {
    ListView listViewPhone;
    ArrayList<String> listInfo;
    ArrayList<User> listUser;
    public String phoneNumber ="";

    SQLHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone);
        setTitle(R.string.search_phone);
        phoneNumber = getIntent().getStringExtra("phone");
        Toast.makeText(getApplicationContext(), "model: "+phoneNumber, Toast.LENGTH_SHORT).show();

        conn = new SQLHelper(getApplicationContext(), "bd_clientes", null, 1);

        consult();



        listViewPhone = (ListView) findViewById(R.id.listViewPhone);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listInfo);
        listViewPhone.setAdapter(adapter);

        listViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = "id: "+listUser.get(position).getId()+"\n";
                info += "name: "+listUser.get(position).getName()+"\n";
                info += "phone: "+listUser.get(position).getPhone()+"\n";
                info += "model: "+listUser.get(position).getModel()+"\n";
                info += "action: "+listUser.get(position).getAction()+"\n";

                User user = listUser.get(position);

                Intent intent = new Intent(SearchPhone.this, ClientDetails.class);
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

        Cursor cursor = db.rawQuery("SELECT * FROM "+Utilidades.Table_Clients+
                " WHERE "+Utilidades.PHONE_FIELD + "='"+phoneNumber+"'", null );

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
        listInfo = new ArrayList<String>();
        for(int i = 0; i<listUser.size(); i++){
            listInfo.add(listUser.get(i).getName() + " - "+
                    listUser.get(i).getModel()+"- "+
                    listUser.get(i).getPhone());
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
}