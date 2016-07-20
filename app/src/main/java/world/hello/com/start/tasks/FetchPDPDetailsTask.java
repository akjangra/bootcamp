package world.hello.com.start.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import world.hello.com.start.models.PdpDetail;

/**
 * Created by a Jedi Master.
 */
public class FetchPDPDetailsTask extends AsyncTask<String, Void, PdpDetail> {
    private static final String TAG = FetchPDPDetailsTask.class.getSimpleName();

    private TaskHandler taskHandler;

    public FetchPDPDetailsTask(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (taskHandler != null){
            taskHandler.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(PdpDetail pdpDetail) {
        super.onPostExecute(pdpDetail);
        if (taskHandler != null){
            taskHandler.onResult(pdpDetail);
        }
    }

    @Override
    protected PdpDetail doInBackground(String... strings) {
        if (strings.length == 0){
            return new PdpDetail();
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL("http://developer.myntra.com/style/" + strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                total.append(lines).append("\n");
            }
            Log.i(TAG, "response: " + total);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException ignore) {
                }
            }
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException ignore) {
                }
            }
        }

        PdpDetail pdpDetailModel = new PdpDetail();
        try {
            JSONObject response = new JSONObject(total.toString());
            JSONObject pdpDetail = response.getJSONObject("data");
            String price = pdpDetail.getString("price");
            String description = pdpDetail.getString("productDisplayName");
            String imageUrl = pdpDetail.getJSONObject("styleImages")
                    .getJSONObject("default")
                    .getString("imageURL");
            //filling the data in pdp Detail model
            pdpDetailModel.setPrice(price);
            pdpDetailModel.setDescription(description);
            pdpDetailModel.setImageUrl(imageUrl);

        } catch (JSONException e){
            Log.e(TAG, "", e);
        }
        return pdpDetailModel;
    }
}
