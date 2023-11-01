package edu.eci.arep.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;



import edu.eci.arep.model.Post;
import edu.eci.arep.response.StreamResponse;

@ApplicationScoped
public class StreamService {
    
    //private static String mongoURL = "mongodb://mydb:27017";

    @Inject
    private MongoClient mongoClient;


/*     private static MongoClient setMongo(){
        try{
        MongoClient mongoClient = new MongoClient("mongo-db",27017);
        return mongoClient;
        }catch(Exception i){
            i.printStackTrace();
        }
        return null;
    } */

    public  List<StreamResponse> getStreams() {
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

    public  List<StreamResponse> add(Post newMessage) {
        Post message = new Post(newMessage.getAuthor(), newMessage.getMessage());

        Document document = new Document()
                .append("author", message.getAuthor())
                .append("post", message.getMessage())
                .append("creation_date", message.getCreationDate());

        getCollection().insertOne(document);

        return getStreams();
    }

    public  MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("mydb").getCollection("messages");
    }

}
