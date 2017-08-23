package com.teal.tealmallard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tealdrones.mallard.Mallard;
import com.tealdrones.mallard.controller.OnErrorListener;
import com.tealdrones.mallard.controller.OnResponseListener;
import com.tealdrones.mallard.controller.Quacker;
import com.tealdrones.mallard.model.PropfindIncubator;
import com.tealdrones.mallard.model.Quack;
import com.tealdrones.mallard.model.QuackErrorResponse;
import com.tealdrones.mallard.model.QuackResponse;


public class MainActivity extends AppCompatActivity implements OnErrorListener, OnResponseListener {

    public static final int QUACK = 1001;
    public static final int DELETE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mallard mallard = Mallard.getInstance();
        mallard.setUrl("http://10.73.41.178");
        Quacker quacker = new Quacker();
        Quack propfind = new PropfindIncubator(mallard, "flight").hatch();
        propfind.setOnResponseListener(this);
        propfind.setOnErrorListener(this);
        quacker.quack(propfind);
        mallard.getList("flight", QUACK, this, this);
    }

    @Override
    public void onError(QuackErrorResponse quack) {
        switch (quack.getRequestCode()) {
            case QUACK:
                Log.d("MainActivity", "We got our quack back!!!!");
                Log.d("MainActivity", quack.toString());
                break;
            case DELETE:
                Log.d("MainActivity", "We got our delete quack back!!!!");
                Log.d("MainActivity", quack.toString());
                break;
            default:
                Log.d("MainActivity", quack.toString());
                break;
        }
    }

    @Override
    public void onResponse(QuackResponse response) {
        switch (response.getRequestCode()) {
            case QUACK:
                Log.d("MainActivity", "We got our quack back!!!!");
                Log.d("MainActivity", response.toString());
                Log.d("MainActivity", response.getResponse().body().toString());
                Log.d("MainActivity", response.getDucks().toString());
                Mallard.getInstance().delete(response.getDucks().get(0).getText(), DELETE, this, this);
                break;
            case DELETE:
                Log.d("MainActivity", "We got our delete quack back!!!!");
                Log.d("MainActivity", response.toString());
                Log.d("MainActivity", response.getResponse().body().toString());
                Log.d("MainActivity", response.getDucks().toString());
                break;
            default:
                Log.d("MainActivity", response.toString());
//                Log.d("MainActivity", response.getResponse().body().toString());
//                Log.d("MainActivity", response.getDucks().toString());
                break;
        }
    }
}
