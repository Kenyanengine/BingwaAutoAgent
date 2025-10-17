package com.bingwa.autoagent;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadService extends IntentService {

    public UploadService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            String sender = intent.getStringExtra("sender");
            String message = intent.getStringExtra("message");

            URL url = new URL("https://your-backend-url.com/api/mpesa");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String json = "{\"sender\":\"" + sender + "\",\"message\":\"" + message + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            Log.d("UPLOAD", "Sent message: " + message);
        } catch (Exception e) {
            Log.e("UPLOAD_ERROR", "Error sending message", e);
        }
    }
}
