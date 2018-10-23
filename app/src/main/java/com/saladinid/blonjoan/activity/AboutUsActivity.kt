package com.saladinid.blonjoan.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.saladinid.blonjoan.R

class AboutUsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle ? ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
