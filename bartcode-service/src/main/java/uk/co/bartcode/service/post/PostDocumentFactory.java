package uk.co.bartcode.service.post;

import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.co.bartcode.service.document.*;

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
class PostDocumentFactory implements DocumentFactory {

    private static final Logger logger = LoggerFactory.getLogger(PostDocumentFactory.class);
    private static final String EXCERPT_END_TAG = "<!--more-->";

    private final MarkdownFileParser markdownFileParser;
    private final PropertyExtractor propertyExtractor;
    private final MetadataGenerator metadataGenerator;
    private final PostStatsService postStatsService;
    private final String postPath;

    @Autowired
    PostDocumentFactory(MarkdownFileParser markdownFileParser,
                        PropertyExtractor propertyExtractor,
                        MetadataGenerator metadataGenerator,
                        PostStatsService postStatsService,
                        @Value("${application.post-path}") String postPath) {
        this.markdownFileParser = markdownFileParser;
        this.propertyExtractor = propertyExtractor;
        this.metadataGenerator = metadataGenerator;
        this.postStatsService = postStatsService;
        this.postPath = postPath;
    }

    @Override
    public boolean supports(String file) {
        return StringUtils.startsWithIgnoreCase(file, postPath);
    }

    @Override
    public Document create(String file) {
        try {
            logger.debug("Processing, file={}", file);
            String markdown = markdownFileParser.read(file);
            Map<String, String> properties = propertyExtractor.extractProperties(markdown);
            ZonedDateTime published = generatePublished(file, properties.get("date"));
            String relativeUrl = generateRelativeUrl(file, published);
            DocumentMetadata metadata = metadataGenerator.generate(properties, relativeUrl);
            String html = markdownFileParser.render(markdown);

            return new Post.Builder()
                    .withRelativeUrl(relativeUrl)
                    .withFile(file)
                    .withMetadata(metadata)
                    .withText(html)
                    .withExcerpt(markdownFileParser.render(generateExcerpt(html)))
                    .withPublished(published)
                    .withTags(generateTags(properties.get("tags")))
                    .withStats(postStatsService.getUpdatedStats(metadata.getCanonicalUrl()))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Cannot process document from file=" + file, e);
        }
    }

    private ZonedDateTime generatePublished(String file, String customDate) throws IOException {
        FileTime fileTime = Files.getLastModifiedTime(Paths.get(file));
        ZonedDateTime fileDateTime = ZonedDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
        if (customDate != null) {
            return fileDateTime.with(LocalDate.parse(customDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            return fileDateTime;
        }
    }

    private String generateRelativeUrl(String file, ZonedDateTime published) {
        return String.format("/%s/%s/%s",
                published.getYear(),
                String.format("%02d", published.getMonthValue()),
                StringUtils.stripFilenameExtension(StringUtils.getFilename(file)));
    }


    private String generateExcerpt(String src) {
        int i = src.indexOf(EXCERPT_END_TAG);
        return (i == -1) ? src : src.substring(0, i);
    }

    private List<String> generateTags(String tags) {
        return (tags == null) ? Collections.emptyList() :
                Splitter.on(',')
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToList(tags);
    }

}