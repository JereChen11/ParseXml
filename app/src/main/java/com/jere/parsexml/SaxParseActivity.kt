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
         * 接收文档开始的通知
         */
        override fun startDocument() {
            super.startDocument()
        }

        /**
         * 接收文档结尾的通知
         */
        override fun endDocument() {
            super.endDocument()
        }

        /**
         * 接收元素开始的通知
         * localName: 返回我们定义的标签，即："Users","user", "name", "age"
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
         * 接收元素结束的通知
         * localName: 返回我们定义的标签，即："Users","user", "name", "age"
         */
        override fun endElement(uri: String?, localName: String?, qName: String?) {
            super.endElement(uri, localName, qName)
            //当前元素解析完成
            currentElement = false
            when(localName) {
                NAME_TAG -> user.name = currentValue
                AGE_TAG -> user.age = currentValue.toInt()
                //即第一个 user 节点解析好后，加入到 userList
                USER_TAG -> userList.add(user)
            }

        }

        /**
         * 接收元素内部字符数据的通知
         * ch: 返回标签内的内容，以 char[] 的形式存储
         * start: ch的起始Index, 即为0
         * length: ch数组的长度
         */
        override fun characters(ch: CharArray?, start: Int, length: Int) {
            super.characters(ch, start, length)
            if (currentElement) {
                //以String的形式返回标签内的内容
                currentValue += String(ch!!, start, length)
            }
        }
    }

}