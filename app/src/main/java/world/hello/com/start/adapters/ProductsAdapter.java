package world.hello.com.start.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import world.hello.com.start.R;
import world.hello.com.start.models.Product;

/**
 * Created by a Jedi Master.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private ArrayList<Product> products = new ArrayList<>();
    private ClickHandler clickHandler;

    public ProductsAdapter(ArrayList<Product> products, ClickHandler clickHandler) {
        this.products = products;
        this.clickHandler = clickHandler;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHandler.onProductClicked(products.get(position));
            }
        });
        Picasso.with(holder.itemView.getContext())
                .load(products.get(position).searchImage)
                .resize(150, 150)
                .into(holder.product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

class ProductViewHolder extends RecyclerView.ViewHolder{
    public ImageView product;
    public ProductViewHolder(View itemView) {
        super(itemView);
        product = (ImageView) itemView;
    }
}