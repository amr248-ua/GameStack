package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.rawgApi.*;
import com.miproyecto.gamestack.model.Genero;
import com.miproyecto.gamestack.model.Plataforma;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.model.Tag;
import com.miproyecto.gamestack.repository.GeneroRepository;
import com.miproyecto.gamestack.repository.PlataformaRepository;
import com.miproyecto.gamestack.repository.TagRepository;
import com.miproyecto.gamestack.repository.VideojuegoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Transactional
@Service
public class RawgService {

    private final WebClient webClient;
    private final String API_KEY;

    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private PlataformaRepository plataformaRepository;
    @Autowired
    private TagRepository tagRepository;


    public RawgService() {
        this.webClient = WebClient.create("https://api.rawg.io/api");
        this.API_KEY = "b63bc77ea7424d118a60bcc10f2b6ba6";
    }


    private void cargarPlataformasJuego(RawgGame game, Videojuego videojuego){
        for(RawgPlatform platform : game.getPlatforms()){
            Plataforma plataforma = plataformaRepository.findByPlataforma(platform.getPlatform().getName());

            if(plataforma != null){
                plataforma.addVideojuego(videojuego);
                plataformaRepository.save(plataforma);
                videojuego.addPlataforma(plataforma);
            }
            else{
                Plataforma newPlataforma = new Plataforma(platform.getPlatform().getName());
                plataformaRepository.save(newPlataforma);

                newPlataforma.addVideojuego(videojuego);
                plataformaRepository.save(newPlataforma);
                videojuego.addPlataforma(newPlataforma);

            }
        }
    }

    private void cargarGenerosJuego(RawgGame game, Videojuego videojuego){
        for(RawgGenre genre : game.getGenres()){
            Genero genero = generoRepository.findByGenero(genre.getName());

            if(genero != null){
                genero.addVideojuegos(videojuego);
                generoRepository.save(genero);
                videojuego.addGenero(genero);
            }
            else{
                Genero newGenero = new Genero(genre.getName());
                newGenero.addVideojuegos(videojuego);
                generoRepository.save(newGenero);
                videojuego.addGenero(newGenero);
            }
        }
    }

    private void cargarTagsJuego(RawgGame game, Videojuego videojuego){
        for(RawgTag tag : game.getTags()) {
            Tag newTag = tagRepository.findByTag(tag.getName());

            if (newTag != null) {
                newTag.addVideojuegos(videojuego);
                tagRepository.save(newTag);
                videojuego.addTag(newTag);
            } else {
                Tag tagNuevo = new Tag(tag.getName());
                tagNuevo.addVideojuegos(videojuego);
                tagRepository.save(tagNuevo);
                videojuego.addTag(tagNuevo);
            }

        }
    }

    private void cargarDevelopersJuego(RawgGameDetails game, Videojuego videojuego){
        for(RawgDeveloper developer : game.getDevelopers()){
            videojuego.addDeveloper(developer.getName());
        }
    }

    private void cargarPublishersJuego(RawgGameDetails game, Videojuego videojuego){
        for(RawgPublisher publisher : game.getPublishers()){
            videojuego.addPublisher(publisher.getName());
        }
    }

    private String quitarEtiquetas(String cadena) {
        // Eliminar etiquetas HTML
        cadena = cadena.replaceAll("<br />", "\n")
                .replaceAll("</?p>", "")
                .trim();

        return cadena;
    }

    public void cargarJuegosDesdeRAWG() {
        String url = "/games?key=" + API_KEY; //+ "&page_size=40";
        int maxPaginas = 1; // Limite de páginas para evitar demasiadas peticiones
        int paginaActual = 1;

        while (url != null && paginaActual <= maxPaginas) {
            RawgResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(RawgResponse.class)
                    .block();

            if (response != null && response.getResults() != null) {
                List<RawgGame> juegos = response.getResults();

                for (RawgGame game : juegos) {
                    RawgGameDetails gameDetails = webClient.get()
                            .uri("/games/" + game.getId() + "?key=" + API_KEY)
                            .retrieve()
                            .bodyToMono(RawgGameDetails.class)
                            .block();

                    if (gameDetails != null) {

                        Videojuego videojuego = new Videojuego(
                                game.getName(),
                                gameDetails.getDescription_raw(),
                                game.getBackground_image(),
                                game.getReleased()

                        );

                        videojuegoRepository.save(videojuego);
                        cargarPlataformasJuego(game,videojuego);
                        cargarGenerosJuego(game,videojuego);
                        cargarTagsJuego(game,videojuego);
                        cargarDevelopersJuego(gameDetails,videojuego);
                        cargarPublishersJuego(gameDetails,videojuego);
                        videojuegoRepository.save(videojuego);

                    }

                }
                url = response.getNext();
            }
            else{
                url=null;
            }
            paginaActual++;

        }

    }
}
