package es.upm.miw.SolitarioCelta.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Result implements Parcelable {

    private int id;
    private String nombre;
    private Date date;
    private int count;

    public Result(int id, String nombre, Date date, int count) {
        this.id = id;
        this.nombre = nombre;
        this.date = date;
        this.count = count;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    protected Result(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeInt(count);
        dest.writeSerializable(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", date=" + date +
                ", count=" + count +
                '}';
    }
}

