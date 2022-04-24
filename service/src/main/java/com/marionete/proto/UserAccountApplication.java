package com.marionete.proto;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.proto.server.LoginServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;

@SpringBootApplication
@EnableFeignClients
public class UserAccountApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(UserAccountApplication.class, args);
        //Uncomment below code to run the service on local machine
//        startLocalService();
    }

    private static void startLocalService() throws InterruptedException, IOException {
        UserInfoMock.start();
        AccountInfoMock.start();
        LoginServer server = new LoginServer(8980);
        server.start();
        server.blockUntilShutdown();
    }
}