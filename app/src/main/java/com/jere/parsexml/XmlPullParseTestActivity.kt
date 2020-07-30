package com.jere.parsexml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_xml_pull_parse_test.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

/**
 * @author jere
 */
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
            //设置XML解析器可以处理命名空间
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            //设置解析器将要处理的输入流，将重置解析器状态并将事件类型设置为初始值START_DOCUMENT
            parser.setInput(inputStream, null)

            //返回当前事件的类型
            var eventType = parser.eventType
            var parseContentText = ""

            //一直循环解析，直到解析到XML文档结束节点
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //返回当前元素的名称，如 START_TAG，END_TAG
                val tagName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (tagName == USER_TAG) user = User()
                    }
                    XmlPullParser.TEXT -> {
                        //以String形式返回当前事件的文本内容
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
            //展示解析出来的内容数据
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