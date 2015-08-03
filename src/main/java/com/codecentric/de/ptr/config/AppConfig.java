package com.codecentric.de.ptr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by benjaminwilms on 24.07.15.
 */
@Configuration
@Import(
        PersistenceConfig.class

)
class AppConfig {


}
