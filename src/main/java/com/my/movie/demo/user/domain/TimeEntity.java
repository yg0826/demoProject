package com.my.movie.demo.user.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {

    @CreatedDate
    private String indate;
    @LastModifiedDate
    private String modifiedDate;

    @PrePersist// 해당 엔티티를 저장하기 이전에 실행
    public void onPrePersist(){
        this.indate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.modifiedDate=this.indate;
    }

    @PreUpdate // 해당 엔티티를 업데이트하기 이전에 실행
    public void onPreUpdate(){
        this.modifiedDate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
