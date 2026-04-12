package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import todolist.dto.UsuarioData;
import todolist.service.UsuarioService;

import javax.servlet.http.HttpSession;
import java.util.List;


import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registered")
    public String listadoUsuarios(Model model, HttpSession session, RedirectAttributes flash) {
        Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");

        if (esAdmin == null || !esAdmin) {
            Long id = (Long) session.getAttribute("idUsuarioLogeado");

            // Añadimos el mensaje de error que viajará a la página de tareas
            flash.addFlashAttribute("error", "Acceso denegado: No tienes permisos de administrador.");

            return (id != null) ? "redirect:/usuarios/" + id + "/tareas" : "redirect:/login";
        }

        model.addAttribute("usuarios", usuarioService.findAll());
        return "listaUsuarios";
    }

    @GetMapping("/registered/{id}")
    public String detalleUsuario(@PathVariable Long id, Model model, HttpSession session) {
        Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");

        if (esAdmin == null || !esAdmin) {
            return "redirect:/login";
        }

        UsuarioData usuario = usuarioService.findById(id);
        if (usuario == null) {
            return "redirect:/registered";
        }

        model.addAttribute("usuarioDetalle", usuario);
        return "detalleUsuario";
    }

    @PostMapping("/usuarios/{id}/bloquear")
    public String bloquearUsuario(@PathVariable Long id, HttpSession session) {
        Boolean esAdmin = (Boolean) session.getAttribute("esAdmin");
        if (esAdmin == null || !esAdmin) return "redirect:/login";

        usuarioService.cambiarEstadoBloqueo(id);
        return "redirect:/registered";
    }
}