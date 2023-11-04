package com.aw.arbanware.domain.common.embedded;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.UUID;

@Embeddable
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachFileInfo {

    private String storedFileName;  // 저장파일명
    private String originalFileName;    // 원본파일명
    private String storedPath;  //저장경로
    private String extensionName;   // 확장자명
    private long fileSize;   // 파일크기

    public AttachFileInfo(MultipartFile multipartFile, String storedPath) {
        originalFileName = multipartFile.getOriginalFilename();
        fileSize = multipartFile.getSize();
        if (StringUtils.hasText(this.originalFileName)) {
            extensionName = originalFileName.substring(originalFileName.lastIndexOf("."));
            storedFileName = UUID.randomUUID().toString();
            this.storedPath = storedPath.replace("\\", "/");
        }
    }

    public static String todayToFolder() {
        final LocalDate now = LocalDate.now();
        return String.format("%s\\%s\\%s\\",
                now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }
}
