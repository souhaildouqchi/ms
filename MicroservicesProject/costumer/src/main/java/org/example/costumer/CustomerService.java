package org.example.costumer;

import lombok.AllArgsConstructor;
import org.example.clients.fraud.FraudCheckResponse;
import org.example.clients.fraud.FraudClient;
import org.example.clients.notification.NotificationClient;
import org.example.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final NotificationClient notificationClient;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        // todo: store customer in db ( done )
        customerRepository.saveAndFlush(customer);
        // todo: check if email valid
        // todo: check if email not taken
        // todo: check if fraudster
     /*   FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{cutomer-id}",
                FraudCheckResponse.class,
                customer.getId()
        );*/
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        // todo: send notification
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),String.format("Hi %s, welcome", customer.getFirstName())
                )
        );

        // todo : make is async use kafka

    }
}
