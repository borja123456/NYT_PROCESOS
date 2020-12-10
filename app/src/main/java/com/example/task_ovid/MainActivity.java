package com.example.task_ovid;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lista;
    private ArrayList<String> tareas;


    private int maxExperiencia = 100;
    public  int maxVida = 100;
    private int vida = maxVida;
    private int experienciaTotal = 0;
    ProgressBar bv;
    TextView nivelTexto;
    private int experiencia=0;
    private int nivel=1;
    ProgressBar be;
    ArrayAdapter<String> tareasAdapter;

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
            int vidaAux = vida - 25;
            setVida(vidaAux);
            bv.setProgress(vida,true);
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
            maxExperiencia += 50;
            be.setMax(maxExperiencia);
            nivelTexto = (TextView)findViewById(R.id.NombreNivel);
            nivelTexto.setText("NIVEL "+nivel);
        }
    }


    public void setVida(int vida) {
        if (vida>=0) {
            this.vida = vida;
        }else{
            this.vida = 0;
        }
    }
    public int getMaxExperiencia(){
        return maxExperiencia;
    }
    public int getVida(){
        return vida;
    }
    public int getNivel(){
        return nivel;
    }
    public int getExperiencia(){
        return experiencia;
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
            startActivity(intent);
        }else if (R.id.Perfil==id) {
            Toast.makeText(getApplicationContext(), "En Construccion...", Toast.LENGTH_SHORT).show();

        }else if (R.id.Tienda== id) {
            Intent intent2= new Intent(this,TiendaBeta.class);
            startActivity(intent2);
        }else if(R.id.Salir==id) {
            finishAffinity();
        }

                return super.onOptionsItemSelected(item);

    }



}