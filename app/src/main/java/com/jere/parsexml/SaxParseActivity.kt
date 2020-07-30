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

/**
 * @author jere
 */
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
            //使用指定的DefaultHandler将给定的输入流内容解析为XML。
            parser.parse(inputStream, Handler())

            //展示解析出来的内容数据
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
        private var currentValue = ""
        var currentElement = false

        /**
         * 开始接收元素
         */
        override fun startElement(
            uri: String?,
            localName: String?,
            qName: String?,
            attributes: Attributes?
        ) {
            super.startElement(uri, localName, qName, attributes)
            //一个新的节点，即一个新的 user 节点
            currentElement = true
            currentValue = ""
            if (localName == USER_TAG) {
                user = User()
            }
        }

        /**
         * 当前元素解析完毕
         */
        override fun endElement(uri: String?, localName: String?, qName: String?) {
            super.endElement(uri, localName, qName)
            currentElement = false
            when(localName) {
                NAME_TAG -> user.name = currentValue
                AGE_TAG -> user.age = currentValue.toInt()
                //当前元素解析好后，即第一个 user 节点解析好后，加入到 userList
                USER_TAG -> userList.add(user)
            }

        }

        /**
         * 解析元素
         */
        override fun characters(ch: CharArray?, start: Int, length: Int) {
            super.characters(ch, start, length)
            if (currentElement) {
                currentValue += String(ch!!, start, length)
            }
        }
    }

}