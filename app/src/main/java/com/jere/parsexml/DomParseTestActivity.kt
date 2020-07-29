package com.jere.parsexml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dom_parse_test.*
import org.w3c.dom.Element
import org.w3c.dom.Node
import javax.xml.parsers.DocumentBuilderFactory

class DomParseTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dom_parse_test)

        domParseData()
    }

    private fun domParseData() {
        try {
            val inputStream = assets.open(USER_INFO_XML_FILE)
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val doc = dBuilder.parse(inputStream)

            val element = doc.documentElement
            element.normalize()

            //拿到所有 'user' 标签的节点
            val nodeList = doc.getElementsByTagName(USER_TAG)
            for (i in 0 until nodeList.length) {
                val node = nodeList.item(i)
                //判断是否是元素节点类型
                if (node.nodeType == Node.ELEMENT_NODE) {
                    //将 node 转换成 Element 类型，为了通过getElementsByTagName()方法获取到节点数据
                    val theElement = node as Element
                    val textContent = "${domParseShowTv.text} \n\n" +
                            "Name: ${getValue(NAME_TAG, theElement)} \n" +
                            "Age: ${getValue(AGE_TAG, theElement)} \n" +
                            "------------------\n"

                    domParseShowTv.text = textContent
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getValue(tag: String, element: Element): String {
        //根据指定的标签取出相对应的节点
        val nodeList = element.getElementsByTagName(tag).item(0).childNodes
        val node = nodeList.item(0)
        return node.nodeValue
    }
}