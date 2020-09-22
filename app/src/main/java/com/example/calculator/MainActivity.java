package com.example.calculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import javax.microedition.khronos.egl.EGLDisplay;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView txtResult;
    private int number1;
    private int number2;
    EditText editTextName;
    EditText editTextBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd;
        Button btnSubtract;
        Button btnMultiply;
        Button btnDivide;
        Button btnFactorial;
        Button btnNext;

        btnAdd = findViewById(R.id.add);
        btnSubtract = findViewById(R.id.subtract);
        btnMultiply = findViewById(R.id.multiply);
        btnDivide = findViewById(R.id.divide);
        btnFactorial = findViewById(R.id.factorial);
        btnNext = findViewById(R.id.next);
        txtResult = findViewById(R.id.result);
        editTextName = findViewById(R.id.name);
        editTextBirthday = findViewById(R.id.age);
        // Register listener
        btnAdd.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnFactorial.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    // Prevent Non-numeric Data for number1 and number2
    public void PreventNonnumericData() {
        // Get the first and second number.
        EditText editText1 = (EditText)findViewById(R.id.num1);
        EditText editText2 = (EditText)findViewById(R.id.num2);
        String numberString1 = editText1.getText().toString();
        String numberString2 = editText2.getText().toString();
        // "Integer.valueOf" can throw a NumberFormatException.
        try {
            number1 = Integer.valueOf(numberString1);
            number2 = Integer.valueOf(numberString2);
        }catch(NumberFormatException e) {
            AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Wrong Data" ) ;
            builder.setMessage("Prevent non-numeric data" ) ;
            builder.setPositiveButton("Try again" ,  null );
            builder.show();
        }
    }

    //Confirm which button is pressed
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add:
                onClickButtonAdd(view);
                break;
            case R.id.subtract:
                onClickButtonSubstract(view);
                break;
            case R.id.multiply:
                onClickButtonMultiply(view);
                break;
            case R.id.factorial:
                onClickButtonFactorial(view);
                break;
            case R.id.divide:
                onClickButtonDivide(view);
                break;
            case R.id.next:
                startActivityForResult(view);
                break;
            default:
                break;
        }
    }

    //  When the “Add” button is clicked, display the result of adding the values (integers) from the num1
    //  and num2 text field together in the result text field.
    private void onClickButtonAdd(View view) {
        this.PreventNonnumericData();
        txtResult.setText(String.valueOf(number1 + number2));
    }

    //  When the “Subtract”, “Multiply”, or “Divide” button is clicked,
    //  the corresponding operation should be performed on the values from the num1 and num2 text fields,
    //  and the result should be displayed in the result text field.
    private void onClickButtonSubstract(View view) {
        this.PreventNonnumericData();
        txtResult.setText(String.valueOf(number1 - number2));
    }

    private void onClickButtonMultiply(View view) {
        this.PreventNonnumericData();
        txtResult.setText(String.valueOf(number1 * number2));
    }
    private void onClickButtonFactorial(View view) {
        this.PreventNonnumericData();
        int temp = 1;
        int factorial = 1;
        while(number1 >= temp) {
            factorial = factorial * temp;
            temp++;
        }
        txtResult.setText(String.valueOf(factorial));
    }

    private void onClickButtonDivide(View view) {
        try {
            this.PreventNonnumericData();
            DecimalFormat df = new DecimalFormat("0.00");
            if(number2 == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Error");
                builder.setMessage("A number cannot be devided by 0");
                builder.setPositiveButton("Try again", null);
                builder.show();
            }
            txtResult.setText(df.format((float) number1 / number2));
        } catch (ArithmeticException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error");
            builder.setMessage(e.getMessage());
            builder.setPositiveButton("Try again", null);
            builder.show();
        }
    }

    //When the user presses the “Start Other Activity” button on the first Activity,
    // use startActivityForResult() to start this new activity.
    public void startActivityForResult(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Main2Activity.class);
        startActivityForResult(intent,1);
        //MainActivity.this.startActivity(intent);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1) {
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
                    String birthday = data.getStringExtra("birthday");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    editTextName.setText(name);
                    editTextBirthday.setText(String.valueOf(birthday));
                }
            }
        }catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error");
            builder.setMessage(e.getMessage());
            builder.setPositiveButton("Try again", null);
            builder.show();
        }
    }

}
