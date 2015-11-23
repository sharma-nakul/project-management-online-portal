package edu.sjsu.cmpe275.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * Configuration class
 * Wrapper to make url case insensitive
 * @author Nakul Sharma
 */
@Configuration
@Component
public class CaseInsensitiveRequestFilter extends OncePerRequestFilter {

    /**
     * Get HttpServlete request and apply filters
     * @param request Http request
     * @param response Http response
     * @param filterChain Object of filter to be applied
     * @throws ServletException Any servlet exception thrown
     * @throws IOException Any IOException thrown
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new CaseInsensitiveHttpServletRequestWrapper(request), response);
    }

    /**
     * Static class to use the Http wrapper for setting all URLs as case insensitive
     */
    private static class CaseInsensitiveHttpServletRequestWrapper extends HttpServletRequestWrapper {

        /**
         * Map that is responsible for making parameters as case insensitive.
         */
        private final LinkedCaseInsensitiveMap<String[]> params = new LinkedCaseInsensitiveMap<>();

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request Http request
         * @throws IllegalArgumentException if the request is null
         */
        private CaseInsensitiveHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            params.putAll(request.getParameterMap());
        }

        /**
         * Method to get parameters from the URI
         * @param name
         * @return Parameters in lower case
         */
        @Override
        public String getParameter(String name) {
            String[] values = getParameterValues(name.toLowerCase());
            if (values == null || values.length == 0) {
                return null;
            }
            return values[0];
        }

        /**
         * Getter for parameter map
         * @return collections of parameter of type Map
         */
        @Override
        public Map<String, String[]> getParameterMap() {
            return Collections.unmodifiableMap(this.params);
        }

        /**
         * Getter for parameter names
         * @return Collection of parameter names in ENUM format
         */
        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(this.params.keySet());
        }

        /**
         * Getter of Parameter Name List
         * @param name Parameter name
         * @return String array of parameter name
         */
        @Override
        public String[] getParameterValues(String name) {
            return (String[])params.get(name.toLowerCase());
        }
    }
}