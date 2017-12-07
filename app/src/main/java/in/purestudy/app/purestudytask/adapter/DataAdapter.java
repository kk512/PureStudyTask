package in.purestudy.app.purestudytask.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.purestudy.app.purestudytask.R;
import in.purestudy.app.purestudytask.model.Data;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Data> android;

    public DataAdapter(ArrayList<Data> android) {
        this.android = android;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tvCity.setText(android.get(i).getCity());
        viewHolder.tvFirstName.setText(android.get(i).getFirstName());
        viewHolder.tvLastName.setText(android.get(i).getLastName());

        if (android.get(i).getStatus().equals("checked")) {
            viewHolder.imgStatus.setBackgroundResource(R.drawable.ic_right_new);
        } else {
            viewHolder.imgStatus.setBackgroundResource(R.drawable.ic_cross);
        }
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFirstName, tvLastName, tvCity;
        private ImageView imgStatus;

        public ViewHolder(View view) {
            super(view);

            tvFirstName = (TextView) view.findViewById(R.id.tv_first_name);
            tvLastName = (TextView) view.findViewById(R.id.tv_last_name);
            tvCity = (TextView) view.findViewById(R.id.tv_city);
            imgStatus = (ImageView) view.findViewById(R.id.img_status);

        }
    }
}
