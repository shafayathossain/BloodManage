package com.example.shafayat.blood

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        donorButton.setOnClickListener { v -> run{
            var intent = Intent(this, DonorFormActivity::class.java)
            startActivity(intent)
        } }

        recipientButton.setOnClickListener { v -> run{
            var intent = Intent(this, RecipientFormActivity::class.java)
            startActivity(intent)
        } }
    }
}
