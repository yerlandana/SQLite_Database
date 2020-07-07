package com.dana.workwithsql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdd, btnRead, btnClear;
    EditText name, email;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String n = name.getText().toString();
        String e = email.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues cValues = new ContentValues();

        switch (v.getId()) {
            case R.id.btnAdd:
                cValues.put(DBHelper.KEY_NAME, n);
                cValues.put(DBHelper.KEY_MAIL, e);

                database.insert(DBHelper.TABLE_CONTACTS, null, cValues);
                break;
            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null); //sort wanted data
                Log.e("SIZE", String.valueOf(cursor.getCount()));

                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        try {
                            Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                    ", name = " + cursor.getString(nameIndex) +
                                    ", email = " + cursor.getString(emailIndex));
                        } catch (Exception e1) {
                            Log.d("ERROR", "HERE", e1);
                        }
                    } while (cursor.moveToNext());
                } else {
                    Log.d("mLOG", "0 rows");
                }
                break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;
        }
        dbHelper.close();
    }
}
