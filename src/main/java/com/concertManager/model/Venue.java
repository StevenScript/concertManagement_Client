package com.concertManager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private List<Event> events = new ArrayList<>();
}
