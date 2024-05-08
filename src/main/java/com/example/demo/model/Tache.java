package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.TimeZone;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String Subject;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date StartTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date EndTime;
    private TimeZone StartTimeZone = TimeZone.getTimeZone("Africa/Casablanca");
    private TimeZone EndTimeZone = TimeZone.getTimeZone("Africa/Casablanca");
    private boolean IsAllDay;
    private String RecurrenceRule;
    private int RecurrenceId;
    private String RecurrenceException;
    private String Location;
    private String Description;
    private int GroupId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_mob_id")
    private UserMob mobileUser;

}