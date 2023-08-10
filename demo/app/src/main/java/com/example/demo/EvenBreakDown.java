package com.example.demo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvenBreakDown extends AppCompatActivity {

    private int person;
    private double amount;
    String person_tostring;
    String amount_tostring;
    private double even_amount;
    private Button myButton;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_break_down);


        myButton=findViewById(R.id.calculate);
        LinearLayout even=findViewById(R.id.even);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText person_id = findViewById(R.id.person);
                EditText amount_id = findViewById(R.id.amount);

                person_tostring = person_id.getText().toString();
                amount_tostring = amount_id.getText().toString();



                if(!(person_tostring.isEmpty()||amount_tostring.isEmpty())){
                    person = Integer.parseInt(person_tostring);
                    amount = Double.parseDouble(amount_tostring);
                    if(person<=0||amount<=0.0){
                        Toast.makeText(EvenBreakDown.this, "Input cannot be less than or equal to 0!", Toast.LENGTH_SHORT).show();
                    }
                    amount/=person;
                    amount_tostring=String.format("%.2f", amount);

                    TextView hiddenTextView = findViewById(R.id.hiddenTextView);
                    hiddenTextView.setText("Each person have to pay RM"+amount_tostring+".");

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            captureAndSaveScreenshot(even); // Capture the entire layout if needed
                        }
                    }, 1000); // 1-second delay
                }
                else{
                    Toast.makeText(EvenBreakDown.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                }


            }

});
    }

    private void captureAndSaveScreenshot(LinearLayout layout) {
        layout.setDrawingCacheEnabled(true);
        Bitmap screenshotBitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
        layout.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(screenshotBitmap);
        layout.draw(canvas);



        String fileName = "screenshot_" + System.currentTimeMillis() + ".png";
        File rootDirectory = Environment.getExternalStorageDirectory();
        File directory = new File(rootDirectory, "EvenBreakDown");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            screenshotBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Notify the gallery about the new image
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, android.net.Uri.fromFile(file)));

            // Show a Toast to indicate successful saving
            Toast.makeText(this, "Screenshot saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Show a Toast if there's an error while saving
            Toast.makeText(this, "Failed to save screenshot", Toast.LENGTH_SHORT).show();
        }
    }




}