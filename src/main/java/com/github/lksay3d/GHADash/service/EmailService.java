package com.github.lksay3d.GHADash.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class EmailService {

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            String[] cmd = {
                "python3",
                "send_email.py",
                to,
                subject,
                text
            };
            Process p = Runtime.getRuntime().exec(cmd);

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}