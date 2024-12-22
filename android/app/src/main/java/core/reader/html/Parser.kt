package core.reader.html

import com.ibm.icu.text.CharsetDetector
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.nio.charset.Charset

class Parser {

    companion object{

        fun getEncoding(xml: String): String {
            val regex = """<\?xml[^>]*encoding="([^"]+)"""".toRegex()
            val match = regex.find(xml)
            return match?.groups?.get(1)?.value ?: "UTF-8"
        }

        fun getEncoding(bytes: ByteArray, length: Int): Charset? {
            val content = String(bytes, 0, length, Charsets.UTF_8)
            val regex = """<\?xml[^>]*encoding=["']([^"']+)["']""".toRegex()
            val match = regex.find(content)
            val encoding = match?.groups?.get(1)?.value

            return if (encoding != null) {
                try {
                    Charset.forName(encoding)
                } catch (e: Exception) {
                    Charsets.UTF_8
                }
            } else {
                Charsets.UTF_8
            }
        }

    }

    fun parseXmlToHtml(xml: String): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("<html><body>")
        val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()

        val parser = factory.newPullParser()
        parser.setInput(xml.reader())

        val imageMap = mutableMapOf<String, String>()

        var eventType = parser.eventType
        var currentText = StringBuilder()
        var imageId = ""

        var lastParser: String? = ""

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "title" -> stringBuilder.append("<h4>")
                        "p" -> stringBuilder.append("<p>")
                        "empty-line" -> stringBuilder.append("<br>")
                        "section" -> stringBuilder.append("<div>")
                        "image" -> {
                            val href = parser.getAttributeValue(null, "l:href")
                            if (href != null) {
                                stringBuilder.append("<img id=\"${href.subSequence(1, href.length)}\">")
                            }
                        }
                        "binary" -> {
                            val id = parser.getAttributeValue(null, "id")
                            if (id != null) {
                                imageId = id
                                currentText.clear()
                            }
                        }
                    }
                }
                XmlPullParser.TEXT -> {
                    val text = parser.text.trim()
                    if (text.isNotEmpty() && lastParser != "binary") {
                        stringBuilder.append(text)
                    }

                    if (lastParser == "binary" && imageId.isNotEmpty()) {
                        currentText.append(text)
                    }
                }
                XmlPullParser.END_TAG -> {
                    when (parser.name) {
                        "title" -> stringBuilder.append("</h4>")
                        "p" -> stringBuilder.append("</p>")
                        "empty-line" -> stringBuilder.append("</br>")
                        "section" -> stringBuilder.append("</div>")
                        "binary" -> {
                            if (currentText.isNotEmpty() && !imageMap.containsKey(imageId)) {
                                imageMap[imageId] = currentText.toString()
                            }
                        }
                    }
                }
            }

            lastParser = parser.name
            eventType = parser.next()
        }

        stringBuilder.append("</body></html>")

        return replaceImageIdsWithSrc(stringBuilder.toString(), imageMap)
    }

    private fun replaceImageIdsWithSrc(html: String, imageMap: Map<String, String>): String {
        var updatedHtml = html

        imageMap.forEach { (id, base64Data) ->
            val imgTagRegex = """<img\s+id=["']$id["']\s*/?>""".toRegex()
            val replacement = """<img id="$id" src="data:image;base64,$base64Data"/>"""
            updatedHtml = updatedHtml.replace(imgTagRegex, replacement)
        }

        return updatedHtml
    }

}