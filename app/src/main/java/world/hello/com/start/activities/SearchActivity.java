package world.hello.com.start.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import world.hello.com.start.R;

public class SearchActivity extends AppCompatActivity {

    private Button go;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);
        go = (Button) findViewById(R.id.go);
        search = (EditText) findViewById(R.id.search);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(search.getText())){
                    return;
                }
                Intent intent = new Intent(SearchActivity.this, ProductsActivity.class);
                intent.putExtra("query", search.getText().toString());
                startActivity(intent);
            }
        });
    }

}
