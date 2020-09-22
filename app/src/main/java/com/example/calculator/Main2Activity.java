package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

public class Main2Activity extends AppCompatActivity implements DatePicker.OnDateChangedListener, View.OnClickListener{
    EditText editTextBirthday, editTextName;
    Calendar calendar;
    DatePicker datePicker;
    Button btnSave;
    Button btnLoad;
    int thisYear, thisMonth, thisDay, monthOfYear, dayOfMonth;
    String name;
    static Boolean canRequestData = false;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // use DatePicker in our application, it will ensure that the users will select a valid date.
        datePicker = (DatePicker)findViewById(R.id.datepicker1);
        datePicker.setMaxDate(new Date().getTime());
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year,monthOfYear,dayOfMonth,this);
        thisYear = calendar.get(Calendar.YEAR);
        thisMonth = calendar.get(Calendar.MONTH);
        thisDay = calendar.get(Calendar.DATE);

        editTextBirthday = findViewById(R.id.editTextBirthday);
        btnSave = findViewById(R.id.save);
        btnLoad = findViewById(R.id.load);
        editTextName = findViewById(R.id.editTextName);

//        try {
//            editTextBirthday.setText(sharedPreferences.getString("name", "type name here..."));
//            editTextName.setText(sharedPreferences.getString("birthday", ""));
//        } catch (Exception e) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
//            builder.setTitle("Save error");
//            builder.setMessage(e.getMessage());
//            builder.setPositiveButton("Try again", null);
//            builder.show();
//            canRequestData = false;
//        }

        btnSave.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = editTextName.getText().toString();
                name = stringFilter(string.toString());
                if(!string.equals(name)) {
                    editTextName.setText(name);
                    editTextName.setSelection(name.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // //Confirm which button is pressed
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.save:
                onClickButtonSave(view);
                break;
            case R.id.load:
                onClickButtonLoad(view);
                break;
            default:
                break;
        }
    }
//    When the save operation has completed, display a Toast that indicates the success of the operation.
    private void onClickButtonSave(View view) {
        if(editTextName.getText().toString().length() == 0 || editTextBirthday.getText().toString().equals("select a birthdate")) {
            AlertDialog.Builder builder  = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Not finished" ) ;
            builder.setMessage("Name and birthday cannot be blank" ) ;
            builder.setPositiveButton("Try again" ,  null );
            builder.show();
        } else {
            sharedPreferences = getSharedPreferences("name_birthday", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("name", editTextName.getText().toString());
            editor.putString("birthday", editTextBirthday.getText().toString());
            editor.commit();
            Toast.makeText(Main2Activity.this,"save operation has completed", Toast.LENGTH_SHORT).show();
        }
    }

//    When the load button is pressed, the most recently saved name and date values should be retrieved
//    and displayed (on the second activity). A Toast should also indicate the successful load.
    private void onClickButtonLoad(View view) {
        try {
            if(sharedPreferences.getString("name", null) != null &&
                    sharedPreferences.getString("birthday", null) != null) {
                Toast.makeText(Main2Activity.this, "load operation has completed", Toast.LENGTH_SHORT).show();
                canRequestData = true;
            }
        }catch(Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setTitle("Save error");
            builder.setMessage("Load error, please try again");
            builder.setPositiveButton("Try again", null);
            builder.show();
            canRequestData = false;
        }
    }

//    When the user returns to the main Activity (by pressing the back button or by pressing the "up" button on the action bar),
//    the name and age EditText widgets on the main Activity should be updated based on the name and age values which are currently
//    displayed in the second Activity.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onBackPressed() {
        if(canRequestData) {
            Intent intent = new Intent();
            intent.putExtra("name", editTextName.getText().toString());
            intent.putExtra("birthday", editTextBirthday.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
        finishAfterTransition();
    }

//    Letters and spaces are allowed only
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[a-zA-Z\\u4E00-\\u9FA5 ]+";
        return str != null ? (str.matches(regEx) ? str : "") : "";
    }

    // Pick date as birthday.
    public void onDateChanged(DatePicker view, int year,int month,int day){
//        if ((year > thisYear) || (year == thisYear && month > thisMonth)
//                || (year == thisYear && month == thisMonth && day > thisDay)
//                ||()) {
//            AlertDialog.Builder builder  = new AlertDialog.Builder(Main2Activity.this);
//            builder.setTitle("Wrong Date" ) ;
//            builder.setMessage("Birthday cannot be in the future" ) ;
//            builder.setPositiveButton("Try again" ,  null );
//            builder.show();
//            datePicker.init(thisYear, thisMonth, thisDay, this);
//        }
//        Toast.makeText(Main2Activity.this,"Birthday：" + year + '/' + (month+1) + '/' + day, Toast.LENGTH_SHORT).show();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.CANADA);
            int NowYear = Integer.parseInt(format.format(new Date()));
            editTextBirthday.setText(String.valueOf(NowYear - year));
        }catch(Exception e){
            Log.d("Error: ", "Message: " + e.getMessage());
        }
    }


}
