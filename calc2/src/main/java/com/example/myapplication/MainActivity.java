package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    EditText edit1,edit2;
    Button btnAdd,btnSub,btnMul,btnDiv;
    TextView textResult;
    String num1, num2;
    Integer result;
    Button[] numButtons = new Button[10];
    Integer[] numBtnIDs= {R.id.Btn0,R.id.Btn1,R.id.Btn2,R.id.Btn3,R.id.Btn4,
                            R.id.Btn5,R.id.Btn6,R.id.Btn7,R.id.Btn8,R.id.Btn9};
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("테이블레이아웃 계산기");
        edit1 = (EditText)findViewById(R.id.Edit1);
        edit2 = (EditText)findViewById(R.id.Edit2);
        btnAdd = (Button) findViewById(R.id.BtnAdd);
        btnSub = (Button) findViewById(R.id.BtnSub);
        btnMul = (Button) findViewById(R.id.BtnMul);
        btnDiv = (Button) findViewById(R.id.BtnDiv);
        textResult = (TextView)findViewById(R.id.Result);
        calcListener listener = new calcListener();

        for(i =0; i< numBtnIDs.length;i++) {
            numButtons[i]= (Button) findViewById(numBtnIDs[i]);
        }

        for(int i=0;i<numBtnIDs.length;i++) {
            final int index;
            index = i;

            numButtons[index].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(edit1.isFocused() == true) {
                        num1 = edit1.getText().toString()
                                + numButtons[index].getText().toString();
                        edit1.setText(num1);
                    } else if (edit2.isFocused() == true) {
                        num2 = edit2.getText().toString()
                                + numButtons[index].getText().toString();
                        edit2.setText(num2);
                    } else {
                        Toast.makeText(getApplicationContext(), "먼저 에디트텍스트를 선택하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        btnAdd.setOnClickListener(listener);
        btnSub.setOnClickListener(listener);
        btnMul.setOnClickListener(listener);
        btnDiv.setOnClickListener(listener);

    }
    class calcListener implements View.OnClickListener
    {
        public void onClick(View arg0) {
            num1=edit1.getText().toString();
            num2=edit2.getText().toString();
            switch(arg0.getId()) {
                case R.id.BtnAdd:
                    result = Integer.parseInt(num1) + Integer.parseInt(num2);
                    break;
                case R.id.BtnSub:
                    result = Integer.parseInt(num1) - Integer.parseInt(num2);
                    break;
                case R.id.BtnMul:
                    result = Integer.parseInt(num1) * Integer.parseInt(num2);
                    break;
                case R.id.BtnDiv:
                    result = Integer.parseInt(num1) / Integer.parseInt(num2);
                    break;
            }
            textResult.setText("계산결과 : " + result.toString());

        }
    }

    /*View.OnTouchListener listener = new View.OnTouchListener()
    {
        public boolean onTouch(View arg0, MotionEvent arg1) {
            switch(arg0.getId()) {
                case R.id.BtnAdd:
                    num1=edit1.getText().toString();
                    num2=edit2.getText().toString();
                    result = Integer.parseInt(num1) + Integer.parseInt(num2);
                    textResult.setText("계산결과 : " + result.toString());
                case R.id.BtnSub:
                    num1=edit1.getText().toString();
                    num2=edit2.getText().toString();
                    result = Integer.parseInt(num1) - Integer.parseInt(num2);
                    textResult.setText("계산결과 : " + result.toString());
                case R.id.BtnMul:
                    num1=edit1.getText().toString();
                    num2=edit2.getText().toString();
                    result = Integer.parseInt(num1) * Integer.parseInt(num2);
                    textResult.setText("계산결과 : " + result.toString());
                case R.id.BtnDiv:
                    num1=edit1.getText().toString();
                    num2=edit2.getText().toString();
                    result = Integer.parseInt(num1) / Integer.parseInt(num2);
                    textResult.setText("계산결과 : " + result.toString());
            }
            return false;
        }
    };*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
