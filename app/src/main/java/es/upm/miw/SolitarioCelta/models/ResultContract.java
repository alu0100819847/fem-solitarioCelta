package es.upm.miw.SolitarioCelta.models;

import android.provider.BaseColumns;

final public class ResultContract {

    private ResultContract(){}

    public static abstract class tablaResults implements  BaseColumns{
        static final String Table_Name = "result";
        static final String COL_NAME_ID = _ID;
        static final String COL_NAME_NAME = "name";
        static final String COL_NAME_DATE = "date";
        static final String COL_NAME_PIECE = "piece";
    }

}
