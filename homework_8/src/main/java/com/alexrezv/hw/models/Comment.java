package com.alexrezv.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private ObjectId id;

    @Field(name = "username")
    private String userName;

    @Field(name = "content")
    private String content;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Field(name = "book_id")
    private ObjectId bookId;

}
