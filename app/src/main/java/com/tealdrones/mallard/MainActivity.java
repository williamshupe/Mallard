package com.tealdrones.mallard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.tealdrones.mallard.communication.Depth;
import com.tealdrones.mallard.communication.OnDeleteRequestCompleteListener;
import com.tealdrones.mallard.communication.OnPropfindRequestCompleteListener;
import com.tealdrones.mallard.model.RemoteFile;
import com.tealdrones.mallard.model.RequestError;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.propfind_request_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePropfindRequest();
            }
        });

        findViewById(R.id.delete_request_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDeleteRequest();
            }
        });
    }

    public void makePropfindRequest() {
        Mallard.propfind("http://10.73.41.98/flight", Depth.ONE, new OnPropfindRequestCompleteListener() {
            @Override
            public void onSuccess(List<RemoteFile> remoteFiles) {
                Log.d("test", "Success with " + remoteFiles.size() + " files");
            }

            @Override
            public void onError(RequestError error) {
                Log.d("test", "Failed with error type of " + error.getErrorType());

                if (error.getErrorException() != null) {
                    error.getErrorException().printStackTrace();
                }
            }
        });
    }

    public void makeDeleteRequest() {
        Mallard.delete("http://10.73.41.98/flight/filename.tfl", new OnDeleteRequestCompleteListener() {
            @Override
            public void onSuccess() {
                Log.d("test", "Successfully deleted file");
            }

            @Override
            public void onError(RequestError error) {
                Log.d("test", "Failed to delete file with error type of " + error.getErrorType());

                if (error.getErrorException() != null) {
                    error.getErrorException().printStackTrace();
                }
            }
        });
    }
}
