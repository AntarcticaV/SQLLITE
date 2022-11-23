package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private GestureDetectorCompat lSwipeDetector;

    RelativeLayout main_layout;
    TextView tvTxt;
    int i;
    Intent intent;

    private static final int SWIPE_MIN_DISTANCE = 130;
    private static final int SWIPE_MAX_DISTANCE = 300;
    private static final int SWIPE_MIN_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        intent = new Intent(this, MainActivity.class);
        i = 1;
        lSwipeDetector = new GestureDetectorCompat(this, new MyGestureListener());
        main_layout = (RelativeLayout) findViewById(R.id.main_layout2);
//        tvTxt = (TextView) findViewById(R.id.tvTxt);
//        tvTxt.setText("" + i);

        main_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return lSwipeDetector.onTouchEvent(event);
            }
        });

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER, UNIQUE(name))");
        db.execSQL("INSERT OR IGNORE INTO users VALUES ('Tom Smith', 23), ('John Dow', 31);");

        Cursor query = db.rawQuery("SELECT * FROM users;", null);
        TextView textView = findViewById(R.id.textView2);
        textView.setText("");
        while(query.moveToNext()){
            String name = query.getString(0);
            int age = query.getInt(1);
            textView.append("Name: " + name + " Age: " + age + "\n");
        }
        query.close();
        db.close();
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_DISTANCE)
                return false;
//            if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
//                i++;
//                tvTxt.setText("" + i);
//            }
            if (e1.getX() -e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_MIN_VELOCITY){
                startActivity(intent);
            }
            return false;
        }
    }
}