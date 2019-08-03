package com.project.lgs.Database;


import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.project.lgs.R;


public class MongoConnect {

        public RemoteMongoClient connect (){

            StitchAppClient stitchAppClient =Stitch.getDefaultAppClient();

            stitchAppClient
                    .getAuth()
                    .loginWithCredential(new AnonymousCredential())
                    .continueWithTask(new Continuation<StitchUser, Task<StitchUser>>() {
                        @Override
                        public Task<StitchUser> then(Task<StitchUser> task) throws Exception {
                            if (task.isSuccessful()) {
                                Log.d("stitch", "logged in anonymously as user " + task.getResult());
                            } else {
                                Log.e("stitch", "failed to log in anonymously", task.getException());
                            }

                            return task;
                        }
                    });

            RemoteMongoClient  mongoClient= stitchAppClient.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

            return mongoClient;

        }

}
