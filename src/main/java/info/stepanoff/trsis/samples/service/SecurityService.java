package info.stepanoff.trsis.samples.service;


public interface SecurityService {

    String findLoggedTelephone();

    void autoLogin(String username, String password);
}
