package com.mongodb.quickstart;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class Read {

    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("blogging");
            MongoCollection<Document> authorCollection = sampleTrainingDB.getCollection("author");

            // find one document with new Document
            Document author1 = authorCollection.find(new Document("name", "Anuj")).first();
            System.out.println("Author 1: " + author1.toJson());

           // find one document with Filters.eq()
            Document author2 = authorCollection.find(eq("no_of_blogs", 2)).first();
            System.out.println("Author 2: " + author2.toJson());

            // find a list of documents and iterate throw it using an iterator.
            FindIterable<Document> iterable = authorCollection.find(gte("no_of_blogs", 34));
            MongoCursor<Document> cursor = iterable.iterator();
            System.out.println("Author list with a cursor: ");
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }

             // find a list of documents and use a List object instead of an iterator
            List<Document> authorlist = authorCollection.find(gte("no_of_blogs", 34)).into(new ArrayList<>());
            System.out.println("Author list with an ArrayList:");
            for (Document student : authorlist) {
                System.out.println(student.toJson());
            }

            // find a list of documents and print using a consumer
            System.out.println("Author list using a Consumer:");
            Consumer<Document> printConsumer = document -> System.out.println(document.toJson());
            authorCollection.find(gte("no_of_blogs", 34)).forEach(printConsumer);

            // find a list of documents with sort, skip, limit and projection
            List<Document> author = authorCollection.find(and(eq("no_of_blogs", 23), lte("no_of_blogs", 45)))
                                                  .projection(fields(excludeId(), include( "no_of_blogs","name","post")))
                                                  .sort(descending("no_of_blogs"))
                                                  .skip(3)
                                                  .limit(2)
                                                  .into(new ArrayList<>());

            System.out.println("Author sorted, skipped, limited and projected: ");
            for (Document student : author) {
                System.out.println(student.toJson());
            }
        }
    }
}
