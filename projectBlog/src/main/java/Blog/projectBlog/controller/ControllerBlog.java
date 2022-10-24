package Blog.projectBlog.controller;


import Blog.projectBlog.model.ConteudoBlog;
import Blog.projectBlog.model.dto.NewPostDTO;
import Blog.projectBlog.service.BlogService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
@Getter @Setter
public class ControllerBlog {

    @Autowired
    private BlogService service;

    @GetMapping("/home")
    public String home(Model model){
        List <ConteudoBlog> conteudoBlog = service.getAllPosts();
        NewPostDTO post = new NewPostDTO();
        model.addAttribute("pesquisaPost", post);
        model.addAttribute("conteudoBlog", conteudoBlog);

        if (conteudoBlog.isEmpty()){
            return "homeEmpty";
        }

        return "home";
    }

    @PostMapping("/add")
    public String novoPost(@Valid @ModelAttribute NewPostDTO request, BindingResult result, Model model){

        ConteudoBlog novoPostData = service.converte(request);
        service.savePost(novoPostData);
        System.out.println(novoPostData.getIdPublicacao());
        return "redirect:/home";
    }

    @GetMapping("/NovoPost")
    public String formulario(Model model){
        NewPostDTO postModel = new NewPostDTO();
        model.addAttribute("pesquisaPost", postModel);
        model.addAttribute("conteudoNovoPost", new ConteudoBlog());
        return "NovoPost";

    }

    @GetMapping("/delete/{id}")
    public String success(@PathVariable("id") Long idPublicacao, Model model){
        NewPostDTO post = new NewPostDTO();
        model.addAttribute("pesquisaPost", post);
        if (idPublicacao == null || service.findById(idPublicacao) == null){
            return "NotFound";
        }
        service.deletePost(idPublicacao);
        return "DeletePost";
    }

    @GetMapping("/EditarPost/{id}")
    public String formEdit(@PathVariable Long id, Model model){
        NewPostDTO postModel = new NewPostDTO();
        model.addAttribute("pesquisaPost", postModel);
        ConteudoBlog post = service.findById(id);
        model.addAttribute("conteudoNovoPost", post);

        if (id == null || service.findById(id) == null){
            return "NotFound";
        }
        return "UpdatePost";
    }

    @GetMapping("/Pesquisa/EditarPost/{id}")
    public String editarPostB(@PathVariable Long id, Model model){
        NewPostDTO postModel = new NewPostDTO();
        model.addAttribute("pesquisaPost", postModel);
        ConteudoBlog post = service.findById(id);
        model.addAttribute("conteudoNovoPost", post);

        if (id == null || service.findById(id) == null){
            return "NotFound";
        }
        return "UpdatePost";
    }

    @PostMapping("/EditarPost/update/{id}")
    public String updatePost(@PathVariable Long id ,NewPostDTO request, Model model){

        if (id == null || service.findById(id) == null){
        return "NotFound";
        }

        NewPostDTO postModel = new NewPostDTO();
        model.addAttribute("pesquisaPost", postModel);
        ConteudoBlog post = service.converte(request);
        service.update(post, id);
        return "UpdatePostSucess";
    }

    @PostMapping("/Pesquisa/EditarPost/update/{id}")
    public String updatePostSearch(@PathVariable Long id ,NewPostDTO request, Model model){

        if (id == null || service.findById(id) == null){
            return "NotFound";
        }

        NewPostDTO postModel = new NewPostDTO();
        model.addAttribute("pesquisaPost", postModel);
        ConteudoBlog post = service.converte(request);
        service.update(post, id);
        return "UpdatePostSucess";
    }


    @PostMapping("/pesquisa")
    public String pesquisa(@ModelAttribute NewPostDTO request){
        return "redirect:/Pesquisa/" + request.getTituloPublicacao();
    }

    @GetMapping("/Pesquisa/{tituloPublicacao}")
    public String buscaPorTitulo(@PathVariable String tituloPublicacao, Model model){
        List<ConteudoBlog> post = service.getByTituloPublicacao(tituloPublicacao);
        NewPostDTO postModel = new NewPostDTO();
        model.addAttribute("pesquisaPost", postModel);
        model.addAttribute("conteudoBlog", post);

        if (post.isEmpty()){
            return "NotFound";
        }
        return "homeQuery";
    }
}
