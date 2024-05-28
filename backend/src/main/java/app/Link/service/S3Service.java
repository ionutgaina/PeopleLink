package app.Link.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class S3Service {

    private final S3Client s3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadFile(String bucketName, String keyName, String filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.putObject(putObjectRequest, Paths.get(filePath));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = LocalDateTime.now().toString();

        s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType("image/png")
                            .build(),
                software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes()));
        System.out.println(file.getName());
        return generatePresignedUrl(fileName).toString();
    }

    public URL generatePresignedUrl(String key) {
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key).build());
    }
}
