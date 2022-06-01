package com.tecazuay.complexivog2c2.dto.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailBody {
    private String content;
    private String email;
    private String subject;
    private String[] imagen;
    private String text2;
}
