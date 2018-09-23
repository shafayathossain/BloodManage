package com.example.shafayat.blood

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_donor_list.*


class DonorListActivity : AppCompatActivity(), CallButtonOnClickListener {

    var donors : MutableList<Donor> = arrayListOf()
    lateinit var adapter : DonorListAdapter
    lateinit var contact_number : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_list)

        var group = intent.getStringExtra("group")
        var area = intent.getStringExtra("area")

        var database = FirebaseFirestore.getInstance()
        var firebaseData : CollectionReference = database.collection("donors")

        firebaseData
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            val data = document.data
                            if(data.get("area").toString().toLowerCase().equals(area.toLowerCase()) &&
                                    data.get("group").toString().toLowerCase().equals(group.toLowerCase())){
                                donors.add(Donor(data.get("name").toString(),
                                        data.get("contact_no").toString(),
                                        data.get("group").toString(),
                                        data.get("area").toString()))
                            }
                        }
                        if(donors.size>0){
                            donorListRecyclerView.layoutManager = LinearLayoutManager(this)
                            adapter = DonorListAdapter(donors, this)
                            donorListRecyclerView.adapter = adapter
                        }
                    } else {

                    }
                })
    }

    override fun onClick(contact_number: String) {
        call(contact_number)
        this.contact_number = contact_number
    }

    fun call(contact_number: String) {

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 0)
        } else {
            dialogue()
        }
    }

    fun dialogue() {
        MaterialDialog.Builder(this)

                .title("Call to Donor")
                .content("Do you want to make a phone call to donor?")
                .positiveText("Yes ")
                .onPositive(object : MaterialDialog.SingleButtonCallback {
                    override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:" + contact_number)
                        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this@DonorListActivity, arrayOf(Manifest.permission.CALL_PHONE), 0)
                            return
                        }
                        startActivity(callIntent)

                    }
                })
                .negativeText("No")
                .onNegative(object : MaterialDialog.SingleButtonCallback {
                    override fun onClick(dialog: MaterialDialog, which: DialogAction) {
                        // do nothing
                        dialog.dismiss()
                    }
                })
                .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            0 -> {
                var isPerpermissionForAllGranted = false
                if (grantResults.size > 0 && permissions.size == grantResults.size) {
                    for (i in permissions.indices) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            isPerpermissionForAllGranted = true
                        } else {
                            isPerpermissionForAllGranted = false
                        }
                    }

                    Log.e("value", "Permission Granted, Now you can use local drive .")
                } else {
                    isPerpermissionForAllGranted = true
                    Log.e("value", "Permission Denied, You cannot use local drive .")
                }
                if (isPerpermissionForAllGranted) {
                    dialogue()
                }
            }
        }
    }
}
