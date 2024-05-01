package Service;

import Entity.User;

public class SessionManager {
     private static SessionManager instance ;
     private User currentUser;
     private SessionManager() {}
     private SessionManager(User currentUser) {
         this.currentUser = currentUser;
     }
     public static SessionManager getInstance() {
         if(instance == null) instance=new SessionManager();
         return instance;
     }
     public  void startSession(User user){
         currentUser = user;
     }
     public  void endSession(){
         currentUser = null;
     }
     public boolean isLoggedIn(){
        return currentUser != null;
     }
}
