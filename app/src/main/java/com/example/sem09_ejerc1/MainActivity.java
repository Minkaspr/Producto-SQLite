package com.example.sem09_ejerc1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_codigo,et_nombre,et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = findViewById(R.id.editTextCodigo);
        et_nombre = findViewById(R.id.editTextNombre);
        et_precio = findViewById(R.id.editTextPrecio);
    }

    /**
     * Método para registrar un producto
     */

    public void Registrar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos",null,1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String nombre = et_nombre.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()){
            ContentValues query = new ContentValues();
            query.put("codigo",codigo);
            query.put("nombre",nombre);
            query.put("precio",precio);

            bd.insert("producto",null,query);
            bd.close();
            et_codigo.setText("");
            et_nombre.setText("");
            et_precio.setText("");

            Toast.makeText(this, "Se registró un producto", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
        }

    }
}