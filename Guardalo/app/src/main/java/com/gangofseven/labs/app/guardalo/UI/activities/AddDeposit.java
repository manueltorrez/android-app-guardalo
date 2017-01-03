package com.gangofseven.labs.app.guardalo.UI.activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.gangofseven.labs.app.guardalo.DatabaseUtil;
import com.gangofseven.labs.app.guardalo.R;
import com.gangofseven.labs.app.guardalo.models.DepositModel;
import com.gangofseven.labs.app.guardalo.models.TotalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDeposit extends AppCompatActivity {

    private EditText add;
    private Button addB;

    private FirebaseDatabase mDatabase;
    private DatabaseReference deposits;
    private DatabaseReference total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deposit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Agregar depósito");

        mDatabase = DatabaseUtil.getDatabase();
        deposits = mDatabase.getReference().child("deposits");
        total = mDatabase.getReference().child("total/totalValue");

        add = (EditText)findViewById(R.id.deposit_amount_edit);

        addB = (Button) findViewById(R.id.add);
        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNullOrEmpty (add.getText().toString())) {
                    Snackbar.make (view, "Rellena el campo", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {

                                }
                            })
                    .show ();
                }
                else {
                    addNewDeposit();
                    Snackbar.make (view, "Guardado exitosamente", Snackbar.LENGTH_LONG)
                            .setAction("VOLVER", new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {
                                    startAgain();
                                }
                            })
                            .show ();
                }
            }
        });



    }

    private void addNewDeposit(){
        final DepositModel depositModel = new DepositModel();
        depositModel.setAmount(Float.parseFloat(add.getText().toString()));
        depositModel.setToday(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        deposits.push().setValue(depositModel);

        total.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float temp = dataSnapshot.getValue(Float.class);
                float totalTemp = temp + depositModel.getAmount();
                total.setValue(totalTemp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static boolean isNullOrEmpty(String value)
    {
        if (value != null)
            return value.length() == 0;
        else
            return true;
    }

    private void startAgain(){
        Intent i = new Intent(AddDeposit.this, MainActivity.class);
        startActivity(i);


    }
}
