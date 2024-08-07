package com.ltc.reboot.hackathon.eam.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.ltc.reboot.hackathon.eam.dto.ERoleEmployee;
import com.ltc.reboot.hackathon.eam.dto.Response;
import com.ltc.reboot.hackathon.eam.entity.AccessDetails;
import com.ltc.reboot.hackathon.eam.entity.EmployeeRole;
import com.ltc.reboot.hackathon.eam.entity.RequestTable;
import com.ltc.reboot.hackathon.eam.repository.AccessDetailsRepository;
import com.ltc.reboot.hackathon.eam.repository.EmployeeRoleRepository;
import com.ltc.reboot.hackathon.eam.repository.RequestTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@RestController
@RequestMapping("startSub")
public class EmployeeController {

    @Autowired
    AccessDetailsRepository accessDetailsRepository;

    @Autowired
    EmployeeRoleRepository employeeRoleRepository;
    @Autowired
    RequestTableRepository requestTableRepository;
    private WebClient webClient;

    @GetMapping()
    public void saveEmployee() throws TimeoutException {
        String projectId = "lloyds-hack-grp-17";
        String subscriptionId = "ticketCreateUpdate-sub";
        this.subscribeAsyncExample(projectId, subscriptionId);
    }

    @GetMapping()
    public ResponseEntity<String> updateEmployee(@RequestParam List<String> val) {
        val.forEach(value -> {
            requestTableRepository.updateStudentName(value, "done");
        });

//        call jira here


        return ResponseEntity.ok("Update successfully!");
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
                    List<AccessDetails> accessDetails = accessDetailsRepository.findByRoleId(employeeRole.get().getId());


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


                    response.subscribe();

// doubt in this save
                    RequestTable requestTable = RequestTable.builder().JiraId("abdds").status("CREATED").build();
                    requestTableRepository.save(requestTable);
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
