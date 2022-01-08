package com.ahmedhossny61.movieapp.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ahmedhossny61.movieapp.Film;
import java.util.ArrayList;

public class dbHelper extends SQLiteOpenHelper {

    public dbHelper(Context c) {
        super(c, "task.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table Film (id INTEGER primary key, title Text, language Text, releaseDate Text, rate Text, voteCount INTEGER,imageUrl Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertFilm(int id,String title,String language ,String releaseDate,String rate, int voteCount, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT into Film (id,title,language,releaseDate,rate,voteCount,imageUrl) VALUES (" + id+ ", '"+title+"', '"+language+"','"+releaseDate+"', '"+rate+"', "+voteCount+", '"+imageUrl+"'); ");
    }

    public ArrayList<Film> AllFilms() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Film> Films = new ArrayList<Film>();
        Cursor c = db.rawQuery("Select * From Film", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int id = c.getInt(0);
            String title=c.getString(1);
            String language=c.getString(2);
            String releaseDate=c.getString(3);
            String rate=c.getString(4);
            int voteCount=c.getInt(5);
            String imageUrl=c.getString(6);
            Films.add(new Film(id,title,language,releaseDate,rate,voteCount,imageUrl));
            c.moveToNext();
        }
        return Films;

    }
    public void DeleteFilm(int id ){

    }
    public int selectWith_id(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        int count=0;
        Cursor c = db.rawQuery("Select * From Film where id ="+id, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int _id = c.getInt(0);
            count++;
            c.moveToNext();
        }
        return count;
    }

}
