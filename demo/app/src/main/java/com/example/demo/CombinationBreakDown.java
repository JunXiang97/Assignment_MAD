package com.example.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;

public class CombinationBreakDown extends AppCompatActivity {

    private EditText personInput;
    private LinearLayout vertical;
    private int round = 0;

    private int[] num_input_id;
    private EditText total;
    Double total_input=0.0;
    String currentText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination_break_down);
        personInput = findViewById(R.id.person_combination);
        vertical = findViewById(R.id.vertical);
        total=findViewById(R.id.amount_combination);
        personInput.addTextChangedListener(combination_input);

    }

    private TextWatcher combination_input = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (round > 0) {
                vertical.removeAllViews();


            }
            String person_Input_string = personInput.getText().toString();

            if (!person_Input_string.isEmpty()) {
                int num_person = Integer.parseInt(person_Input_string);
                int[] num_input_id=new int[num_person]; //IDs
                int[] name_input_id=new int[num_person];


                if (num_person <= 0) {
                    TextView warning = new TextView(CombinationBreakDown.this);
                    warning.setText("Please enter valid number!");
                    warning.setTextColor(0xFFFF0000);
                    warning.setTextSize(12f);
                    vertical.addView(warning);
                } else {
                    for (int j = 0; j < num_person; j++) {
                        //Create horizontal layout
                        LinearLayout horizontal = new LinearLayout(CombinationBreakDown.this);
                        LinearLayout horizontal_name = new LinearLayout(CombinationBreakDown.this);
                        horizontal.setOrientation(LinearLayout.HORIZONTAL);
                        horizontal_name.setOrientation(LinearLayout.HORIZONTAL);
                        horizontal.setId(20000+j);
                        horizontal_name.setId(30000+j);

                        TextView text = new TextView(CombinationBreakDown.this);
                        EditText input = new EditText(CombinationBreakDown.this);
                        TextView name_text = new TextView(CombinationBreakDown.this);
                        EditText name = new EditText(CombinationBreakDown.this);
                        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        input.setLayoutParams(layoutParams);
                        name.setLayoutParams(layoutParams);
                        input.setId(10000 + j);
                        name.setId(40000+j);
                        num_input_id[j]=10000+j; //store IDs
                        name_input_id[j]=40000+j;
                        name_text.setText("Please enter your name: ");
                        text.setText( " Person " +(j+1)+" 's amount : ");
                        text.setPadding(5,5,50,5);
                        input.setPadding(15,30,10,30);
                        name.setPadding(15,30,10,30);
                        name.setBackgroundResource(R.drawable.rounded_edittext_background);
                        input.setBackgroundResource(R.drawable.rounded_edittext_background);
                        horizontal_name.addView(name_text);
                        horizontal_name.addView(name);
                        horizontal.addView(text);
                        horizontal.addView(input);
                        horizontal.setPadding(0,15,0,50);
                        vertical.addView(horizontal_name);
                        vertical.addView(horizontal);

                    }
                    round++;

                    TextWatcher check_combination_input = new TextWatcher() {
                        String combination_input_string;
                        String nameInput;
                        Double total_int;


                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String total_string=total.getText().toString();
                            total_int=Double.parseDouble(total_string);

                            String[] name_stored=new String[num_person];
                            Double[] combination_input_stored=new Double[num_person];
                            Double input_total=0.0;
                            Double different;

                             for(int i=0;i<num_person;i++){
                                 EditText combination_input=findViewById(num_input_id[i]);
                                 EditText name_input=findViewById(name_input_id[i]);
                                 TextView warning=new TextView(CombinationBreakDown.this);

                                 LinearLayout horizontal_warning=findViewById(20000+i);


                                 nameInput=name_input.getText().toString();
                                 combination_input_string=combination_input.getText().toString();


                                 if(!(combination_input_string.isEmpty())){

                                     combination_input_stored[i]=Double.parseDouble(combination_input_string);
                                     name_stored[i]=nameInput;
                                     input_total+=combination_input_stored[i];
                                     different=input_total-total_int;



                                         for(int j=0;j<num_person;j++){
                                             EditText combination_input_check=findViewById(num_input_id[i]);



                                             if(input_total>total_int){
                                                 GradientDrawable borderDrawable = new GradientDrawable();
                                                 borderDrawable.setStroke(2, 0xFFFF0000); // Set border color to red
                                                 borderDrawable.setCornerRadius(8); // Set corner radius
                                                 combination_input_check.setBackground(borderDrawable);
                                                 Toast.makeText(CombinationBreakDown.this, "The combination amount exceeded the total amount by RM"+String.format("%.2f", different), Toast.LENGTH_SHORT).show();

                                             }
                                             else{
                                                 GradientDrawable borderDrawable = new GradientDrawable();
                                                 borderDrawable.setStroke(2, 0xFF6BD92E); //
                                                 borderDrawable.setCornerRadius(8); // Set corner radius
                                                 combination_input_check.setBackground(borderDrawable);

                                         }
                                     }
                                         TextView display=new TextView(CombinationBreakDown.this);
                                     Button submit=findViewById(R.id.Submit);

                                     TextView display_text=findViewById(R.id.display_text);
                                     LinearLayout combination=findViewById(R.id.verticalCombination);



                                     submit.setOnClickListener(new View.OnClickListener() {
                                         EditText total_EditText=findViewById(R.id.amount_combination);
                                         Double total_amount=Double.parseDouble(total_EditText.getText().toString());
                                         int click=0;

                                         @Override
                                         public void onClick(View view) {
                                             if(click>0){
                                                 display_text.setText("");
                                                 total_input=0.0;
                                             }


                                             for(int j=0;j<num_person;j++){
                                                 total_input+=combination_input_stored[j];
                                             }





                                             if((total_input.compareTo(total_amount))!=0){
                                                 Toast.makeText(CombinationBreakDown.this, "The combination amount is not equal to the total amount! The diffences is RM"+String.format("%.2f", (total_amount-total_input)), Toast.LENGTH_SHORT).show();
                                                 total_input=0.0;
                                             }
                                             else{
                                                 for(int j=0;j<num_person;j++){
                                                     currentText+=name_stored[j]+":\t\tRM"+String.format("%.2f", combination_input_stored[j])+"\n";
                                                 }
                                                 display_text.setText(currentText);
                                                 display_text.setTextSize(20.0f);
                                             InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                             imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                                                 new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         captureAndSaveScreenshot(combination); // Capture the entire layout if needed
                                                     }
                                                 }, 1000); // 1-second delay
                                             }
                                             click++;




                                         }

                                     });

                                 }

                             }


                        }
                    };
                    EditText[] textchange=new EditText[num_person];
                    for(int j=0;j<num_person;j++){
                        textchange[j]=findViewById(10000+j);
                        textchange[j].addTextChangedListener(check_combination_input);
                    }



                }

            }


        }

        @Override
        public void afterTextChanged(Editable editable) {


        }


    };

    private void captureAndSaveScreenshot(LinearLayout layout) {
        layout.setDrawingCacheEnabled(true);
        Bitmap screenshotBitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.ARGB_8888);
        layout.setDrawingCacheEnabled(false);
        Canvas canvas = new Canvas(screenshotBitmap);
        layout.draw(canvas);



        String fileName = "screenshot_" + System.currentTimeMillis() + ".png";
        File rootDirectory = Environment.getExternalStorageDirectory();
        File directory = new File(rootDirectory, "CombinationeBreakDown");

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

