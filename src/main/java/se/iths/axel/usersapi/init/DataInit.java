package se.iths.axel.usersapi.init;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.iths.axel.usersapi.model.AppUser;
import se.iths.axel.usersapi.repository.AppUserRepository;

@Component
public class DataInit {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder encoder;

    public DataInit(AppUserRepository appUserRepository, PasswordEncoder encoder) {
        this.appUserRepository = appUserRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {
        AppUser appUser = new AppUser();

        appUser.setUsername("axel@gmail.com");
        appUser.setPassword("password");
        appUser.setRole("ADMIN");

        String encoded = encoder.encode(appUser.getPassword());

        appUser.setPassword(encoded);

        appUserRepository.save(appUser);
        IO.println("Init successful");
    }
}
