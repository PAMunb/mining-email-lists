package br.unb.scrap.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.unb.scrap.domain.Post;
import br.unb.scrap.repository.PostRepository;
import lombok.RequiredArgsConstructor;

/**
 * Serviço responsável por fornecer operações relacionadas a postagens.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    /**
     * Busca postagens por nome do autor.
     *
     * @param authorName o nome do autor da postagem a ser buscada
     * @return uma lista de postagens correspondentes ao autor especificado
     * @throws ServiceException se ocorrer um erro ao buscar as postagens
     */
    public List<Post> findPostsByAuthor(String authorName) {
        try {
            return postRepository.findByAuthorName(authorName);
        } catch (Exception e) {
            logger.error("Erro ao buscar postagens por autor: {}", e.getMessage(), e);
            throw new ServiceException("Erro ao buscar postagens por autor", e);
        }
    }
}
