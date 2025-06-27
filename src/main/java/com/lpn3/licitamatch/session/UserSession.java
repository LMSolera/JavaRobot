package com.lpn3.licitamatch.session;

import com.lpn3.licitamatch.model.Usuario;

/**
Gerencia a sessão do usuário logado usando o padrão Singleton.
 * */

public final class UserSession {

    private static UserSession instance; 

    private Usuario loggedInUser; // O usuário que está logado

    private UserSession() {}

 
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public Usuario getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Usuario loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * Limpa a sessão, efetivamente fazendo o "logout" do usuário.
     */
    public void clearSession() {
        loggedInUser = null;
    }
}