package Blog.projectBlog.repository;

import Blog.projectBlog.model.ConteudoBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<ConteudoBlog, Long> {

    List<ConteudoBlog> findByTituloPublicacaoContaining(@Param("titulo") String tituloPublicacao);
}
