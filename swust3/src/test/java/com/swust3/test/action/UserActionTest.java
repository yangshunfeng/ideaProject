package com.swust3.test.action;

import com.swust3.test.JunitBaseActionTest;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class UserActionTest extends JunitBaseActionTest {
    public Map<String, String> res = new HashMap<String, String>();

    @Test
    public void selectByUid() throws Exception {
        res.put("uid", "4");
        MockHttpServletResponse response = request("/user/selectByUid", res);
        System.out.println(response.getContentAsString());
    }

    @Test
    public void selectByCondition() throws Exception {
        // res.put("uid", "1");
        MockHttpServletResponse response = request("/user/selectByCondition",
                res);
        System.out.println(response.getContentAsString());
    }

    @Test
    public void login() throws Exception {
        res.put("username", "yang");
        res.put("password", "456");
        MockHttpServletResponse response = request("/user/login", res);
        System.out.println(response.getContentAsString());
    }
}
