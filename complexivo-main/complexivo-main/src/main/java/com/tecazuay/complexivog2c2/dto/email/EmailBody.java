package com.tecazuay.complexivog2c2.dto.email;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailBody {
    private String content;
    private List<String> email;
    private String subject;
    private String[] imagen;
    private String text2;
}
