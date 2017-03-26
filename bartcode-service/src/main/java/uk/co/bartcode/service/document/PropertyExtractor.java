package uk.co.bartcode.service.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PropertyExtractor {

    private static final Pattern PATTERN_PROPERTY_BLOCK = Pattern.compile("[^\\s]*<!--(.*?)-->", Pattern.DOTALL);
    private static final Pattern PATTERN_PROPERTY = Pattern.compile("\\s*([a-zA-Z0-9_]+)\\s*[=|:]\\s*(.+)\\s*");
    private static final Logger logger = LoggerFactory.getLogger(PropertyExtractor.class);

    public Map<String, String> extractProperties(String source) {
        Matcher m = PATTERN_PROPERTY_BLOCK.matcher(source);
        Map<String, String> props = new HashMap<>();
        while (m.find()) {
            String commentBlock = m.group(1);
            props.putAll(extract(commentBlock));
        }
        logger.debug("Extracted: {}", props);
        return props;
    }

    private Map<String, String> extract(String src) {
        Matcher m = PATTERN_PROPERTY.matcher(src);
        Map<String, String> props = new HashMap<>();
        while (m.find()) {
            props.put(m.group(1), m.group(2));
        }
        return props;
    }

}