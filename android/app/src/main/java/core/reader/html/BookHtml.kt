package core.reader.html

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import core.reader.TextUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.*
import kotlin.math.ceil

class BookHtml(
    private val htmlBodyText: String,
    private val pageWidth: Int,
    private val pageHeight: Int,
    private val encoding: String,
    private val context: Context
) {

    var currentStyle = BookStyle(
        fontSize = 16,
        spacing = 20,
        backgroundColorHEX = "#000000",
        textColorHEX = "#FFFFFF",
        edgesMargin = 16
    )
        set(value) {
            applyStyle(value)
        }

    private var document: Document = Jsoup.parse(htmlBodyText)
    private val pages = mutableListOf<String>()

    init {
        applyStyle(currentStyle)
        document.head().append("<meta charset=\"$encoding\">")
        precalculatePages()
    }

    private fun applyStyle(bookStyle: BookStyle) {
        val styleElement = Element("style").text(
            """
            body {
                font-size: ${bookStyle.fontSize}px;
                line-height: ${bookStyle.spacing}px;
                margin: ${bookStyle.edgesMargin}px;
                padding: 0;
                width: auto;
                height: auto;
                background-color: ${bookStyle.backgroundColorHEX} !important;
                color: ${bookStyle.textColorHEX} !important;
                text-align: justify;
                overflow-wrap: break-word;
            }
            
            p {
                  text-indent: 20px;
                  margin: 0;
                  overflow-wrap: break-word;
            }
            
            img {
                max-width: ${pageWidth - bookStyle.edgesMargin * 2}px;
                height: auto;
                display: block;
                margin-left: auto;
                margin-right: auto;
            }
            
            h4 {
                text-align: center;
            }
            """
        )
        document.head().select("style").remove()
        document.head().appendChild(styleElement)
    }
    fun getTotalPages(): Int {
        return pages.size
    }

    private fun calculateWordWidth(word: String): Int {
        return TextUtils.getTextWidth(context, word, currentStyle.fontSize.toFloat())
    }

    fun getPage(pageNumber: Int): String {
        return if (pageNumber in 1..pages.size) {
            wrapTextInHtml(pages[pageNumber - 1])
        } else {
            ""
        }
    }

    private fun splitTextAndImages(text: String): List<String> {
        return text.split(Regex("(<img.*?>)")).filter { it.isNotEmpty() }
    }

    private fun stripHtmlTags(html: String): String {
        val regex = "\\s?<[^>]*>\\s?"
        return html.replace(regex.toRegex(), "")
    }

    private fun precalculatePages() {
        val currentPageContent = StringBuilder()
        var currentHeight = 0

        val parts = splitTextAndImages(htmlBodyText)

        for (part in parts) {
            if (part.startsWith("<img")) {
                val base64Data = extractBase64FromImage(part)
                val (imageHeight, imageWidth) = calculateImageSize(base64Data)
                val scaledImageHeight = imageHeight * (pageWidth - currentStyle.edgesMargin * 2) / imageWidth

                if (currentHeight + scaledImageHeight > pageHeight) {
                    pages.add(currentPageContent.toString())
                    currentPageContent.clear()
                    currentHeight = 0
                }

                currentPageContent.append(part)
                currentHeight += scaledImageHeight

            } else {
                val words = Regex("""\S+|\s+""").findAll(part).map { it.value }.toList()

                var lineWidth = 0
                var linesUsed = 0
                val lineWords = mutableListOf<String>()
                var isHeader = false
                var lineSpacing = currentStyle.spacing.toFloat()

                if (part.startsWith("<h4")) {
                    isHeader = true
                    lineSpacing *= 2f
                }

                for (word in words) {
                    val wordWidth = calculateWordWidth(stripHtmlTags(word))

                    if (lineWidth*1.5 + wordWidth > pageWidth - currentStyle.edgesMargin * 2) {
                        currentPageContent.append(lineWords.joinToString(" "))
                        linesUsed++
                        lineWords.clear()
                        lineWidth = 0
                        currentHeight += lineSpacing.toInt()

                        if (currentHeight > pageHeight) {
                            pages.add(currentPageContent.toString())
                            currentPageContent.clear()
                            linesUsed = 0
                            currentHeight = 0
                        }
                    }

                    lineWords.add(word)
                    lineWidth += wordWidth
                }

                if (lineWords.isNotEmpty()) {
                    currentPageContent.append(lineWords.joinToString(" ")).append("</p><p>")
                    currentHeight += lineSpacing.toInt()
                }

                if (isHeader) {
                    currentPageContent.append("</h4>")
                }
            }
        }

        if (currentPageContent.isNotEmpty()) {
            pages.add(currentPageContent.toString())
        }
    }


    private fun calculateImageSize(base64Data: String): Pair<Int, Int> {
        val image = decodeBase64Image(base64Data) ?: return Pair(0, 0)
        return Pair(image.height, image.width)
    }

    private fun decodeBase64Image(base64Image: String?): Bitmap? {
        if (base64Image.isNullOrEmpty()) return null
        return try {
            val decodedBytes = Base64.getDecoder().decode(base64Image.toByteArray())
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }

    private fun extractBase64FromImage(imgTag: String): String {
        val regex = """src="data:image;base64,([^"]+)"""".toRegex()
        return regex.find(imgTag)?.groupValues?.get(1) ?: ""
    }

    private fun splitBodyByImages(bodyText: String): List<String> {
        val imageRegex = """<img[^>]*>""".toRegex()
        val parts = mutableListOf<String>()
        var lastIndex = 0

        imageRegex.findAll(bodyText).forEach { match ->
            val textBeforeImage = bodyText.substring(lastIndex, match.range.first)
            if (textBeforeImage.isNotEmpty()) parts.add(textBeforeImage)
            parts.add(match.value)
            lastIndex = match.range.last + 1
        }

        val remainingText = bodyText.substring(lastIndex)
        if (remainingText.isNotEmpty()) parts.add(remainingText)

        return parts
    }

    private fun wrapTextInHtml(text: String): String {
        return """
            <html>
            <head>${document.head().html()}</head>
            <body>${text}</body>
            </html>
        """.trimIndent()
    }

    data class BookStyle(
        var fontSize: Int,
        var spacing: Int,
        var backgroundColorHEX: String,
        var textColorHEX: String,
        var edgesMargin: Int
    )
}
