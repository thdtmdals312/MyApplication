package sssss.com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView viewDate, viewRemove; // 날짜표시, 삭제메시지;
    Button btnSave; // 저장버튼
    EditText editInput; // 일기입력칸
    DatePicker dp;
    View dialogView; // DatePicker 보이게
    View removeDialog; // 일기삭제다이얼로그
    String filename; // 파일명
    String today; // 선택날짜
    LinearLayout baseLayout;
    Calendar cal = Calendar.getInstance();
    int cYear = cal.get(Calendar.YEAR);
    int cMonth = cal.get(Calendar.MONTH);
    int cDay = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("일기장앱만들기");

        viewDate = (TextView) findViewById(R.id.ViewDate);
        btnSave = (Button) findViewById(R.id.BtnSave);
        editInput = (EditText)findViewById(R.id.EditInput);
        viewDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v ) { // 날짜를 표시한 TextView를 터치하면 DatePicker의 위젯을 가지고 있는 다이얼로그가 나타나는 클릭리스너
                dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                dp = (DatePicker) dialogView.findViewById(R.id.DatePicker);
                dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        today = Integer.toString(year) + "년 " + Integer.toString(monthOfYear + 1) + "월 " + Integer.toString(dayOfMonth) + "일";
                        filename = makeDiaryFileName(year, monthOfYear+1, dayOfMonth); // 파일명 정의

                    }
                });
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setView(dialogView);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() { // 날짜를 선택하고 확인버튼을 누르면 MainActivity의 날짜 표시 TextView의 내용이
                                                                                       // 선택된 날짜로 변경되고, 해당 날짜의 일기가 존재하면 읽어온다.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewDate.setText(today);
                        String str = readDiary(filename);
                        editInput.setText(str);
                    }
                });
                dlg.show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() { // 저장버튼을 누르면 현재 TextView에 보여지는 날짜에 해당하는 파일에 EditText의 일기 내용을 저장한다.
            @Override
            public void onClick(View v) { // 저장 클릭 리스너
                //Save();

                try {
                    FileOutputStream outFs = openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
                    String input = editInput.getText().toString();
                    outFs.write(input.getBytes());
                    Toast.makeText(getApplicationContext(), filename + "이 저장됨", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                }

            }
        });



    }





    String makeDiaryFileName(int Year, int Month, int Day) { // 파일명 정의 메서드
        String filename = Integer.toString(Year)+"년" + Integer.toString(Month)+"월" + Integer.toString(Day)+"일" + ".txt";
        return filename;
    }
    public void Save() { // sd카드에 저장하는 메소드
        File file;
        String path = Environment.getExternalStorageDirectory()+"/android/data/pe.berabue.maptools/map"; // sd카드저장소
        file = new File(path);
        if ( !file.exists()) // 폴더가 없다면 폴더를 만들도록 한다.
            file.mkdirs();
        file = new File(path+filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(editInput.getText().toString().getBytes());
            fos.close();
            Toast.makeText(MainActivity.this, "Save success", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {}
    }
    String readDiary(String fName) { // 선택날짜에 fName으로 저장된 일기 불러오기
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();

        } catch (IOException e)
        {
            editInput.setHint("일기 없음");

        }
        return diaryStr;
    }
    String removeDiary(String fname) { // 선택날짜에 있는 일기 삭제하기
        String diaryStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fname);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = "";

        } catch (IOException e) {   }
        return diaryStr;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // 메뉴만들기
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1, menu); // menu1.xml 붙이기
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId(); // 선택된 메뉴 아이템을 id에 저장
        switch(id) {
            case R.id.itemReload: // '다시 읽기' 메뉴를 선택하면 TextView의 날짜에 해당하는 일기 파일을 다시 불러온다.
                String loadStr = readDiary(filename);
                editInput.setText(loadStr);
                break;
            case R.id.itemRemove: // '일기 삭제' 메뉴를 선택하면 "20XX년 X월 X일의 일기를 삭제하시겠습니까?" 라고 묻는 다이얼로그(removeDialog)를 보여주고,
                                    // 확인을 누른경우에 TextView의 날짜에 해당하는 일기를 지우고, EditText의 내용도 지운다.
                                    // 취소를 누르면 아무 작업도 하지 않는다.
                removeDialog = (View) View.inflate(MainActivity.this, R.layout.removedialog, null);
                viewRemove = (TextView)removeDialog.findViewById(R.id.viewRemove);
                viewRemove.setText(today+"의 일기를 삭제하시겠습니까?");
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setView(removeDialog);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() { // 확인을 누른경우
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String removeStr = removeDiary(filename);
                        editInput.setText(removeStr);
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() { // 취소를 누른경우
                    @Override
                    public void onClick(DialogInterface dialog, int which) {  }
                });
                dlg.show();
                break;
            case R.id.sizelarge: // EditText의 글자 크기 '크게'
                editInput.setTextSize(60);
                break;
            case R.id.sizeNormal: // EditText의 글자 크기 '보통'
                editInput.setTextSize(20);
                break;
            case R.id.sizeSmall: // EditText의 글자 크기 '작게'
                editInput.setTextSize(10);
                break;
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
