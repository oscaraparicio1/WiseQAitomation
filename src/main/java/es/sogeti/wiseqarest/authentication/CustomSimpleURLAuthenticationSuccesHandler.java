package es.sogeti.wiseqarest.authentication;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Besides the AuthenticationSuccessHandler interface, Spring also provides a sensible default
 * for this strategy component – the AbstractAuthenticationTargetUrlRequestHandler and a simple 
 * implementation – the SimpleUrlAuthenticationSuccessHandler. Typically these implementations 
 * will determine the URL after login and perform a redirect to that URL. While somewhat flexible, 
 * the mechanism to determine this target URL does not allow the determination to be done 
 * programmatically – so we’re going to implement the interface and provide a custom 
 * implementation of the success handler. This implementation is going to determine the URL to
 * redirect the user to after login based on the role of the user
 * 
 * @author angemart
 */
public class CustomSimpleURLAuthenticationSuccesHandler implements AuthenticationSuccessHandler {
	protected Log logger = LogFactory.getLog(this.getClass());
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
 
    protected void handle(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
 
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
 
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
 
    /** 
     * Builds the target URL according to the logic defined in the main class
     * 
     * @return mainBase for a common user, administration for admins and error
     * for everyone else
     * 
     */
    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(Role.ADMIN.toString())) {
                isAdmin = true;
                break;
            } else if (grantedAuthority.getAuthority().equals(Role.USER.toString())) {
                isUser = true;
                break;
            }
        }
 
        if (isAdmin) {
            return "/admin";
        } else if (isUser) {
            return "/main";
        } else {
            throw new IllegalStateException();
        }
    }
 
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
