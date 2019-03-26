package com.example.logintest;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Arrays;

public class AddContacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress("192.168.0.100", 27017))))
                        .build());
                MongoDatabase database = mongoClient.getDatabase("contact");
                MongoCollection<Document> collection = database.getCollection("person");
                EditText editTextName = findViewById(R.id.nameEditText);
                EditText editTextAddress =findViewById(R.id.addressEditText);
                EditText editTextCity= findViewById(R.id.cityEditText);
                EditText editTextPhone =findViewById(R.id.phoneEditText);
                EditText editTextEmail =findViewById(R.id.emailEditText);
                Document doc = new Document("name", editTextName.getText().toString() )
                        .append("address", editTextAddress.getText().toString() )
                        .append("city", editTextCity.getText().toString() )
                        .append("phone", editTextPhone.getText().toString() )
                        .append("email", editTextEmail.getText().toString() );

                collection.insertOne(doc);
            }});

        Button ContactList = findViewById(R.id.btnContact);
        ContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactsList.class);
                startActivity(intent);
            }
        });
    }
}
