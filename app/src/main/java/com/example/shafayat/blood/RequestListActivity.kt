package com.example.shafayat.blood

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_recipient_form.*
import kotlinx.android.synthetic.main.activity_request_list.*

public class RequestListActivity : AppCompatActivity(), CallButtonOnClickListener {


    var recipients : MutableList<Recipient> = arrayListOf()
    lateinit var adapter : RecipientListAdapter
    lateinit var contact_number : String

    override fun onClick(contact_number: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_list)

        var database = FirebaseFirestore.getInstance()
        var firebaseData : CollectionReference = database.collection("recipients")
        firebaseData.get()
                .addOnCompleteListener(OnCompleteListener {task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val data = document.data
                            recipients.add(Recipient(data.get("name").toString(),
                                    data.get("contact_no").toString(),
                                    data.get("group").toString(),
                                    data.get("hospital").toString(),
                                    data.get("area").toString(),
                                    data.get("time") as Long))
                        }


                        if(recipients.size>0){
                            requestListRecyclerView.layoutManager = LinearLayoutManager(this)
                            adapter = RecipientListAdapter(recipients, this)
                            requestListRecyclerView.adapter = adapter
                        }
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }

                })
    }
}