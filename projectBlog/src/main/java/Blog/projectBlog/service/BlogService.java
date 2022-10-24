package Blog.projectBlog.service;

import Blog.projectBlog.model.ConteudoBlog;
import Blog.projectBlog.model.dto.NewPostDTO;
import Blog.projectBlog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository repository;


    public List<ConteudoBlog> getAllPosts() {
        return repository.findAll();
    }

    public ConteudoBlog converte(NewPostDTO request) {

        ConteudoBlog conteudoBlog = new ConteudoBlog();

        conteudoBlog.setDataPublicacao(request.getDataPublicacao());
        conteudoBlog.setTituloPublicacao(request.getTituloPublicacao());
        conteudoBlog.setTextoPublicacao(request.getTextoPublicacao());
        conteudoBlog.setFavorito(false);

        return conteudoBlog;
    }

    public void savePost(ConteudoBlog novoPost) {
        repository.save(novoPost);
    }

    public ConteudoBlog findById(Long idPublicacao) {
        return repository.findById(idPublicacao).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + idPublicacao));
    }

    public void deletePost(Long idPublicacao) {
        repository.deleteById(idPublicacao);
    }

    public void update(ConteudoBlog novoPost, Long id) {
        ConteudoBlog post = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        post.setTextoPublicacao(novoPost.getTextoPublicacao());
        post.setTituloPublicacao(novoPost.getTituloPublicacao());
        repository.save(post);
    }

    public List<ConteudoBlog> getByTituloPublicacao(String tituloPublicacao){
        return repository.findByTituloPublicacaoContaining(tituloPublicacao);
    }
}
