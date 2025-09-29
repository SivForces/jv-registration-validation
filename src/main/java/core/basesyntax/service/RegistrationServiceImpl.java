package core.basesyntax.service;

import core.basesyntax.Invalid;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

import static core.basesyntax.db.Storage.people;

public class RegistrationServiceImpl implements RegistrationService {

    public RegistrationServiceImpl(StorageDaoImpl dao) {

    }

    @Override
    public User register(User user) {
        validateUser(user);
        if (!containsLogin(user)) {
            throw new Invalid("This login is already used by other user");
        }
        if (!loginLength(user)) {
            throw new Invalid("Login should be more than 6 characters");
        }
        if (!passwordLength(user)) {
            throw new Invalid("Password should be more than 6 characters");
        }
        if (!ageValidation(user)) {
            throw new Invalid("The allowed age is more than 18");
        }
        people.add(user);
        return user;
    }

    public void validateUser(User user) {
        if (user == null) {
            throw new Invalid("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new Invalid("Login cannot be null");
        }
        if (user.getPassword() == null) {
            throw new Invalid("Password cannot be null");
        }
        if (user.getAge() == null) {
            throw new Invalid("Age cannot be null");
        }
    }

    public boolean containsLogin(User user) {
        return !people.contains(user.getLogin());
    }

    public boolean loginLength(User user) {
        return user.getLogin().length() >= 7;
    }

    public boolean passwordLength(User user) {
        return user.getPassword().length() >= 7;
    }

    public boolean ageValidation(User user) {
        return user.getAge() >= 18;
    }
}
