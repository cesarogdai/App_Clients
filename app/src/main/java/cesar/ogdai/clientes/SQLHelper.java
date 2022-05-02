package cesar.ogdai.clientes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cesar.ogdai.clientes.utils.Utilidades;

public class SQLHelper  extends SQLiteOpenHelper {

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Utilidades.CREATE_TABLE_CLIENTS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS clientes");
        onCreate(db);
    }
}
