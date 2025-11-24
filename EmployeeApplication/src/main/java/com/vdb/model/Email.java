package com.vdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    private String toEmail;

    private String emailBody;

    private String emailSub;

    private String emailAttachment;

}
