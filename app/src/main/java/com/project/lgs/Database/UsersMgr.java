package com.project.lgs.Database;

import com.google.android.gms.tasks.Task;
import com.mongodb.BasicDBObject;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;
import com.project.lgs.CategoryClasses.Category;
import com.project.lgs.UsersClasses.User;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UsersMgr {

    MongoOperations operations;
    String collection  = "User";


    public UsersMgr(String databaseName, RemoteMongoClient mongoClient){

        this.operations = new MongoOperations(databaseName, mongoClient);
    }


    public String insertDocument (HashMap<String,String> values){

        String res = "1";
        try{
            operations.insertDocument(collection,values);
        }catch (Exception e){
            res = "-1";
        }

        return res;
    }

    public String insertDocumentWait (HashMap<String,String> values){

        String res = "1";
        try{
            operations.insertDocumentWait(collection,values);
        }catch (Exception e){
            res = "-1";
        }

        return res;
    }


    public String updateDocument (HashMap<String,String> values, HashMap<String, ObjectId> filter){

        String res = "1";

        try {
            operations.updateDoument(collection, values, filter);
        }catch(Exception e){
            res = "-1";
        }

        return res;
    }

    public String deleteDocument (ObjectId id){

        String res = "1";
        try{
            operations.deleteDocument(collection,id);
        }catch(Exception e){
            res = "-1";
        }

        return res;
    }

    public ArrayList<User> findDocument (HashMap<String,String> values, HashMap<String,Integer> sorting, int limit){

        List <Document> res =  operations.findDocument(collection,values,sorting,limit);
        ArrayList <User> user = new ArrayList<User>();

        for (Document doc: res) {
            User pro = new User((String)doc.get("_id").toString(),
                    (String)doc.get("Email"));

            user.add(pro);

        }

        return user;

    }

    public ArrayList<User> findDocumentById (HashMap<String,ObjectId> values, HashMap<String,Integer> sorting, int limit){

        List <Document> res =  operations.findDocumentById(collection,values,sorting,limit);
        ArrayList <User> product = new ArrayList<User>();

        for (Document doc: res) {
            User pro = new User((String)doc.get("_id").toString(),
                    (String)doc.get("Email"));

            product.add(pro);

        }

        return product;

    }

}
