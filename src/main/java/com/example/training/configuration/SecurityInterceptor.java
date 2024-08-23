package com.example.training.configuration;

import com.example.training.service.AccidentsService;
import com.example.training.service.PoliciesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final ObjectProvider<PoliciesService> policiesServiceProvider;
    private final ObjectProvider<AccidentsService> accidentsServiceProvider;

    @Autowired
    public SecurityInterceptor(ObjectProvider<PoliciesService> policiesServiceProvider, ObjectProvider<AccidentsService> accidentsServiceProvider) {
        this.policiesServiceProvider = policiesServiceProvider;
        this.accidentsServiceProvider = accidentsServiceProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authenticatedUserId = SecurityContextHolder.getContext().getAuthentication().getName();

        if (handler instanceof HandlerMethod) {
            Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            String policyNumber = pathVariables.get("policyNumber");
            String accidentId = pathVariables.get("accidentId");

            PoliciesService policiesService = policiesServiceProvider.getIfAvailable();
            AccidentsService accidentsService = accidentsServiceProvider.getIfAvailable();

            if (policyNumber != null && !policiesService.isPolicyOwnedByUser(policyNumber, authenticatedUserId)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para acceder a esta póliza.");
                return false;
            }
            if (accidentId != null && policyNumber != null) {
                if (!policiesService.isPolicyOwnedByUser(policyNumber, authenticatedUserId) ||
                        !accidentsService.isAccidentOwnedByPolicy(accidentId, policyNumber)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para acceder a este siniestro.");
                    return false;
                }
            }
        }

        return true;
    }
}
