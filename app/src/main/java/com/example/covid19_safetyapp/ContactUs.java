package com.example.covid19_safetyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    ImageView imgReturn;
    EditText etName,etEmail,etContact,etAge,etQuery;
    Button btnSubmit;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference dbRef=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Intent intent=getIntent();

        imgReturn=findViewById(R.id.imgContactUsReturn);
        etName=findViewById(R.id.etContactName);
        etEmail=findViewById(R.id.etContactEmail);
        etContact=findViewById(R.id.etContactNumber);
        etAge=findViewById(R.id.etContactAge);
        etQuery=findViewById(R.id.etContactData);
        btnSubmit=findViewById(R.id.btnSubmitData);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,email,contact,query,age;
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                contact=etContact.getText().toString();
                age=etAge.getText().toString();
                query=etQuery.getText().toString();

                if(name.isEmpty() || email.isEmpty() || contact.isEmpty() || age.isEmpty() || query.isEmpty()){
                    Toast.makeText(ContactUs.this,"Please Fill all the Details",Toast.LENGTH_SHORT).show();
                }
                else if(contact.length()!=10){
                    Toast.makeText(ContactUs.this,"Please enter a Valid Contact Number",Toast.LENGTH_SHORT).show();
                 }
                else if(Integer.parseInt(age)<=0){
                    Toast.makeText(ContactUs.this,"Please Enter a Valid Age",Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference myRef=dbRef.child("UserQuery").push();
                    myRef.child("Name").setValue(name);
                    myRef.child("Email").setValue(email);
                    myRef.child("ContactNumber").setValue(contact);
                    myRef.child("Age").setValue(age);
                    myRef.child("Query").setValue(query);
                    Toast.makeText(ContactUs.this,"Hello "+name+"!, Your Query was Submitted Succesfully. We will Contact you very Soon",Toast.LENGTH_LONG).show();
                    ContactUs.this.finish();
                }

            }
        });

        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUs.this.finish();
            }
        });
    }
}
