package uk.co.bartcode.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import uk.co.bartcode.service.post.DatePostCount;
import uk.co.bartcode.service.post.Post;
import uk.co.bartcode.service.post.TagPostCount;

@Configuration
class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(DatePostCount.class, TagPostCount.class, Post.class);
        config.getCorsRegistry().addMapping("/**")
                .allowedOrigins("http://localhost:4000", "http://localhost:4200")
                .allowedMethods("GET");
    }


}
