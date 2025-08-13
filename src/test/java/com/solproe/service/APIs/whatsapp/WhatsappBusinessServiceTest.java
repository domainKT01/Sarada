package com.solproe.service.APIs.whatsapp;

import com.solproe.business.gateway.WhatsappService;
import com.solproe.util.ValidateDate;
import org.junit.jupiter.api.Test;

class WhatsappBusinessServiceTest {


    @Test
    public void message() {
        WhatsappService whatsappService = new WhatsappBusinessService();
        whatsappService.setToken("EAARsQH7A6kIBPBPQ0ZBeqIhPLNsYGO7qB7zAouGGvWioXqDgM59KpB9mCPGyxgO6zdFS0bpxqapy4rZCgEkZCUHoGEz8MOfU6fbn2f3srfPTbWKbVO4FfqbBS8ag28MkYutpuVuOE1XxseJkxZBFAxkDNQGeK80jZCFEOHzc9FJ0HLZAZAZCpMzdkZCbVuyqKK3ZBtYAqtpZA64iujpoY8wZAwZDZD");
        whatsappService.setPhoneNumberId("105793722275227");
        whatsappService.sendMessage();
    }
}