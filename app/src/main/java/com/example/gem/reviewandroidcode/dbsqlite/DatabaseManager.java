package com.example.gem.reviewandroidcode.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gem.reviewandroidcode.pojo.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "schoolManager";
  private static final int DATABASE_VERSION = 1;
  private static final String TABLE_NAME = "students";

  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_ADDRESS = "address";
  private static final String KEY_NUM_PHONE = "num_phone";

  private Context context;

  private String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
      KEY_ID + " integer primary key, " +
      KEY_NAME + " TEXT, " +
      KEY_ADDRESS + " TEXT, " +
      KEY_NUM_PHONE + " TEXT)";

  /*
   * Khi thay doi DATABASE_VERSION thi onUpgrade moi duoc goi
   */
  public DatabaseManager(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
//    String createStudentsTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_NUM_PHONE);
    db.execSQL(createTable);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String dropStudentsTable = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
    db.execSQL(dropStudentsTable);
    onCreate(db);
  }

  // add new record
  public void addStudent(Student student) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, student.getName());
    values.put(KEY_ADDRESS, student.getAddress());
    values.put(KEY_NUM_PHONE, student.getNumPhone());

    db.insert(TABLE_NAME, null, values);
    db.close();
  }

  // truy van du lieu trong bang su dung Cursor
  public Student getStudent(int id) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
    if (cursor != null)
      cursor.moveToFirst();
    Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
    return student;
  }

  // get all student
  public List<Student> getAllStudent() {
    List<Student> students = new ArrayList<>();
    String query = "SELECT * FROM " + TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(query, null);
//    if (cursor != null)
//      cursor.moveToFirst();
//    while (cursor.isAfterLast() == false) {
//      Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//      students.add(student);
//      cursor.moveToNext();
//    }
    if (cursor.moveToFirst()) {
      do {
        Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        students.add(student);
      } while (cursor.moveToNext());
    }
    db.close();
    return students;
  }

  // Update du lieu trong bang
  public void updateStudent(Student student) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(KEY_NAME, student.getName());
    values.put(KEY_ADDRESS, student.getAddress());
    values.put(KEY_NUM_PHONE, student.getNumPhone());

    db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[]{
        String.valueOf(student.getId())
    });
    db.close();
  }

  // Delete record
  public void deleteStudent(int id) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{
        String.valueOf(id)
    });
    db.close();
  }
}
