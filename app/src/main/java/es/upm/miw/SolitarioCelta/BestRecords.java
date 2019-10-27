package es.upm.miw.SolitarioCelta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class BestRecords extends AppCompatActivity {

    private ListView lvListado;
    private Bundle bundle;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.record_list);

        this.lvListado =(ListView) findViewById(R.id.lvListadoElementos);
        this.bundle = this.getIntent().getExtras();
        this.data = bundle.getStringArrayList(getString(R.string.resultkey));

/*
        RecordAdapter recordAdapter = new RecordAdapter(getApplicationContext(), R.layout.record_list, data);
        lvListado.setAdapter(recordAdapter);*/

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                this.data
        );
        lvListado.setAdapter(adaptador);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.records_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                DeleteDialog dialogFragment = new DeleteDialog();
                dialogFragment.show(getSupportFragmentManager(), "Delete");
                break;
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(BestRecords.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
        }
        return true;
    }
}
