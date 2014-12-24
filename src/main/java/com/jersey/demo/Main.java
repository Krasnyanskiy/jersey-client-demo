package com.jersey.demo;

import com.jaspersoft.jasperserver.dto.authority.ClientUser;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Alex Krasnyanskiy
 */
public class Main {
    public static void main(String[] args) {

        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic("superuser", "superuser");
        Client client = ClientBuilder
                .newClient()
                .register(authenticationFeature)
                        //.register(new JettisonFeature())
                        //.register(new JacksonFeature())
                        //.register(JacksonConfigurator.class)
                .register(JacksonFeature.class)
                .register(CustomRepresentationTypeProvider.class);


        WebTarget target = client.target("http://localhost:4444/jasperserver-pro/");
        ClientUser entity;
        Response resp;

        resp = target.path("/rest_v2/users/superuser")
                .request()
                .accept(APPLICATION_JSON) // works for APPLICATION_XML as well
                .get();

        entity = resp.readEntity(ClientUser.class);
        System.out.println(entity);

    }
}