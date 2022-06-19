package com.fyp.sporteaze.Academy.Invoice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.Invoice;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AcademyViewInvoice extends AppCompatActivity {
    List<Invoice> invoiceList;
    RecyclerView recyclerView;
    ViewInvoiceAdapter viewInvoiceAdapter;
    DatabaseReference databaseReference;
    String academy_email , academy_id, academy_name;
    LinearLayout no_invoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_view_invoice);

        recyclerView = findViewById(R.id.coaches_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        no_invoices = findViewById(R.id.no_invoices);
        invoiceList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");
        databaseReference = FirebaseDatabase.getInstance().getReference("Invoices");
            no_invoices.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);



        databaseReference.child(academy_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Invoice us = ds.getValue(Invoice.class);
                    invoiceList.add(us);
                }
                viewInvoiceAdapter = new ViewInvoiceAdapter(invoiceList , academy_id , academy_email);
                recyclerView.setAdapter(viewInvoiceAdapter);
                if (invoiceList.size()==0){
                    no_invoices.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else{
                    no_invoices.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}