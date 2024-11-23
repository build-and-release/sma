package com.technovate.school_management.configuration;

import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.technovate.school_management.model.PaystackConfig;
import com.technovate.school_management.service.contracts.AuthService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
@EnableSpringDataWebSupport(
        pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
@EnableAsync  // Enable async processing
public class ApplicationConfiguration {
    private final AuthService.UserDetailsService userDetailsService;

    @Value("${application_config.email_credentials.api_key}")
    private String API_KEY;
    @Value("${application_config.email_credentials.secret_key}")
    private String SECRET_KEY;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public MailjetClient mailjetClient() {
        ClientOptions options = ClientOptions.builder()
                .apiKey(API_KEY)
                .apiSecretKey(SECRET_KEY)
                .build();

        return new MailjetClient(options);
    }

    @Bean
    public Cloudinary cloudinary() {
        Dotenv dotenv = Dotenv.load();
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        cloudinary.config.secure = true;
        return cloudinary;
    }

    @Bean
    public PaystackConfig paystackConfig() {
        Dotenv dotenv = Dotenv.load();
        PaystackConfig paystack = new PaystackConfig();
        paystack.setPublicKey(dotenv.get("PAYSTACK_PUBLIC_KEY"));
        paystack.setSecretKey(dotenv.get("PAYSTACK_SECRET_KEY"));
        paystack.setPaystackUrl(dotenv.get("PAYSTACK_URL"));
        paystack.setCallBackUrl(dotenv.get("PAYSTACK_CALL_BACK_URL"));
        return paystack;
    }

    // Optional: Custom thread pool configuration for async tasks
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("AsyncTask-");
        executor.initialize();
        return executor;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}