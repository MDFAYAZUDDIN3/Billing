package com.example.billing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Order_Details extends AppCompatActivity {
    TextView ono;
    TextView tno;

    ArrayList<Getter_Setter_Items> list;
    RecyclerView recyclerView;
    DatabaseReference db;
    MyAdapter_Order_Dishes myAdapterOrderDishes;
    long i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Bundle b1= getIntent().getExtras();
        final long orderno = b1.getLong("Ordernumber");
        final long tableno = b1.getLong("tablenumber");

        tno=findViewById(R.id.TableNumber);
        ono=findViewById(R.id.OrderNumber);
        ono.setText(String.valueOf(orderno));
        tno.setText(String.valueOf(tableno));

        db = FirebaseDatabase.getInstance().getReference().child(String.valueOf(orderno));

        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapterOrderDishes =new MyAdapter_Order_Dishes(this,list);
        recyclerView.setAdapter(myAdapterOrderDishes);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                i=(snapshot.getChildrenCount());
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Getter_Setter_Items i = dataSnapshot.getValue(Getter_Setter_Items.class);
                    list.add(i);
                }
                myAdapterOrderDishes.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ;
    }
}