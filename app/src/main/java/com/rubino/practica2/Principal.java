package com.rubino.practica2;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Principal extends Activity {

    private GestorJugador gj;
    private EditText etNombre, etTelefono, etFecha;
    private Adaptador ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        gj = new GestorJugador(this);
        etNombre = (EditText)findViewById(R.id.etNombre);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
        etFecha = (EditText)findViewById(R.id.etFecha);

        final ListView lv = (ListView)findViewById(R.id.lvJugadores);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor)lv.getItemAtPosition(position);
                Jugador j = GestorJugador.getRow(cursor);
                gj.delete(j);
                ad.changeCursor(gj.getCursor());
                tostada("Eliminado jugador con ID " + j.getId());
                return false;
            }
        });
        gj.open();


        Cursor c = gj.getCursor();
      
        ad = new Adaptador(this, c);
        lv.setAdapter(ad);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gj.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
    }


    public void alta(View v){
        String nombre, telefono, fecha;
        nombre = etNombre.getText().toString();
        telefono = etTelefono.getText().toString();
        fecha = etFecha.getText().toString();
        Jugador j = new Jugador(nombre, telefono, fecha);
        long id = gj.insert(j);
        tostada("El jugador " + j + " se ha creado con un ID " + id);
        Cursor c = gj.getCursor();
        ad.changeCursor(c);
        vaciarCampos();
    }

    public void vaciarCampos(){
        etNombre.setText("");
        etTelefono.setText("");
        etFecha.setText("");
    }


    public void tostada(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
