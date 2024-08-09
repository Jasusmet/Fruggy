package com.eoi.Fruggy.controladores;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

@Controller
public class LanguageCtrl {

    @GetMapping("/language/{lang}")
    public RedirectView changeLanguage(@PathVariable String lang, HttpServletRequest request) {
        Locale locale = new Locale(lang);
        LocaleContextHolder.setLocale(locale);
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        return new RedirectView("/", true);
    }
}
