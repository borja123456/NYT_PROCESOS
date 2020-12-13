package com.example.task_ovid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lista;
    private ArrayList<String> tareas;


    private static int maxExperiencia = 100;
    public  static int maxVida = 100;
    private static int vida = maxVida;
    private int experienciaTotal = 0;
    private static ProgressBar bv;
    private TextView nivelTexto;
    private static int experiencia=0;
    private static int nivel=1;
    public  static ProgressBar be;
    private static TextView monedas;
    private ArrayAdapter<String> tareasAdapter;
    private static double resistencia=1;
    private static int monedasUsuario=0;
    private static int restaAux;


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallaprincipal);
        lista = findViewById(R.id.lista);
        this.tareas = new ArrayList<>();
        llenarTareas();
        tareasAdapter = new ArrayAdapter(this, R.layout.rowtext, tareas);
        lista.setAdapter(tareasAdapter);
        lista.setOnItemClickListener(this);


    }

    private void llenarTareas(){ //este método es para rellenar las listas, el 0 simboliza el contador
        tareas.add("+ salir con mascarilla 0");
        tareas.add("- olvidarse la mascarilla 0");
        tareas.add("+ lavarse las manos 0");
        tareas.add("+ desinfectarse 0");
        tareas.add("* Hacer PCR 0");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String t = parent.getItemAtPosition(position).toString();
        //divide el string para poder cambiar el contador
        String[] parts = t.split(" ");
        //incrementar el contador y actualizar el string
        int cont = Integer.parseInt(parts[parts.length-1]) + 1;
        parts[parts.length-1] = String.valueOf(cont);
        //une el string de nuevo para colocarlo en la lista
        String string = "";
        for (int i = 0; i <= parts.length-1; i++){
            if (i == 0){
                string = parts[i];
            }else{
                string = string + " "+parts[i];
            }
        }
        //actualiza la lista
        tareas.set(position,string);
        tareasAdapter.notifyDataSetChanged();

        bv=(ProgressBar)findViewById(R.id.Vida);
        bv.setMax(maxVida);
        if (t.contains("-")){
            restaAux = (int)(25 * resistencia);
            int vidaAux = vida - restaAux;
            setVida(vidaAux);
            bv.setProgress(vida,true);
            if (vidaAux<=0){
                Toast.makeText(getApplicationContext(), "Con estos habitos te vas a contagiar ;(", Toast.LENGTH_LONG).show();
            }
        }else{
            incrementarExperiencia(t);
        }

    }

    public void incrementarExperiencia(String t){
        be=(ProgressBar)findViewById(R.id.Experiencia);
        be.setMax(maxExperiencia);
        if(t.contains("+")){
            experiencia += 10;
            experienciaTotal +=10;

        }else{
            experiencia += 50;
            experienciaTotal += 10;
        }
        subirNivel();
        be.setProgress(experiencia,true);
    }

    public void subirNivel(){
        if (experiencia>=maxExperiencia){
            int extra = experiencia-maxExperiencia;
            nivel ++;
            experiencia = extra;
            maxExperiencia += 20;
            be.setMax(maxExperiencia);
            nivelTexto = (TextView)findViewById(R.id.NombreNivel);
            nivelTexto.setText("NIVEL "+nivel);
            Toast.makeText(getApplicationContext(), "Has subido de nivel. Enhorabuena", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Has ganado 100 monedas", Toast.LENGTH_LONG).show();

            incrementarMonedas(100);
        }
    }

    public void incrementarMonedas(int cantidad){
        monedas = (TextView)findViewById(R.id.monedasActuales);
        monedasUsuario+=cantidad;
        monedas.setText(""+ monedasUsuario);

    }


    public static void setVida(int v) {
        if (vida>=0) {
            vida = v;
        }else{
            vida = 0;
        }
        bv.setProgress(vida,true);
    }
    public static int getMaxExperiencia(){
        return maxExperiencia;
    }
    public static int getVida(){
        return vida;
    }
    public static int getNivel(){
        return nivel;
    }
    public static int getExperiencia(){
        return experiencia;
    }

    public static int getMaxVida() {
        return maxVida;
    }

    public static int getMonedasUsuario() {
        return monedasUsuario;
    }

    public  static void setMonedasUsuario(int m) {
        monedasUsuario = m;
        monedas.setText(""+ monedasUsuario);
    }

    public static double getResistencia() {
        return resistencia;
    }

    public  void setResistencia(double resistencia) {
        this.resistencia = resistencia;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(R.id.main==id) {
            Intent intent= new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }else if (R.id.Perfil==id) {
            Toast.makeText(getApplicationContext(), "En Construccion...", Toast.LENGTH_SHORT).show();

        }else if (R.id.Tienda== id) {
            Intent intent= new Intent(this,TiendaBeta.class);
            startActivity(intent);
        }else if(R.id.Salir==id) {
            finishAffinity();
        }

                return super.onOptionsItemSelected(item);

    }





}