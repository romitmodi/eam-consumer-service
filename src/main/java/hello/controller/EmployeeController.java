package hello.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import hello.dto.ERoleEmployee;
import hello.dto.Response;
import hello.entity.AccessDetails;
import hello.entity.EmployeeRole;
import hello.repository.AccessDetailsRepository;
import hello.repository.EmployeeRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("startSub")
public class EmployeeController {

    @Autowired
    AccessDetailsRepository accessDetailsRepository;

    @Autowired
    EmployeeRoleRepository employeeRoleRepository;
    private WebClient webClient;


    @GetMapping()
    public void saveEmployee() throws TimeoutException {
        String projectId = "lloyds-hack-grp-17";
        String subscriptionId = "ticketCreateUpdate-sub";
        this.subscribeAsyncExample(projectId, subscriptionId);
    }

    public void subscribeAsyncExample(String projectId, String subscriptionId) throws TimeoutException {
        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(projectId, subscriptionId);

        MessageReceiver receiver =
                (PubsubMessage message, AckReplyConsumer consumer) -> {
                    System.out.println("Id: " + message.getMessageId());
                    System.out.println("Data: " + message.getData().toStringUtf8());

                    final ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> mapFromString = new HashMap<>();
                    try {
                        mapFromString = mapper.readValue(message.getData().toStringUtf8(), new TypeReference<Map<String, Object>>() {
                        });
                    } catch (IOException e) {
                        System.out.println("Exception launched while trying to parse String to Map.");
                    }
                    System.out.println(mapFromString.get("role"));
                    Optional<EmployeeRole> employeeRole = employeeRoleRepository.findByName(ERoleEmployee.findByName(mapFromString.get("role").toString()));
                    List<AccessDetails> accessDetails  = accessDetailsRepository.findByRoleId(employeeRole.get().getId());


                    WebClient webClient = WebClient.create("https://team-vikings.atlassian.net");

                    Mono<Response> response = webClient.post()
                            .uri("/rest/api/3/issue")
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Basic cm9taXQubW9kaUBvdXRsb29rLmNvbTpBVEFUVDN4RmZHRjBuT1hpbkZqUWI0a016OTk2Y3R5Z2YyaFdrYzRROC1wdHc2NnJhS0FxSDVsSEtubFNUQURjNHBjZG5XTFM0VWlReF9ZWnF3TEx0OEhDcExFMGxLRThCSHliS2g3MG1IbVI2WUxHOE03b3BFZ081N3lEZDg2ZHlQZ1BRSXRTZG9lMHlTTEZvY29aTm5HSWNONzBORUdfM2xrQ01Qd3NSQ0VnQmRLM2lCZU9GZ0k9OUJERkYxQzk=")
                            .header("Cookie", "atlassian.xsrf.token=7334e1faa0e5c14014df77e415114c752c63f6ce_lin")
                            .body(Mono.just(accessDetails.get(0).getJsonStructure()), Object.class)
                            .retrieve()
                            .bodyToMono(Response.class);

                    response.subscribe(System.out::println);
                    consumer.ack();
                };

        Subscriber subscriber = null;
        subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        // Start the subscriber.
        subscriber.startAsync().awaitRunning();
        System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
        // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
        subscriber.awaitTerminated(3000000, TimeUnit.HOURS);
    }
}
