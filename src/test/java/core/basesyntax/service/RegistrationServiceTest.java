package core.basesyntax.service;

import core.basesyntax.Invalid;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {
    static RegistrationService service;
    static StorageDaoImpl dao;

    @BeforeEach
    static void beforeEach() {
        dao = new StorageDaoImpl();
        service = new RegistrationServiceImpl(dao);
    }

    @Test
    void register_validUser_Ok() {
        User user1 = new User();
        user1.setAge(20);
        user1.setId(111L);
        user1.setLogin("bruh");
        user1.setPassword("password");

        service.register(user1);

        User stored = dao.get(user1.getLogin());
        assertNotNull(stored);
        assertEquals("john123", stored.getLogin());
    }

    @Test
    void register_nullUser_notOk() {
        User user1 = new User();
        user1.setAge(20);
        user1.setId(111L);
        user1.setLogin(null);
        user1.setPassword("password");

        service.register(user1);

        User stored = dao.get(user1.getLogin());
        assertNull(stored);
        assertThrows("Login can't be null");
    }
}