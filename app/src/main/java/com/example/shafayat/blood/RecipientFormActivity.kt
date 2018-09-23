package com.example.shafayat.blood

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_recipient_form.*
import java.util.*
import java.text.SimpleDateFormat


public class RecipientFormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    var millis : Long = 0

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        dateButton.setText(dayOfMonth.toString() + "-" + monthOfYear + "-" + year)
        var myDate = year.toString() + "/" + monthOfYear.toString() + "/" + dayOfMonth.toString() + " " + "23:59:59"
        var sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        var date = sdf.parse(myDate)
        millis = date.getTime()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipient_form)

        dateButton.setOnClickListener { v -> run{
            val calendar = Calendar.getInstance()
            DatePickerDialog.newInstance(this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show(fragmentManager, "Date Picker")
        } }

        submitButton.setOnClickListener { v -> run{
            var recipient = Recipient(nameEditText.text.toString(),
                    contactNoEditText.text.toString(),
                    bloodGroupSpinner.selectedItem.toString(),
                    hospitalNameEditText.text.toString(),
                    areaSpinner.selectedItem.toString(),
                    millis)
            var database = FirebaseFirestore.getInstance()
            var firebaseData : CollectionReference = database.collection("recipients")
            firebaseData.add(recipient)

            var intent = Intent(this, DonorListActivity::class.java)
            intent.putExtra("group", bloodGroupSpinner.selectedItem.toString())
            intent.putExtra("area", areaSpinner.selectedItem.toString())
            startActivity(intent)

        } }
    }
}
