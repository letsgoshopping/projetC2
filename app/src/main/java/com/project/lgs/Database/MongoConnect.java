package com.project.lgs.Database;


import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;


public class MongoConnect {

        private StitchAppClient client;
        private RemoteMongoClient mongoClient;
        private RemoteMongoCollection mongoCollection;


        public RemoteMongoClient connect (){

            client = Stitch.getDefaultAppClient();
            mongoClient = client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");
            Task<StitchUser> stitchUserTask = client.getAuth().loginWithCredential(new AnonymousCredential());

            if (stitchUserTask.isSuccessful()) {
                return mongoClient;
            } else {
                return null;
            }
        }

}
