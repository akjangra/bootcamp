package world.hello.com.start.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import world.hello.com.start.R;
import world.hello.com.start.models.PdpDetail;
import world.hello.com.start.tasks.FetchPDPDetailsTask;
import world.hello.com.start.tasks.TaskHandler;

public class PDPActivity extends AppCompatActivity {

    ImageView pdpImage, buyButtonImage;
    TextView descriptionText, priceText;
    PdpDetail pdpDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Image views
        pdpImage = (ImageView) findViewById(R.id.pdp_image);
        buyButtonImage = (ImageView) findViewById(R.id.buy_button);

        //Text Views
        descriptionText = (TextView) findViewById(R.id.description_text);
        priceText = (TextView) findViewById(R.id.price);

        new FetchPDPDetailsTask(new TaskHandler() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onResult(Object result) {
                if (result != null && result instanceof PdpDetail){
                    pdpDetailModel = (PdpDetail) result;
                    priceText.setText(pdpDetailModel.getPrice());
                    descriptionText.setText(pdpDetailModel.getDescription());
                    Picasso.with(PDPActivity.this)
                            .load(pdpDetailModel.getImageUrl()).into(pdpImage);
                }
            }
        }).execute(getIntent().getStringExtra("styleId"));
    }
}
