package de.uniba.dsg.carrental.rentservice.model.dto.architectureextraction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogDto {
    private String clientId;
    private String serverId;
    private String methodId;
    private String responseCode;
    private Long timestamp;
    private Long responseTime;
}
