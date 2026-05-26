package com.example.ch4;

import com.example.ch4.service.S3Service;
import io.awspring.cloud.s3.S3Template;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @Mock
    private S3Template s3Template;

    @InjectMocks
    private S3Service s3Service;

    @Test
    @DisplayName("파일 업로드 성공 테스트")
    void uploadFileSuccess() throws Exception {
        // given
        ReflectionTestUtils.setField(s3Service, "bucket", "test-bucket");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "content".getBytes());

        // when
        String key = s3Service.upload(file);

        // then
        assertThat(key).contains("uploads/");
        verify(s3Template, times(1)).upload(eq("test-bucket"), anyString(), any(InputStream.class));
    }

    @Test
    @DisplayName("다운로드 URL 생성 테스트")
    void getDownloadUrlSuccess() throws Exception {
        // given
        ReflectionTestUtils.setField(s3Service, "bucket", "test-bucket");
        String key = "uploads/test.jpg";
        URL mockUrl = new URL("https://s3.amazonaws.com/test-url");

        when(s3Template.createSignedGetURL(eq("test-bucket"), eq(key), any(Duration.class)))
                .thenReturn(mockUrl);

        // when
        URL result = s3Service.getDownloadUrl(key);

        // then
        assertThat(result.toString()).isEqualTo("https://s3.amazonaws.com/test-url");
    }
}