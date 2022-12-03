package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class Create {

    private static final Random rand = new Random();

    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("blogging");
            MongoCollection<Document> authorCollection = sampleTrainingDB.getCollection("author");

            insertOneDocument(authorCollection);
            insertManyDocuments(authorCollection);
        }
    }

    private static void insertOneDocument(MongoCollection<Document> authorCollection) {
        authorCollection.insertOne(generateNewGrade("anuj", 100));
        System.out.println("Name inserted for anuj ");
    }

   private static void insertManyDocuments(MongoCollection<Document> authorCollection) {
        List<Document> grades = new ArrayList<>();
        for (double classId = 1d; classId <= 10d; classId++) {
            grades.add(generateNewGrade("blog heading", classId));
        }

        authorCollection.insertMany(grades, new InsertManyOptions().ordered(false));
        System.out.println("Ten  inserted for anuj.");
    }

    private static Document generateNewGrade(String nameN, double blogs) {
        List<Document> scores = asList(new Document("blog heading", "god is great").append("no_of_likes", 77));

        return new Document("_id", new ObjectId()).append("blog heading", nameN)
                                                  .append("no_of_likes", blogs)
                                                  .append("post", scores);
    }
}
