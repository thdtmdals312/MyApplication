package com.example.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    TextView text1, text2;
    Switch switchAgree;
    RadioGroup rGroup1;
    RadioButton rdoJel, rdoKit, rdoLol;
    Button btnExit, btnStart;
    ImageView imgVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("안드로이드 사진 보기");
        text1= (TextView) findViewById(R.id.Text1);
        switchAgree = (Switch) findViewById(R.id.SwitchAgree);
        text2 = (TextView) findViewById(R.id.Text2);
        rGroup1 = (RadioGroup) findViewById(R.id.Rgroup1);
        rdoJel = (RadioButton) findViewById(R.id.RdoJel);
        rdoKit = (RadioButton) findViewById(R.id.RdoKit);
        rdoLol = (RadioButton) findViewById(R.id.RdoLol);
        btnExit = (Button) findViewById(R.id.BtnExit);
        btnStart = (Button) findViewById(R.id.BtnStart);
        imgVersion = (ImageView) findViewById(R.id.ImgVersion);
        RadioButtonListener RBL = new RadioButtonListener();
        switchAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchAgree.isChecked() == true) {
                    text2.setVisibility(View.VISIBLE);
                    rGroup1.setVisibility(View.VISIBLE);
                    btnStart.setVisibility(View.VISIBLE);
                    btnExit.setVisibility(View.VISIBLE);
                    imgVersion.setVisibility(View.VISIBLE);
                } else {
                    text2.setVisibility(View.INVISIBLE);
                    rGroup1.setVisibility(View.INVISIBLE);
                    btnStart.setVisibility(View.VISIBLE);
                    btnExit.setVisibility(View.INVISIBLE);
                    imgVersion.setVisibility(View.INVISIBLE);
                }
            }
        });
        rdoJel.setOnClickListener(RBL);
        rdoKit.setOnClickListener(RBL);
        rdoLol.setOnClickListener(RBL);
        btnExit.setOnClickListener(RBL);
        btnStart.setOnClickListener(RBL);
        /*btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SwitchAgree.setChecked(false);
            }
        });*/
        btnExit.setOnClickListener(new View.OnClickListener()
        {            public void onClick(View v) {
                System.exit(0);
            }
        });
        btnStart.setOnClickListener(RBL);
    }
    class RadioButtonListener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.RdoJel:
                    imgVersion.setImageResource(R.drawable.jelly);
                    break;
                case R.id.RdoKit:
                    imgVersion.setImageResource(R.drawable.kitkat);
                    break;
                case R.id.RdoLol:
                    imgVersion.setImageResource(R.drawable.lolli);
                    break;
                case R.id.BtnStart:
                    switchAgree.setChecked(false);
                    break;
            }
        }}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }    @Override
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
    }}