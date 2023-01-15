package cesar.ogdai.clientes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cesar.ogdai.clientes.adapters.ListClientAdapter;
import cesar.ogdai.clientes.entidades.User;
import cesar.ogdai.clientes.utils.Utilidades;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView listViewClients;
    ArrayList<String> listInfo;
    ArrayList<User> listUser;
    public String currentDate;
    public String modelName;
    SQLHelper conn;
    public int opcion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = df.format(c);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();



        View headerView = navigationView.getHeaderView(0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          //      Toast.makeText(getApplicationContext(), "Current Date "+ currentDate, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, RegisterClient.class);
                startActivity(intent);
            }
        });
        conn = new SQLHelper(getApplicationContext(), "bd_clientes", null, 1);
        listViewClients = (ListView) findViewById(R.id.listViewClients);

        //GET CLIENTS
        consultClients();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listInfo);
        listViewClients.setAdapter(adapter);
        listViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = "id: " + listUser.get(position).getId() + "\n";
                info += "name: " + listUser.get(position).getName() + "\n";
                info += "phone: " + listUser.get(position).getPhone() + "\n";
                info += "model: " + listUser.get(position).getModel() + "\n";
                info += "action: " + listUser.get(position).getAction() + "\n";
                info += "date: " + listUser.get(position).getDatepicked() +"\n";

                //           Toast.makeText(getApplicationContext(), ""+info, Toast.LENGTH_SHORT).show();

                User user = listUser.get(position);

                Intent intent = new Intent(MainActivity.this, ClientDetails.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", user);

                intent.putExtras(bundle);


                startActivity(intent);
            }
        });


    }






    private void consultClients(){
        SQLiteDatabase db = conn.getReadableDatabase();

        User user = null;
        listUser = new ArrayList<User>();

        //QUERY TO SELECT

     //   Cursor cursor = db.rawQuery("SELECT "+Utilidades.NAME_FIELD+", "+Utilidades.PHONE_FIELD+
        //        ", "+ Utilidades.MODEL+ ","+Utilidades.ACTION+" FROM " +Utilidades.Table_Clients + " WHERE "+
          //      Utilidades.DATE_PICKED +" ="+currentDate,null);
       Cursor cursor = db.rawQuery("SELECT * FROM "+ Utilidades.Table_Clients + " WHERE "+Utilidades.DATE_PICKED + "= '"+
               currentDate+"'",null);

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
                    listUser.get(i).getModel());
        }
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //  if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_all) {
            Intent intent = new Intent(MainActivity.this, ListClientActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_model) {
            searchData("model");

        }
        else if(id == R.id.nav_phone){
            searchData("phone");
        }else if(id == R.id.nav_name){
            searchData("name");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(){
        AlertDialog.Builder dialog =  new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        final View myview = inflater.inflate(R.layout.client_edit,null);

        dialog.setView(myview);

        final AlertDialog dialog1 = dialog.create();
        dialog1.setCancelable(true);
        dialog1.show();

        Button search, cancel;
        final EditText model;

        final CalendarView calendarView;
        calendarView = myview.findViewById(R.id.calendarEdit);

        calendarView.setVisibility(View.INVISIBLE);

        model = myview.findViewById(R.id.clientEdit);
        search = myview.findViewById(R.id.clientAccept);
        cancel = myview.findViewById(R.id.clientCancel);

        model.setHint("Ingresa el modelo");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelName = model.getText().toString();

                Intent intent = new Intent(MainActivity.this, SearchModel.class);
                intent.putExtra("model", modelName.toLowerCase());
                startActivity(intent);

            }
        });

    }


    private void searchPhone(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        final View myview = inflater.inflate(R.layout.input_phone, null);
        dialog.setView(myview);

        final AlertDialog dialog1 = dialog.create();
        dialog1.setCancelable(false);
        dialog1.show();

        Button search, cancel;
        final EditText phone;

        phone = myview.findViewById(R.id.phoneN);
        search = myview.findViewById(R.id.phoneAccept);
        cancel = myview.findViewById(R.id.phoneCancel);



       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String phoneNumber = "";
               phoneNumber = phone.getText().toString();
               if(phone.getText().toString().length() == 10){
                   Intent intent = new Intent(MainActivity.this,SearchPhone.class );
                   intent.putExtra("phone", phoneNumber);
                   startActivity(intent);
               }
               else{
                   Toast.makeText(getApplicationContext(), "Verifique el número", Toast.LENGTH_SHORT).show();
               }
           }
       });

       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog1.dismiss();
           }
       });

    }


    private void searchData(String param){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        final View myview = inflater.inflate(R.layout.input_phone, null);
        dialog.setView(myview);

        final AlertDialog dialog1 = dialog.create();
        dialog1.setCancelable(false);
        dialog1.show();

        Button search, cancel;
        TextView title;
        final EditText input;

        input = myview.findViewById(R.id.phoneN);
        search = myview.findViewById(R.id.phoneAccept);
        cancel = myview.findViewById(R.id.phoneCancel);
        title = myview.findViewById(R.id.phone_text);

        if(param == "phone"){
            title.setText("Ingrese el modelo");
            input.setHint("Ingrese el modelo");
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    String phoneNumber = input.getText().toString();
                    if(input.getText().toString().length() == 10){
                        Intent intent = new Intent(MainActivity.this,SearchPhone.class );
                        intent.putExtra("phone", phoneNumber);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Verifique el número", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });
        }
        else if(param == "model"){
            title.setText("Ingrese el modelo");
            input.setHint("Ingrese el modelo");
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //input.setInputType(InputType.TYPE_CLASS_TEXT);
                    String model = input.getText().toString();
                    if(model.length() > 0){
                        Intent intent = new Intent(MainActivity.this, SearchModel.class);
                        intent.putExtra("model", model);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Verifique el campo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });
        }
        else if(param == "name"){
            title.setText("Ingrese el nombre");
            input.setHint("Ingrese el nombre");
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = input.getText().toString();
                    if(name.length() > 0){
                        Intent intent = new Intent(MainActivity.this, SearchName.class);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Verifique el campo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });
        }

    }


}
