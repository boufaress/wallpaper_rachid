package com.rba.animewp.ads;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

public class Review {
    public static ReviewInfo reviewinfo ;
    public static ReviewManager manager ;
    public static void ReviewCreateur(Activity activity){
        manager = ReviewManagerFactory.create(activity);
        Task<ReviewInfo> managerinfoTask = manager.requestReviewFlow();
        managerinfoTask.addOnCompleteListener(task->
        {
            if (task.isSuccessful()) {
                reviewinfo = task.getResult();
            }
            else{
                Toast.makeText(activity, "Review faild to start", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void ShowReviewflow(Activity activity){
            if (reviewinfo != null){
              Task<Void>  flow = manager.launchReviewFlow(activity,reviewinfo);
              flow.addOnCompleteListener(task->
              {
                 // Toast.makeText(activity, "Rating is completed", Toast.LENGTH_SHORT).show();
              });
            }
    }


}
