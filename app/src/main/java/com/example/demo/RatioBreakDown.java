package com.example.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RatioBreakDown extends AppCompatActivity {
    private EditText personInput;
    private LinearLayout vertical;
    private int round = 0;



    private int[] num_input_id;
    private EditText total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratio_break_down);
        personInput = findViewById(R.id.person_ratio);
        vertical = findViewById(R.id.parentContainer);
        total=findViewById(R.id.total_amount);
        personInput.addTextChangedListener(ratio_input);




    }

    private TextWatcher ratio_input = new TextWatcher() {
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
                int[] num_input_id=new int[num_person];
                Double[] num_input_percentage=new Double[num_person];
                int[] percentage_person = new int[num_person];
                Double[] total_paid=new Double[num_person];

                if (num_person <= 0) {
                    TextView warning = new TextView(RatioBreakDown.this);
                    warning.setText("Please enter valid number!");
                    warning.setTextColor(0xFFFF0000);
                    warning.setTextSize(12f);
                    vertical.addView(warning);
                } else {
                    for (int j = 0; j < num_person; j++) {
                        //Create horizontal layout
                        ScrollView scrollview=new ScrollView(RatioBreakDown.this);
                        LinearLayout horizontal = new LinearLayout(RatioBreakDown.this);
                        LinearLayout vertical_holder = new LinearLayout(RatioBreakDown.this);
                        LinearLayout horizontal_name=new LinearLayout(RatioBreakDown.this);
                        horizontal.setOrientation(LinearLayout.HORIZONTAL);
                        horizontal_name.setOrientation(LinearLayout.HORIZONTAL);


                        TextView text = new TextView(RatioBreakDown.this);
                        EditText input = new EditText(RatioBreakDown.this);
                        TextView name_text= new TextView(RatioBreakDown.this);
                        EditText name= new EditText(RatioBreakDown.this);

                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );

                        input.setLayoutParams(layoutParams);
                        input.setId(10000 + j);
                        name.setLayoutParams(layoutParams);
                        name.setId(30000+j);
                        num_input_id[j]=10000+j; //store IDs
                        name_text.setText("Please enter your name: ");
                        name_text.setPadding(5,5,50,5);
                        name.setPadding(15,30,10,30);
                        text.setText( " Person "+(j+1)+" 's percentage(%) : ");
                        input.setPadding(15,30,10,30);

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

                    Button calculateButton = findViewById(R.id.calculateButton); // Assuming you have a Button with this ID
                    TextView display=findViewById(R.id.display_text);
                    LinearLayout display_layout=findViewById(R.id.Display);


                    calculateButton.setOnClickListener(new View.OnClickListener() {
                        int click_times=0;
                        String[] name_stored=new String[num_person];
                        @Override
                        public void onClick(View v) {
                            Double total_percentage=0.0;

                            for(int i=0;i<num_person;i++){
                                EditText percentage_input=findViewById(num_input_id[i]);
                                EditText name_input=findViewById(30000+i);
                                String percentage_input_string=percentage_input.getText().toString();
                                String name_input_string=name_input.getText().toString();
                                name_stored[i]=name_input_string;
//                                if(percentage_input_string.isEmpty()||name_input_string.isEmpty()){
//                                    Toast.makeText(RatioBreakDown.this, "The input cannot be empty!", Toast.LENGTH_SHORT).show();
//                                    i=0;
//                                    continue;
//                                }

                                num_input_percentage[i]=Double.parseDouble(percentage_input_string);
                                String total_string=total.getText().toString();
                                Double calculateAmount=(Double.parseDouble(total_string)*(num_input_percentage[i]/100));
                                total_paid[i]=calculateAmount;



                            }

                            String currentText=display.getText().toString();

                            for(int i=0;i<num_person;i++){
                                total_percentage+=num_input_percentage[i];
                            }

                            if(total_percentage!=100){
                                Toast.makeText(RatioBreakDown.this, "The total percentage is not 100%!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                for(int i=0;i<num_person;i++){
                                    currentText+=name_stored[i]+" 's total amount: RM"+String.format("%.2f", total_paid[i])+"\n";
                                }
                                display.setText(currentText);
                                LinearLayout ratio=findViewById(R.id.ratio);
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        captureAndSaveScreenshot(ratio); // Capture the entire layout if needed
                                    }
                                }, 1000); // 1-second delay
                            }



                        }
                    });


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
        File directory = new File(rootDirectory, "PercentageBreakDown");

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














