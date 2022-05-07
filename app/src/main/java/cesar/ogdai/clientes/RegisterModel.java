package cesar.ogdai.clientes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;

public class RegisterModel extends AppCompatActivity {
    private static final String[] BRANDS = new String[]{
            "HONDA", "KIA", "FORD", "HYUNDAI", "MAZDA", "RENAULT", "TOYOTA", "CHEVROLET"
    };
    private static final String[] STATUS = new String[]{
            "DISPONIBLE", "APARTADO"
    };
    private Button accept;
    public String selectedBrand = "";
    public String selectedStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_model);
        //setContentView(R.layout.activity_list_client);
        setTitle(R.string.add_car_data); //add_car_data
      //  String[] brands = getResources().getStringArray(R.array.brands);
        accept = findViewById(R.id.data_car_add_btn_accept);
        AutoCompleteTextView brands = findViewById(R.id.actv_brand);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, BRANDS);
        brands.setAdapter(adapter);
        AutoCompleteTextView status = findViewById(R.id.actv_status);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, STATUS);
        status.setAdapter(adapter1);

       brands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               selectedBrand = (String) parent.getItemAtPosition(position);
           }
       });
       status.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               selectedStatus = (String) parent.getItemAtPosition(position);
           }
       });
       accept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getApplicationContext(), "BRAND: " + selectedBrand + "\n STATUS: "+ selectedStatus , Toast.LENGTH_SHORT).show();
           }
       });
    }
}