package uk.co.bartcode.service.document;

import com.vladsch.flexmark.IParse;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class MarkdownFileParser {

    private static final MutableDataHolder options = new MutableDataSet()
            .set(HtmlRenderer.SUPPRESS_HTML_COMMENT_BLOCKS, true);
    private static final IParse parser = Parser.builder(options).build();
    private static final IRender renderer = HtmlRenderer.builder(options).build();

    public String read(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read, file=" + file, e);
        }
    }

    public String render(String markdown) {
        return renderer.render(parser.parse(markdown));
    }

}