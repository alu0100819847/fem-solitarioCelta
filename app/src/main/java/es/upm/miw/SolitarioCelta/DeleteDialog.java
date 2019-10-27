package es.upm.miw.SolitarioCelta;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import es.upm.miw.SolitarioCelta.models.RepositorioResults;


public class DeleteDialog extends DialogFragment {
    private RepositorioResults repositorioResults;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final BestRecords actividad =(BestRecords) getActivity();
        this.repositorioResults = new RepositorioResults(actividad.getApplicationContext());
        builder.setTitle(getString(R.string.delete))
                .setMessage(getString(R.string.deleteDialog))
                .setPositiveButton(
                        getString(R.string.deleteSi),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                repositorioResults.deleteAll();

                            }
                        }

                )
                .setNegativeButton(
                        getString(R.string.deleteNo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Acción opción No
                            }
                        }
                );
        return builder.create();
    }
}