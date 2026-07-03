package com.conectacampo.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode == null) {
            statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        }

        if (statusCode == null) {
            statusCode = 500;
        }

        String errorMessage = getErrorMessage(statusCode);
        model.addAttribute("error", errorMessage);
        model.addAttribute("status", statusCode);
        model.addAttribute("path", request.getRequestURI());

        System.out.println("========= ERROR DETECTADO =========");
        System.out.println("Status Code: " + statusCode);
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("Message: " + errorMessage);
        System.out.println("===================================");

        switch (statusCode) {
            case 400:
                return "error/400";
            case 401:
                return "error/401";
            case 403:
                return "error/403";
            case 404:
                return "error/404";
            case 405:
                return "error/405";
            case 500:
                return "error/500";
            case 503:
                return "error/503";
            default:
                return "error/500";
        }
    }

    private String getErrorMessage(Integer statusCode) {
        switch (statusCode) {
            case 400:
                return "La solicitud no es válida. Verifica los datos ingresados.";
            case 401:
                return "Debes iniciar sesión para acceder a esta página.";
            case 403:
                return "No tienes permisos para acceder a esta página.";
            case 404:
                return "La página que buscas no existe.";
            case 405:
                return "El método HTTP no está permitido para esta ruta.";
            case 500:
                return "Error interno del servidor. Intenta más tarde.";
            case 503:
                return "El servicio no está disponible. Intenta más tarde.";
            default:
                return "Ha ocurrido un error inesperado.";
        }
    }
}