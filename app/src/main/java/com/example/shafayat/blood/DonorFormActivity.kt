package com.example.shafayat.blood

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_recipient_form.*

public class DonorFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_form)

        submitButton.setOnClickListener(View.OnClickListener {
            val donor = Donor(name = nameEditText.text.toString(),
                    contact_no = contactNoEditText.text.toString(),
                    group = bloodGroupSpinner.selectedItem.toString(),
                    area = areaSpinner.selectedItem.toString())

            var database = FirebaseFirestore.getInstance()
            var firebaseData : CollectionReference = database.collection("donors")
            firebaseData.add(donor).addOnSuccessListener { documentReference -> run{
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            } }.addOnFailureListener { exception -> run{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            } }
        })
    }
}