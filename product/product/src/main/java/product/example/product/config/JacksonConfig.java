package product.example.product.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configure Jackson to allow deeper nesting levels
        objectMapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, true);

        // Disable failing on empty beans
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // Register any custom modules if necessary (optional)
        SimpleModule module = new SimpleModule();
        // Add custom serializers or deserializers if needed
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
