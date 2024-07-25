package hello.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class AccessDetailsDTO {
    private Integer accessId;
    private String channel;
    private JsonNode jsonStructure;
    private Integer dependentAccessId;
}
