package com.my.movie.demo.user.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class TimeEntity {

    private String indate;
    private String modifiedDate;

    @PrePersist
    public void onPrePersist(){
        this.indate= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.modifiedDate=this.indate;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedDate=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
