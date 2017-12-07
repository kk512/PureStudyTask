package in.purestudy.app.purestudytask.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.purestudy.app.purestudytask.R;
import in.purestudy.app.purestudytask.adapter.DataAdapter;
import in.purestudy.app.purestudytask.fragment.MyAlertDialogFragment;
import in.purestudy.app.purestudytask.listener.RecyclerItemClickListener;
import in.purestudy.app.purestudytask.model.Data;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private ArrayList<Data> responseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        responseArrayList = new ArrayList<>();

        // row click listener
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        String title = responseArrayList.get(position).getFirstName() + " " + responseArrayList.get(position).getLastName() + "\n" +
                                "\n" + responseArrayList.get(position).getCity();
                        showDialog(title, position);
                    }
                })
        );


        loadJSON();
    }

    private void loadJSON() {

        Ion.with(this)
                .load("https://test.purestudy.com/testapi.php")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        // do stuff with the result or error
                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            JSONArray jsonArray = jsonResponse.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Data data = new Data();

                                data.setCity(jsonObject.getString("city"));
                                data.setFirstName(jsonObject.getString("first_name"));
                                data.setLastName(jsonObject.getString("last_name"));
                                data.setStatus("Unread");

                                responseArrayList.add(data);
                            }


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        adapter = new DataAdapter(responseArrayList);
                        recyclerView.setAdapter(adapter);
                    }
                });

    }

    void showDialog(String title, int position) {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                title, position);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void doPositiveClick(int position) {
        Log.i("FragmentAlertDialog", "Positive click!");
        responseArrayList.get(position).setStatus("checked");
        adapter.notifyDataSetChanged();
    }

    public void doNegativeClick(int position) {
        Log.i("FragmentAlertDialog", "Negative click!");
        responseArrayList.get(position).setStatus("unchecked");
        adapter.notifyDataSetChanged();
    }
}
