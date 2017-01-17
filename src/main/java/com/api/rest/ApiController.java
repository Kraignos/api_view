package com.api.rest;

import com.api.annotation.ApiView;
import com.api.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @ApiView
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object index() {
        User user = new User();
        user.setUsername("antoine");
        user.setValid(true);
        user.setPasswordHash("jfmds43T39T5UTFDS");
        return user;
    }
}
