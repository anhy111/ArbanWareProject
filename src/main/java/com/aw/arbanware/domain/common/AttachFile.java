package com.aw.arbanware.domain.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(AttachFileKey.class)
@Getter @Setter
public class AttachFile extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "ATTACH_FILE_ID")
    private Long id;

    @Id
    private int sequence;

    private String storedName;
    private String originalName;
    private String storedPath;
    private String extensionName;
    private int fileSize;

}
