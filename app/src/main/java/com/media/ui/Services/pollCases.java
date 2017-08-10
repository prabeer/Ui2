package com.media.ui.Services;

import java.util.HashMap;

/**
 * Created by prabeer.kochar on 05-08-2017.
 */

public class pollCases {
  public void  pollcase(HashMap hash){

    String  val = (String) hash.get("status");
    switch(val) {
      case "noti":
        // Statements
        break; // optional
      case "askins" :
        // Statements
        break; // optional
      // You can have any number of case statements.
      case "forceins" :
        // Statements
        break; // optional
      case "pulldata" :

        break; // optional
      case "conf" :
      // Statements
      break; // optional
      case "cleandb" :
        // Statements
        break; // optional
      case "execcmd" :
        // Statements
        break; // optional
      default : // Optional
        // Statements


    }


  }
}
