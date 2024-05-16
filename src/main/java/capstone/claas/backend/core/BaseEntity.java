package capstone.claas.backend.core;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDate;

@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedBy
    private LocalDate lastModified;

    private boolean isDeleted = false;

    @PrePersist
    public void onPrePersist( ) {
        this.createdDate = LocalDate.now();
        this.lastModified = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate( ) {
        this.lastModified = LocalDate.now();
    }

    public void delete( ) {
        this.isDeleted = true;
    }
}