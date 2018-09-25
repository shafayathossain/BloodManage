package com.example.shafayat.blood

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_recipient_form.*
import java.util.*
import java.text.SimpleDateFormat
import ir.mtajik.android.advancedsmsmanager.model.MySmsManager
import ir.mtajik.android.advancedsmsmanager.SmsHandler




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
            if(isNetworkAvailable()){
                var recipient = Recipient(nameEditText.text.toString(),
                        contactNoEditText.text.toString(),
                        bloodGroupSpinner.selectedItem.toString(),
                        hospitalNameEditText.text.toString(),
                        areaSpinner.selectedItem.toString(),
                        millis)
                var database = FirebaseFirestore.getInstance()
                var firebaseData : CollectionReference = database.collection("recipients")
                firebaseData.add(recipient)
            }else{
                showNoInternetDialog()
            }

            var intent = Intent(this, DonorListActivity::class.java)
            intent.putExtra("group", bloodGroupSpinner.selectedItem.toString())
            intent.putExtra("area", areaSpinner.selectedItem.toString())
            startActivity(intent)

        } }
    }

    private fun showNoInternetDialog() {
        MaterialDialog.Builder(this)

                .title("You have no internet connection")
                .content("Do you want to send sms?")
                .positiveText("Yes ")
                .onPositive(object : MaterialDialog.SingleButtonCallback {
                    override fun onClick(dialog: MaterialDialog, which: DialogAction) {

                        sendSms()

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

    private fun sendSms() {
        val smsBody = "recipient name"+ nameEditText.text.toString()+
                " contact_no " + contactNoEditText.text.toString()+
                " group "+ bloodGroupSpinner.selectedItem.toString()+
                " area " + areaSpinner.selectedItem.toString()+
                " hospital " + hospitalNameEditText.text.toString()
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.putExtra("sms_body", smsBody)
        sendIntent.type = "vnd.android-dir/mms-sms"
        startActivity(sendIntent)
    }

    fun isNetworkAvailable() : Boolean {
        var connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
