package com.ltc.reboot.hackathon.eam.service;

import com.ltc.reboot.hackathon.eam.repository.AccessDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelTicketPushService {

    @Autowired
    private AccessDetailsRepository accessDetailsRepository;

    public void call() {
//        System.out.println(accessDetailsRepository.findByRoleId(1));
    }
}
