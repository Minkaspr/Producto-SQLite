package com.example.sem09_ejerc1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

            if (validarCodigo(codigo) && validarNombre(nombre)){
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
                Toast.makeText(this, "Caracteres no permitidos", Toast.LENGTH_SHORT).show();
            }
            

        } else {
            Toast.makeText(this, "Ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Método para buscar un producto
     */

    public void Buscar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos",null,1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()){
            Cursor fila = bd.rawQuery("SELECT nombre, precio FROM producto WHERE codigo="+codigo , null);

            if (fila.moveToFirst()){
                et_nombre.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                et_codigo.setText(codigo);
            } else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }

            // Liberamos recursos
            fila.close();
            // Cerramos conexion con base de datos
            bd.close();

        } else {
            Toast.makeText(this, "Ingrese el codigo", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Método para actualizar un producto
     */

    public void Modificar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"productos",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String nombre = et_nombre.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty()) {

            ContentValues query = new ContentValues();

            query.put("codigo",codigo);
            query.put("nombre",nombre);
            query.put("precio",precio);

            int cantidad = bd.update("producto",query,"codigo="+codigo,null);
            bd.close();
            
            if (cantidad == 1) {
                Toast.makeText(this, "Producto actualizado con exito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo para eliminar un producto
     */

    public void Eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "productos", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if (!codigo.isEmpty()) {
            int cantidad = bd.delete("producto", "codigo="+codigo, null);
            bd.close();
            
            et_codigo.setText("");
            et_nombre.setText("");
            et_precio.setText("");
            
            if (cantidad == 1) {
                Toast.makeText(this, "Producto eliminado con exito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes introducir el codigo del producto", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Metodos para validar
     * regex -> Expresión Regular
     */
    public boolean validarCodigo(String codigo) {
        String regex = "^[0-9]+$";
        return codigo.matches(regex);
    }

    private boolean validarNombre(String nombre) {
        String regex = "^[A-Za-z0-9_\\-\\s]+$";
        return nombre.matches(regex);
    }



}