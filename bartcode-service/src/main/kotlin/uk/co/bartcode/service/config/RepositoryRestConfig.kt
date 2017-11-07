package uk.co.bartcode.service.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import uk.co.bartcode.service.post.DatePostCount
import uk.co.bartcode.service.post.TagPostCount

@Configuration
class RepositoryRestConfig : RepositoryRestConfigurerAdapter() {

    override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration) {
        config.exposeIdsFor(DatePostCount::class.java, TagPostCount::class.java)
        config.corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:4000", "http://localhost:4200")
    }

}