package com.example.springfirstproject.objects.noticias.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springfirstproject.common.config.anotaciones.module.Requieremodule;
import com.example.springfirstproject.objects.noticias.model.Noticias;
import com.example.springfirstproject.objects.preferencias.service.PreferenciasService;
import com.example.springfirstproject.user.models.UserAuth;
import com.example.springfirstproject.user.service.UserAuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@Requieremodule({ 2L })
public class PrincipalController {

    private final UserAuthService userAuthService;

    private final PreferenciasService preferenciasService;

    @GetMapping("/principal")
    public String showPrincipal(Model model, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("username", auth.getName());

        Optional<UserAuth> userAuth = userAuthService.findByUsername(auth.getName());
        if (!userAuth.isPresent()) {
            return null;
        }
        model.addAttribute("userAuth", userAuth.get());

        Set<Long> lista = new HashSet<>();
        lista.add(1L);
        model.addAttribute("modulesNecesarios", lista);

        LinkedHashSet<Noticias> setNoticias = new LinkedHashSet<>();

        Noticias salida;
        Noticias fechaSalida;
        Noticias prueba;

        if (preferenciasService.obtenerPreferencias(userAuth.get().getId()).getIdioma().equals("es")) {
            salida = new Noticias(1L, "The Dawn of Man: disponible en...",
                    "Ya se puede comprar the dawn of man en las plataformas oficiales...", "13/05/2025", "imagen");
            fechaSalida = new Noticias(1L, "Fecha de salida: The Dawn of Man",
                    "La fecha de salida ya está confirmada para el 13/05/2025. Ya podéis reservar en...", "10/04/2025",
                    "imagen");
            prueba = new Noticias(1L, "Esto es una prueba que...",
                    "Primera prueba del sistema de noticias y si o is tiene que tener, al menos, dos lineas",
                    "03/04/2025", "imagen");
        } else {
            salida = new Noticias(1L, "The Dawn of Man: available...",
                    "The Dawn of Man is now available for purchase on official platforms..", "13/05/2025", "imagen");
            fechaSalida = new Noticias(1L, "Departure date: The Dawn of Man",
                    "The departure date is now confirmed for May 13, 2025. You can now book at...", "10/04/2025",
                    "imagen");
            prueba = new Noticias(1L, "This is a test that...",
                    "First test of the news system and it has to have at least two lines.", "03/04/2025", "imagen");
        }

        setNoticias.add(salida);
        setNoticias.add(fechaSalida);
        setNoticias.add(prueba);

        model.addAttribute("noticias", setNoticias);
        return "principal";
    }
}
