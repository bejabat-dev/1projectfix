package com.example.a1projectfix;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Example {
    CollectionReference db = FirebaseFirestore.getInstance().collection("users");

    private void init(){
        db.document("Haris").get();
    }

}
