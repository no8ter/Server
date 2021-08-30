package ru.rgrtu.pahomova.Filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rs = (HttpServletResponse) response;
        String ServPath = rq.getServletPath();
        Logger logger = LoggerFactory.getLogger(AuthorizedFilter.class);

        switch (ServPath) {
            case ("/login"):
            case ("/register"):
                break;
            default:
                LoggerFactory.getLogger(AuthorizedFilter.class).debug("Trying to get authorization check");
                boolean auth = (boolean) (rq.getSession().getAttribute("isAuthorized") == null ? false : rq.getSession().getAttribute("isAuthorized"));
                if (!auth) {
                    logger.debug("Check failed. Redirecting to login");
                    rs.sendRedirect("/login");
                    return;
                }
                logger.debug("Authorization check success");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
