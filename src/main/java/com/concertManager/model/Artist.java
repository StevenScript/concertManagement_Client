package com.concertManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {
    private Long id;
    private String stageName;
    private String genre;
    private Integer membersCount;
    private String homeCity;
    private Set<Event> events = new HashSet<>();
}
