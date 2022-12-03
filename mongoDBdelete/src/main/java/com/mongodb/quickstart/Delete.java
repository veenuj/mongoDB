package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;

public class Delete {

    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(connectionString))  {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("blogging");
            MongoCollection<Document> authorCollection = sampleTrainingDB.getCollection("author");

           // delete one document
            Bson filter = eq("name", "Sathya");
            DeleteResult result = authorCollection.deleteOne(filter);
            System.out.println(result);

            // findOneAndDelete operation
            filter = eq("name", "Lavi");
            Document doc = authorCollection.findOneAndDelete(filter);
            System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));

           // delete many documents
            filter = gte("no_of_blogs", 45);
            result = authorCollection.deleteMany(filter);
            System.out.println(result);

           // delete the entire collection and its metadata (indexes, chunk metadata, etc).
            authorCollection.drop();
        }
    }
}
