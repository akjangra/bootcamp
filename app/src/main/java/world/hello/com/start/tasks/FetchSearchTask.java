package world.hello.com.start.tasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import world.hello.com.start.models.Product;

/**
 * Created by a Jedi Master.
 */
public class FetchSearchTask extends AsyncTask<String, Void, ArrayList<Product>> {
    private static final String TAG = FetchSearchTask.class.getSimpleName();

    private TaskHandler taskHandler;

    public FetchSearchTask(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        taskHandler.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        super.onPostExecute(products);
        taskHandler.onResult(products);
    }

    @Override
    protected ArrayList<Product> doInBackground(String... strings) {
        if (strings.length == 0){
            return new ArrayList<>();
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder total = new StringBuilder();
        try {
            URL url = new URL("http://developer.myntra.com/v2/search/data/" + strings[0]);
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

        try {
            Log.d(TAG, "result::" + total.toString());
            JSONObject searchData = new JSONObject(total.toString());
            JSONObject data = searchData.getJSONObject("data");
            JSONObject results = data.getJSONObject("results");
            JSONArray productsArray = results.getJSONArray("products");

            ArrayList<Product> products = new ArrayList<>();
            for (int i = 0; i < productsArray.length(); i++){
                Product product = getProductFrom(productsArray.getJSONObject(i));
                if (product != null){
                    products.add(product);
                }
            }
            return products;
        } catch (JSONException e) {
            Log.e(TAG, "", e);
        }

        return new ArrayList<>();
    }

    private Product getProductFrom(JSONObject object){
        if (object == null){
            return null;
        }
        Product product = new Product();
        try {
            product.searchImage = object.getString("search_image");
            product.styleId = object.getString("styleid");
        } catch (JSONException e) {
            return null;
        }
        return product;
    }
}
