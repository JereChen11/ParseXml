package com.jere.parsexml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_xml_pull_parse_test.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream


class XmlPullParseTestActivity : AppCompatActivity() {
    private val userList: ArrayList<User> = ArrayList()
    private var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml_pull_parse_test)

        xmlParseData()
    }

    private fun xmlParseData() {
        try {
            val inputStream: InputStream = assets.open(USER_INFO_XML_FILE)
            val parserFactory =
                XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)

            var eventType = parser.eventType
            var parseContentText = ""

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName == USER_TAG) user = User()
                    }
                    XmlPullParser.TEXT -> {
                        parseContentText = parser.text
                    }
                    XmlPullParser.END_TAG -> {
                        when (tagName) {
                            NAME_TAG -> user.name = parseContentText
                            AGE_TAG -> user.age = parseContentText.toInt()
                            USER_TAG -> userList.add(user)
                        }
                    }

                }
                eventType = parser.next()
            }
            for (user in userList) {
                val textContent = "${xmlPullParseShowTv.text} \n\n" +
                        "Name: ${user.name} \n" +
                        "Age: ${user.age} \n" +
                        "------------------\n"
                xmlPullParseShowTv.text = textContent
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        }
    }


}