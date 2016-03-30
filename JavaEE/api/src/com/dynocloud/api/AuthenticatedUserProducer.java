//package com.dynocloud.api;
//
//import javax.enterprise.context.RequestScoped;
//import javax.enterprise.event.Observes;
//import javax.enterprise.inject.Produces;
//
//@RequestScoped
//public class AuthenticatedUserProducer {
//
//    @Produces
//    @RequestScoped
//    @AuthenticatedUser
//    private User authenticatedUser;
//
//    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String UserID) {
//    	System.out.println("handleAuthenticationEvent");
//        this.authenticatedUser = findUserID(UserID);
//    }
//
//    private User findUserID(String UserID) {
//       User currentUser = new User();
//       currentUser.setUserID("001");
//       
//       System.out.println("findUserID");
//       
//       return currentUser;
//    }
//}