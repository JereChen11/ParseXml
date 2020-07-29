package com.jere.parsexml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sax_parse.*
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory


class SaxParseActivity : AppCompatActivity() {
    var userList: ArrayList<User> = ArrayList()
    var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sax_parse)

        saxParseData()
    }

    private fun saxParseData() {
        try {
            val parserFactory: SAXParserFactory = SAXParserFactory.newInstance()
            val parser: SAXParser = parserFactory.newSAXParser()

            val inputStream: InputStream = assets.open(USER_INFO_XML_FILE)
            parser.parse(inputStream, Handler())

            for (user in userList) {
                val textContent = "${saxParseShowTv.text} \n\n" +
                        "Name: ${user.name} \n" +
                        "Age: ${user.age} \n" +
                        "------------------\n"
                saxParseShowTv.text = textContent
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        }
    }

    inner class Handler : DefaultHandler() {
        var currentValue = ""
        var currentElement = false

        override fun startElement(
            uri: String?,
            localName: String?,
            qName: String?,
            attributes: Attributes?
        ) {
            super.startElement(uri, localName, qName, attributes)
            currentElement = true
            currentValue = ""
            if (localName == "user") {
                user = User()
            }
        }

        override fun endElement(uri: String?, localName: String?, qName: String?) {
            super.endElement(uri, localName, qName)
            currentElement = false
            when(localName) {
                NAME_TAG -> user.name = currentValue
                AGE_TAG -> user.age = currentValue.toInt()
                USER_TAG -> userList.add(user)
            }

        }

        override fun characters(ch: CharArray?, start: Int, length: Int) {
            super.characters(ch, start, length)
            if (currentElement) {
                currentValue += String(ch!!, start, length)
            }
        }
    }

}