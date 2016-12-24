package com.gangofseven.labs.app.guardalo.UI.activities;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chocoyo.labs.adapters.progress.AdapterProgress;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gangofseven.labs.app.guardalo.DatabaseUtil;
import com.gangofseven.labs.app.guardalo.R;
import com.gangofseven.labs.app.guardalo.models.DepositModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private FirebaseDatabase mDatabase;
    private DatabaseReference deposits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(null);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        topToolBar.setNavigationIcon(R.drawable.ic_action_ic_chancho);

        mDatabase = DatabaseUtil.getDatabase();
        deposits = mDatabase.getReference().child("deposits");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new AdapterProgress());

        //writeNewUser(10, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddDeposit.class);
                startActivity(i);
            }
        });

    }


    //FILLING THE ACTION BAR

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            Toast.makeText(MainActivity.this, "Refresh App", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }


    //FILLING THE RECYCLERVIEW

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DepositModel, CarViewHolder> adapter = new FirebaseRecyclerAdapter<DepositModel, CarViewHolder>(
                DepositModel.class,
                R.layout.item_deposit,
                CarViewHolder.class,
                deposits) {
            @Override
            protected void populateViewHolder(CarViewHolder viewHolder, final DepositModel depositModel, final int position) {

                // set key to send to edit activity
                final String key = this.getRef(position).getKey();

                viewHolder.amount.setText(String.valueOf(depositModel.getAmount()));
                viewHolder.today.setText(depositModel.getToday());


/*

                // open activity to edit
                viewHolder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AddCarActivity.class);
                        intent.putExtra(AddCarActivity.EXTRA_KEY, key);
                        startActivity(intent);
                    }
                });
                */
            }
        };

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(700);
        alphaAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(alphaAdapter);
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView amount;
        TextView today;
        public CarViewHolder(View view) {
            super(view);
            amount = (TextView) view.findViewById(R.id.deposit_amount);
            today = (TextView) view.findViewById(R.id.deposit_date);
        }
    }

    private void writeNewUser(float username, String email) {
        DepositModel juanModel = new DepositModel();
        juanModel.setAmount(username);
        juanModel.setToday(email);

        deposits.push().setValue(juanModel);
    }

}
