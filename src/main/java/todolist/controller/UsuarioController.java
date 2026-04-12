package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import todolist.dto.UsuarioData;
import todolist.service.UsuarioService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // LISTADO DE USUARIOS
    @GetMapping("/registered")
    public String listadoUsuarios(Model model, HttpSession session) {
        // SEGURIDAD: Si no hay nadie logueado, al login
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }

        List<UsuarioData> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios";
    }

    // DETALLE DE UN USUARIO (Lo dejamos ya preparado)
    @GetMapping("/registered/{id}")
    public String detalleUsuario(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/login";
        }

        UsuarioData usuario = usuarioService.findById(id);
        if (usuario == null) {
            return "redirect:/registered";
        }

        model.addAttribute("usuarioDetalle", usuario);
        return "detalleUsuario";
    }
}