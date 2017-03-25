package uk.co.bartcode.service.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
class DocumentFactoryImpl implements DocumentFactory {

    private static final Logger logger = LoggerFactory.getLogger(DocumentFactoryImpl.class);
    private final MarkdownFileParser markdownFileParser;
    private final PropertyExtractor propertyExtractor;
    private final MetadataGenerator metadataGenerator;
    private final String documentPath;

    @Autowired
    DocumentFactoryImpl(MarkdownFileParser markdownFileParser,
                        PropertyExtractor propertyExtractor,
                        MetadataGenerator metadataGenerator,
                        @Value("${application.document-path}") String documentPath) {
        this.markdownFileParser = markdownFileParser;
        this.propertyExtractor = propertyExtractor;
        this.metadataGenerator = metadataGenerator;
        this.documentPath = documentPath;
    }

    @Override
    public boolean supports(String file) {
        return StringUtils.startsWithIgnoreCase(file, documentPath);
    }

    @Override
    public Document create(String file) {
        logger.debug("Processing, file={}", file);

        String relativeUrl = generateRelativeUrl(file);
        String markdown = markdownFileParser.read(file);
        Map<String, String> properties = propertyExtractor.extractProperties(markdown);

        return new Document.Builder()
                .withRelativeUrl(relativeUrl)
                .withFile(file)
                .withText(markdownFileParser.render(markdown))
                .withMetadata(metadataGenerator.generate(properties, relativeUrl))
                .build();
    }

    private String generateRelativeUrl(String file) {
        return String.format("/%s", StringUtils.stripFilenameExtension(StringUtils.getFilename(file)));
    }

}