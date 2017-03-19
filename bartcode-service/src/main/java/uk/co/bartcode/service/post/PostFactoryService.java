package uk.co.bartcode.service.post;

import com.google.common.base.Splitter;
import com.vladsch.flexmark.IParse;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
class PostFactoryService {

    private static final Logger logger = LoggerFactory.getLogger(PostFactoryService.class);
    private static final String EXCERPT_END_TAG = "<!--more-->";
    private static final IParse parser = Parser.builder().build();
    private static final IRender renderer = HtmlRenderer.builder().build();
    private final PostPropertyExtractor propertyExtractor;

    @Value("${application.base-url}")
    private String baseUrl;

    @Autowired
    PostFactoryService(PostPropertyExtractor propertyExtractor) {
        this.propertyExtractor = propertyExtractor;
    }

    Post create(String file) {
        try {
            logger.debug("Processing, file={}", file);
            String markdown = new String(Files.readAllBytes(Paths.get(file)));
            Map<String, String> properties = propertyExtractor.extractProperties(markdown);
            ZonedDateTime published = getPublished(properties.get("date"), file);
            String slug = getSlug(file, published);
            String html = getHtml(markdown);
            String excerpt = getExcerpt(html);
            return Post.builder()
                    .withSlug(slug)
                    .withFile(file)
                    .withCanonicalUrl(properties.getOrDefault("url", baseUrl + slug))
                    .withTitle(properties.getOrDefault("title", getTitleFromSlug(slug)))
                    .withDescription(properties.getOrDefault("description", ""))
                    .withText(html)
                    .withExcerpt(excerpt)
                    .withTags(getTags(properties.get("tags")))
                    .withPublished(published)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Cannot create document from file=" + file, e);
        }
    }

    private String getSlug(String file, ZonedDateTime published) {
        return String.format("/%s/%s/%s/",
                published.getYear(),
                String.format("%02d", published.getMonthValue()),
                com.google.common.io.Files.getNameWithoutExtension(file));
    }

    private String getTitleFromSlug(String id) {
        return StringUtils.capitalize(id
                .replaceFirst("/[0-9]+/[0-9]+/", "")
                .replace("-", " ")
                .replaceFirst("/$", ""));
    }

    private List<String> getTags(String tags) {
        return (tags == null) ? Collections.emptyList() :
                Splitter.on(',')
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToList(tags);
    }

    private ZonedDateTime getPublished(String dateString, String file) throws IOException {
        FileTime lastModifiedTime = Files.getLastModifiedTime(Paths.get(file));
        if (dateString != null) {
            return ZonedDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault())
                    .with(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            return ZonedDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault());
        }
    }

    private String getHtml(String markdown) {
        return renderer.render(parser.parse(markdown));
    }

    private String getExcerpt(String source) {
        int i = source.indexOf(EXCERPT_END_TAG);
        return (i == -1) ? source : source.substring(0, i + EXCERPT_END_TAG.length());
    }

}