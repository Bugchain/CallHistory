package com.bugchain.callhistory;

import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCallLog();
    }

    public void getCallLog(){
        StringBuffer sb = new StringBuffer();
        String strOrder = CallLog.Calls.DATE + " DESC";
        /* Query the call log content provider  */
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);
        if(cursor != null) {
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            sb.append("Call log :\n");

            while (cursor.moveToNext()){
                String phoneNumber = cursor.getString(number);
                String strCallType = cursor.getString(type);
                String strCallDate = cursor.getString(date);
                String callDuration = cursor.getString(duration);
                String strName = cursor.getString(name);
                Date callDate = new Date(Long.valueOf(strCallDate));
                String callType = null;
                int callCode = Integer.parseInt(strCallType);
                switch (callCode){
                    case CallLog.Calls.OUTGOING_TYPE:
                        callType = "Outgoing";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        callType  = "Incoming";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        callType = "Missed";
                        break;
                }
                sb.append("\nPhone Number :  " + phoneNumber +
                          "\nName : " + strName +
                          "\nCall Type :  " + callType +
                          "\nCall Date :  " + callDate +
                          "\nCall Duration (sec) :  " + callDuration );
                sb.append("\n-----------------------------------------");
            }
            cursor.close();
            TextView text = (TextView)findViewById(R.id.textHistoryLog);
            text.setText(sb.toString());
        }

    }


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
