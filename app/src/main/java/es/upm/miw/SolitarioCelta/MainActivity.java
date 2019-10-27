package es.upm.miw.SolitarioCelta;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import es.upm.miw.SolitarioCelta.models.RepositorioResults;

public class MainActivity extends AppCompatActivity {

    SCeltaViewModel miJuego;
    public final String LOG_KEY = "MiW";

    public final String NOMBRE_FICHERO= "save.txt";

    public final String RESULT_KEY = "RESULT_DATA";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miJuego = ViewModelProviders.of(this).get(SCeltaViewModel.class);
        mostrarTablero();
    }

    /**
     * Se ejecuta al pulsar una ficha
     * Las coordenadas (i, j) se obtienen a partir del nombre del recurso, ya que el botón
     * tiene un identificador en formato pXY, donde X es la fila e Y la columna
     * @param v Vista de la ficha pulsada
     */
    public void fichaPulsada(@NotNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';   // fila
        int j = resourceName.charAt(2) - '0';   // columna

        Log.i(LOG_KEY, "fichaPulsada(" + i + ", " + j + ") - " + resourceName);
        miJuego.jugar(i, j);
        Log.i(LOG_KEY, "#fichas=" + miJuego.numeroFichas());

        mostrarTablero();
        if (miJuego.juegoTerminado()) {
            // TODO guardar puntuación
            new AlertDialogFragment().show(getFragmentManager(), "ALERT_DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        String strRId;
        String prefijoIdentificador = getPackageName() + ":id/p"; // formato: package:type/entry
        int idBoton;

        for (int i = 0; i < JuegoCelta.TAMANIO; i++)
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                strRId = prefijoIdentificador + i + j;
                idBoton = getResources().getIdentifier(strRId, null, null);
                if (idBoton != 0) { // existe el recurso identificador del botón
                    button = findViewById(idBoton);
                    button.setChecked(miJuego.obtenerFicha(i, j) == JuegoCelta.FICHA);
                }
            }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcAjustes:
                startActivity(new Intent(this, SCeltaPrefs.class));
                return true;
            case R.id.opcAcercaDe:
                startActivity(new Intent(this, AcercaDe.class));
                return true;
            case R.id.opcReiniciarPartida:
                this.miJuego.reiniciar();
                this.mostrarTablero();
                return true;
            case R.id.opcGuardarPartida:
                this.saveGame();
                return true;
            case R.id.opcRecuperarPartida:
                this.loadGame();
                this.mostrarTablero();
                return true;
            case R.id.opcMejoresResultados:
                RepositorioResults repo = new RepositorioResults(getApplicationContext());
                Log.i(LOG_KEY, repo.readString().toString());
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(getString(R.string.resultkey), repo.readString());
                Intent intent = new Intent(getApplicationContext(), BestRecords.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            // TODO!!! resto opciones

            default:
                Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.txtSinImplementar),
                        Snackbar.LENGTH_LONG
                ).show();
        }
        return true;
    }

    public void saveGame(){
        String cadena = this.miJuego.serializaTablero();
        try {
            FileOutputStream fos = openFileOutput(this.NOMBRE_FICHERO, Context.MODE_PRIVATE);
            fos.write(cadena.getBytes());
            fos.close();
            Log.i(LOG_KEY, "Game saved");
        } catch(FileNotFoundException e){
            Log.i(LOG_KEY, "FileNotFoundException");
        } catch(IOException e){
            Log.i(LOG_KEY, "IOException");
        }
    }

    public void loadGame() {
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput(this.NOMBRE_FICHERO)));
            String linea = fin.readLine();
            fin.close();
            Log.i(LOG_KEY, linea);
            miJuego.deserializaTablero(linea);
        }catch(FileNotFoundException e){
            Log.i(LOG_KEY, "FileNotFoundException");
        } catch(IOException e){
            Log.i(LOG_KEY, "IOException");
        }
    }
}
