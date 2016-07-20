package world.hello.com.start.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.ArrayList;

import world.hello.com.start.R;
import world.hello.com.start.adapters.ClickHandler;
import world.hello.com.start.adapters.ProductsAdapter;
import world.hello.com.start.models.Product;
import world.hello.com.start.tasks.FetchSearchTask;
import world.hello.com.start.tasks.TaskHandler;

public class ProductsActivity extends AppCompatActivity {

    RecyclerView products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_products);
        products = (RecyclerView) findViewById(R.id.products);

        getSupportActionBar().setTitle(getIntent().getStringExtra("query"));
        new FetchSearchTask(new TaskHandler() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onResult(Object result) {
                if (result != null && result instanceof ArrayList){
                    ArrayList<Product> products = (ArrayList<Product>) result;
                    ProductsActivity.this.products.setAdapter(new ProductsAdapter(products, new ClickHandler() {
                        @Override
                        public void onProductClicked(Product product) {
                            if (product == null || TextUtils.isEmpty(product.styleId)){
                                return;
                            }
                            Intent intent = new Intent(ProductsActivity.this, PDPActivity.class);
                            intent.putExtra("styleId", product.styleId);
                            startActivity(intent);
                        }
                    }));
                    ProductsActivity.this.products.setLayoutManager(new GridLayoutManager(ProductsActivity.this, 2));
                }
            }
        }).execute(getIntent().getStringExtra("query"));

    }
}
