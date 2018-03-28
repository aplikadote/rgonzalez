/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springapp.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.portlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author administrador
 */
public class HelloController extends AbstractController {

//    protected final Log logger = LogFactory.getLog(getClass());
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        logger.info("Returning hello view");
        return new ModelAndView("hello.jsp");
    }

}
