package com.kanban.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    @Id
    private String username;
    private String notificationMessage;
    private JSONObject jsonObject;
}
