package app.Link.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of("your_aws_region")) // e.g., Region.US_EAST_1
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("your_access_key_id", "your_secret_access_key")))
                .build();
    }
}

