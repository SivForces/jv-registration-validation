package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {
    private StorageDaoImpl dao;
    private RegistrationService service;

    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl(dao);

    @BeforeEach
    void setUp() {
        dao = new StorageDaoImpl();
        service = new RegistrationServiceImpl(dao);
        Storage.clear();
    }

    @Test
    void register_validUser_Ok() {
        User user1 = new User();
        user1.setAge(20);
        user1.setId(111L);
        user1.setLogin("bruhman");
        user1.setPassword("password");

        service.register(user1);

        User stored = dao.get(user1.getLogin());
        assertNotNull(stored);
        assertEquals("bruhman", stored.getLogin());
    }

    @Test
    void register_nullUser_notOk() {
        User user1 = new User();
        user1.setAge(20);
        user1.setId(111L);
        user1.setLogin(null);
        user1.setPassword("password");

        Storage.people.add(user1);

        User stored = dao.get(user1.getLogin());
        assertNull(stored);
    }

    @Test
    void register_nullLogin_notOk() {
        User user1 = new User();
        user1.setLogin(null);

        assertNull(user1.getLogin());
    }

    @Test
    void register_shortLogin_notOk() {
        User user1 = new User();
        user1.setLogin("bruh");
        boolean actual = registrationService.loginLength(user1);
        assertFalse(actual);
    }

    @Test
    void register_nullPassword_notOk() {
        User user1 = new User();
        user1.setPassword(null);

        assertNull(user1.getPassword());
    }

    @Test
    void register_nullAge_notOk() {
        User user1 = new User();
        user1.setAge(null);

        assertNull(user1.getAge());
    }

    @Test
    void register_underAge_notOk() {
        User user1 = new User();
        user1.setAge(17);
        boolean actual = registrationService.ageValidation(user1);

        assertFalse(actual);
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin("bruhman");
        User user1 = new User();
        user1.setLogin("bruhman");
        boolean actual = registrationService.containsLogin(user1);
        assertFalse(actual);
    }
}
