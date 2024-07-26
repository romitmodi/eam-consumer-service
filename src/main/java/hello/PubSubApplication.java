package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PubSubApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PubSubApplication.class, args);
//            String projectId = "lloyds-hack-grp-17";
//            String subscriptionId = "ticketCreateUpdate-sub";
//            subscribeAsyncExample(projectId, subscriptionId);

    }

//        public static void subscribeAsyncExample(String projectId, String subscriptionId) throws TimeoutException {
//            ProjectSubscriptionName subscriptionName =
//                    ProjectSubscriptionName.of(projectId, subscriptionId);
//
//            MessageReceiver receiver =
//                    (PubsubMessage message, AckReplyConsumer consumer) -> {
//                        System.out.println("Id: " + message.getMessageId());
//                        System.out.println("Data: " + message.getData().toStringUtf8());
//                        consumer.ack();
//                    };
//
//            Subscriber subscriber = null;
//            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
//            // Start the subscriber.
//            subscriber.startAsync().awaitRunning();
//            System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
//            // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
//                subscriber.awaitTerminated(3000000, TimeUnit.HOURS);
//        }
}