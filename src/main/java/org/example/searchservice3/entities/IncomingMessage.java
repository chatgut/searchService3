package org.example.searchservice3.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Getter
@Setter
@Document(indexName = "messages", createIndex = true)
public class IncomingMessage {


        @Id
        private String id;

        @Field(type = FieldType.Keyword, name = "userID")
        private String userID;

        @Field(type = FieldType.Text, name = "message")
        private long message;

        public IncomingMessage(String userID, long message) {
                this.userID = userID;
                this.message = message;
        }
}
