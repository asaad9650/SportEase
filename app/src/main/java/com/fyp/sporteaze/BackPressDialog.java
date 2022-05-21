package com.fyp.sporteaze;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class BackPressDialog {

    public void logout(Context context){

        AlertDialog alertbox = new AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Do you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(context , LoginActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();

                        // finish();
                        //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                })
                .show();

    }




    public void exit_application(Context context){
        AlertDialog alertbox = new AlertDialog.Builder(context)
                .setTitle("Exit")
                .setMessage("Do you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        System.exit(0);

//                        Intent intent = new Intent(context , LoginActivity.class);
//                        context.startActivity(intent);
//                        ((Activity)context).finishAndRemoveTask();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                })
                .show();
    }
}
