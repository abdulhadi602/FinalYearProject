package com.example.nomanikram.epilepsyseizuredetection;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nomanikram.epilepsyseizuredetection.models.Patient;
import com.example.nomanikram.epilepsyseizuredetection.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    AppCompatTextView txt_name;
    AppCompatTextView txt_age;
    AppCompatTextView txt_weight;
    AppCompatTextView txt_height;


    FirebaseAuth mAuth;
    DatabaseReference reference;
    String userID;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        txt_name = (AppCompatTextView) view.findViewById(R.id.txt_name);
        txt_age = (AppCompatTextView) view.findViewById(R.id.txt_age);
        txt_weight = (AppCompatTextView) view.findViewById(R.id.txt_weight);
        txt_height = (AppCompatTextView) view.findViewById(R.id.txt_height);

//        txt_name = (AppCompatTextView) view.findViewById(R.id.txt_name);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

         userID = mAuth.getCurrentUser().getUid();

        Toast.makeText(getActivity().getApplicationContext(),"SHOW: "+userID,Toast.LENGTH_SHORT).show();

        Query query1 = reference.child("users").child(userID).child("Patient");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Patient patient = new Patient();
                    patient.setName(""+dataSnapshot.child("name").getValue());
                    patient.setAge(""+dataSnapshot.child("age").getValue());
                    patient.setWeight(""+dataSnapshot.child("weight").getValue());
                    patient.setHeight(""+dataSnapshot.child("height").getValue());

                    Log.w("TAG", "Patient Name: " + patient.getName());
                    Log.w("TAG", "Patient Age: " + patient.getAge());
                    Log.w("TAG", "Patient Height: " + patient.getWeight());
                    Log.w("TAG", "Patient Weight: " + patient.getHeight());


                    txt_name.setText(patient.getName());
                    txt_age.setText(patient.getAge()+ " years old");
                    txt_height.setText(patient.getHeight() + "cm");
                    txt_weight.setText(patient.getWeight()+"kg");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}