package dan.hunt.com.sqltest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SQLActivity extends AppCompatActivity {

    TextView resultView;
    Button getDataButton;
    OkHttpClient client;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        //Initialise OkHTTPClient instance
        client = new OkHttpClient();

        //setup textview
        resultView = (TextView) findViewById(R.id.resultView);

        //setup button and click listener
        getDataButton = (Button) findViewById(R.id.getDataButton);
        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebservice();
            }
        });
    }

    private void getWebservice() {
       RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", "123")
                .addFormDataPart("start", "2017-07-01")
                .addFormDataPart("end", "2017-07-16")
                .build();

        final Request request = new Request.Builder().url("http://192.168.1.5/getsales4.php")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultView.setText("Failure !");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            resultView.setText(response.body().string());
                        } catch (IOException ioe) {
                            resultView.setText("Error during get body");
                        }
                    }
                });
            }
        });
    }

}
