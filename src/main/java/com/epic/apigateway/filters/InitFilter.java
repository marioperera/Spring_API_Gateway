package com.epic.apigateway.filters;
import com.epic.apigateway.dao.ApplicationUser;
import com.epic.apigateway.repositories.ApplicationUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;



public class InitFilter implements Filter {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public InitFilter(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository =applicationUserRepository;
        this.bCryptPasswordEncoder =bCryptPasswordEncoder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(req.getRequestURL().toString().contains("/auth/sign_up")){
            try {
                String ret = getParamsFromPost(req);
                ApplicationUser user= objectMapper.readValue(ret,ApplicationUser.class);
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                String json = objectMapper.writeValueAsString(applicationUserRepository.save(user));
                res.getWriter().write(json);
                res.setStatus(200);
            } catch (IOException e) {
                e.printStackTrace();
                res.setStatus(400);
                res.getWriter().write(e.getMessage());
            }

        }
//        chain.doFilter(request, response);
    }

    private String getParamsFromPost(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = reader.readLine();
        }
        reader.close();
        String params = sb.toString();
        String[] _params = params.split("&");
        System.out.println(_params[0]);
        return _params[0];
    }
}
