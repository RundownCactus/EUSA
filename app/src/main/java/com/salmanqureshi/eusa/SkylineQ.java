package com.salmanqureshi.eusa;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class SkylineQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        PyObject pyObj = py.getModule("test");
        //Pass arguments to func
        //PyObject obj = pyObj.callAttr("wow","arg1","arg2".....);
        PyObject obj = pyObj.callAttr("wow");
        Log.d("IF THIS WORKS I AM GOD", obj.toString());
        Toast.makeText(SkylineQ.this, obj.toString(), Toast.LENGTH_SHORT).show();
        finish();
    }


}