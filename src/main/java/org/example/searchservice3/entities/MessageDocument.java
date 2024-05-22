package org.example.searchservice3.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@Data
@Getter
@Setter
@Document(indexName = "messages", createIndex = true)
public class MessageDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "from")
    private String from;
    @Field(type = FieldType.Text, name = "to")
    private String to;
    @Field(type = FieldType.Text, name = "message")
    private String message;
    @Field(type = FieldType.Date, name = "date")
    private ZonedDateTime date;
}
