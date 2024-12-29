package login;
// interfaccia usata per gestire i listener
public interface LoginListener {
    boolean onLogin(boolean success, String username);
}