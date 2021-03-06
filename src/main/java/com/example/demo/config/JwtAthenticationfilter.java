package com.example.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Controller.Home;
import com.example.demo.Exception.UserServiceException;
import com.example.demo.Service.CustomUserDetailsService;
import com.example.demo.jwthelper.JwtUtil;


@Component
public class JwtAthenticationfilter extends  OncePerRequestFilter {
	Logger logger = LoggerFactory.getLogger(JwtAthenticationfilter.class);
	
	 @Autowired
	    private CustomUserDetailsService customUserDetailsService;

	    @Autowired
	    private JwtUtil jwtUtil;

	   
		

	@Override
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws   ServletException, IOException  {
		// TODO Auto-generated method stub
		//get jwt
        //Bearer
        //validate
		
		logger.info("Readuser from auth");
        String requestTokenHeader = request.getHeader("Authorization");
        String username=null;
        String jwtToken=null;

        //null and format
        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer"))
        {
            jwtToken=requestTokenHeader.substring(7);

            try{
             
                username = this.jwtUtil.getUsernameFromToken(jwtToken);

               
            }catch (Exception e)
            {
                e.printStackTrace();
               // throw new ServletException("Tolken in valis");
            }

            
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {

                UserDetails userDetails = ((com.example.demo.Service.CustomUserDetailsService) this.customUserDetailsService).loadUserByUsername(username);
                //security


                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            }else
            	
            {
            	logger.info("Token is not validate ");
                System.out.println("Token is not validated..");
                
               throw new UserServiceException("Token is not validated");
            }
            }



        
        


         filterChain.doFilter(request,response);

		
	}

}
