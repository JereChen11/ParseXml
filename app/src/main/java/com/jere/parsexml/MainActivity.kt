package com.jere.parsexml

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xmlPullParseBtn.setOnClickListener(this)
        domParseBtn.setOnClickListener(this)
        saxParseBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.xmlPullParseBtn -> {
                startActivity(Intent(this, XmlPullParseTestActivity::class.java))
            }
            R.id.domParseBtn -> {
                startActivity(Intent(this, DomParseTestActivity::class.java))
            }
            R.id.saxParseBtn -> {
                startActivity(Intent(this, SaxParseActivity::class.java))
            }
        }
    }


}