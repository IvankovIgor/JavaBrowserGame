package messagesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// TODO
// COUNTER - DOUBTFUL

public class AddressService {
    private final List<Address> accountServiceAddresses = new ArrayList<>();
    private final AtomicInteger accountServiceCounter = new AtomicInteger();
    private final List<Address> databaseServiceAddresses = new ArrayList<>();
    private final AtomicInteger databaseServiceCounter = new AtomicInteger();
    private final List<Address> frontendServiceAddresses = new ArrayList<>();
    private final AtomicInteger frontendServiceCounter = new AtomicInteger();

    public Address getAccountServiceAddress() {
        final int index = accountServiceCounter.incrementAndGet() % accountServiceAddresses.size();
        return accountServiceAddresses.get(index);
    }

    public void addAccountService(Abonent accountService) {
        accountServiceAddresses.add(accountService.getAddress());
    }

    public Address getDatabaseServiceAddress() {
        final int index = databaseServiceCounter.incrementAndGet() % databaseServiceAddresses.size();
        return databaseServiceAddresses.get(index);
    }

    public void addDatabaseService(Abonent databaseService) {
        databaseServiceAddresses.add(databaseService.getAddress());
    }

    public Address getFrontendServiceAddress() {
        final int index = frontendServiceCounter.incrementAndGet() % frontendServiceAddresses.size();
        return frontendServiceAddresses.get(index);
    }

    public void addFrontendService(Abonent frontendService) {
        frontendServiceAddresses.add(frontendService.getAddress());
    }
}
