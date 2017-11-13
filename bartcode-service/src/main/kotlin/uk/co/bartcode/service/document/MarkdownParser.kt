package uk.co.bartcode.service.document

import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import org.springframework.stereotype.Component
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@Component
class MarkdownParser {

    fun parse(file: String) = render(read(file))

    private fun read(file: String): String {
        try {
            return String(Files.readAllBytes(Paths.get(file)))
        } catch (e: IOException) {
            throw RuntimeException("Cannot read, path=" + file, e)
        }

    }

    private fun render(markdown: String) = renderer.render(parser.parse(markdown))

    companion object {
        private val options = MutableDataSet()
        private val parser = Parser.builder(options).build()
        private val renderer = HtmlRenderer.builder(options).build()
    }

}