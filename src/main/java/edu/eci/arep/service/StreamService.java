package edu.eci.arep.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;



import edu.eci.arep.model.Post;
import edu.eci.arep.response.StreamResponse;

@ApplicationScoped
public class StreamService {
    
    private static String mongoURL = "mongodb://mydb:27017";
    private static MongoClient mongoClient = setMongo();


    private static MongoClient setMongo(){
        MongoClient mongoClient11 = MongoClients.create(mongoURL);
        return mongoClient11;
    }

    public static List<StreamResponse> getStreams() {
        List<StreamResponse> posts = new ArrayList<>();
        try (MongoCursor<Document> cursor = getCollection().find().sort(Sorts.descending("creationDate")).limit(10).iterator()) {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                StreamResponse post = new StreamResponse();

                post.setAuthor(document.getString("author"));
                post.setPost(document.getString("message"));
                posts.add(post);
            }
        }

        return posts;
    }

    public static List<StreamResponse> add(Post newMessage) {
        Post message = new Post(newMessage.getAuthor(), newMessage.getMessage());

        Document document = new Document()
                .append("author", message.getAuthor())
                .append("post", message.getMessage())
                .append("creation_date", message.getCreationDate());

        getCollection().insertOne(document);

        return getStreams();
    }

    private static MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("post").getCollection("posts");
    }

}
