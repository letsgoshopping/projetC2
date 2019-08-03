package com.project.lgs.Database;

import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteDeleteResult;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;


public class MongoOperations {

        private RemoteMongoClient mongoClient;
        private String databaseName;


        MongoOperations(String databaseName,  RemoteMongoClient mongoClient){

            this.databaseName = databaseName;
            this.mongoClient = mongoClient;
        }


        public void insertDocument (String collection, HashMap<String,String> values)throws Exception{

                RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

                Document newDoc = new Document();

                for (Entry<String, String> entry : values.entrySet()) {
                    String key = entry.getKey();
                    String val = entry.getValue();

                    newDoc.append(key, val);
                }

            mongoCollection.insertOne(newDoc).continueWithTask(new Continuation<StitchUser, Task<StitchUser>>() {
                @Override
                public Task<StitchUser> then(Task<StitchUser> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw new Exception("Insertion failed");
                    }

                    return task;
                }
            });
        }

        public void updateDoument (String collection, HashMap<String,String> values, String id)throws Exception{

            String res;

            try {

                this.deleteDocument(collection, id);
                this.insertDocument(collection, values);

            } catch (Exception e){

                throw new Exception("Update failed");

            }

        }

        public void deleteDocument (String collection, String id) throws Exception{

            String res;

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document filterDoc = new Document().append("id", id);

            mongoCollection.deleteOne(filterDoc).continueWithTask(new Continuation<StitchUser, Task<StitchUser>>() {
                @Override
                public Task<StitchUser> then(Task<StitchUser> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw new Exception("Deletion failed");
                    }
                    return task;
                }
            });;
        }

        public ArrayList<Document> findDocument (String collection, HashMap<String,String> values, HashMap<String,Integer> sorting) {

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document searchDoc = new Document();

            for (Entry<String, String> entry : values.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                searchDoc.append(key, val);
            }

            Document sortingDoc = new Document();

            if (sorting != null) {

                for (Entry<String, Integer> entry : sorting.entrySet()) {
                    String key = entry.getKey();
                    int val = entry.getValue();

                    sortingDoc.append(key, val);
                }
            }

            RemoteFindIterable findResults = mongoCollection
                    .find(searchDoc);
                  //  .sort(sortingDoc);

            ArrayList<Document> res = new ArrayList<Document>();
            Log.d("maya array size",Integer.toString(res.size()));
            findResults.into(res);

            return res;

        }
}
