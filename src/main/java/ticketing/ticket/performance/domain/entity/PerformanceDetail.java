package ticketing.ticket.performance.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import ticketing.ticket.base.BaseEntity;
import ticketing.ticket.performance.domain.dto.PerformanceDetailDto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Entity
@Data
public class PerformanceDetail extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long performanceDetailId;
    private String artist;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;
    
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;
    
    public PerformanceDetailDto toDto() {
        return PerformanceDetailDto.builder()
            .id(this.performanceDetailId)
            .artist(this.artist)
            .startTime(this.startTime)
            .endTime(this.endTime)
            .price(this.price)
            .performanceId(this.performance.getPerformanceId())
            .build();
    }
}