package messagesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// TODO
// COUNTER - DOUBTFUL

public class AddressService {
    private final List<Address> accountServices = new ArrayList<>();
    private final AtomicInteger accountServiceCounter = new AtomicInteger();
    private final List<Address> databaseServices = new ArrayList<>();
    private final AtomicInteger databaseServiceCounter = new AtomicInteger();

    public Address getAccountService() {
        final int index = accountServiceCounter.incrementAndGet() % accountServices.size();
        return accountServices.get(index);
    }

    public void addAccountService(Abonent accountService) {
        accountServices.add(accountService.getAddress());
    }

    public Address getDatabaseService() {
        final int index = databaseServiceCounter.incrementAndGet() % databaseServices.size();
        return databaseServices.get(index);
    }

    public void addDatabaseService(Abonent databaseService) {
        databaseServices.add(databaseService.getAddress());
    }
}
