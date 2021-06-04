package br.com.zupacademy.armando.propostamicroservice.proposals.utils;

import br.com.zupacademy.armando.propostamicroservice.proposals.enums.ProposalStatus;

import java.util.HashMap;
import java.util.Map;

public class ProposalStatusMapper {

    public static Map<String, ProposalStatus> statusMap;
    static {
        statusMap = new HashMap<>();
        statusMap.put("COM_RESTRICAO", ProposalStatus.NAO_ELEGIVEL);
        statusMap.put("SEM_RESTRICAO", ProposalStatus.ELEGIVEL);
    }

}
