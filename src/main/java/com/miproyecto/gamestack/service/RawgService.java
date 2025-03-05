package com.miproyecto.gamestack.service;

import com.miproyecto.gamestack.dto.RawgGame;
import com.miproyecto.gamestack.dto.RawgGenre;
import com.miproyecto.gamestack.dto.RawgPlatform;
import com.miproyecto.gamestack.dto.RawgResponse;
import com.miproyecto.gamestack.model.Genero;
import com.miproyecto.gamestack.model.Plataforma;
import com.miproyecto.gamestack.model.Videojuego;
import com.miproyecto.gamestack.repository.GeneroRepository;
import com.miproyecto.gamestack.repository.PlataformaRepository;
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
    @Autowired
    private VideojuegoRepository videojuegoRepository;
    @Autowired
    private GeneroRepository generoRepository;
    @Autowired
    private PlataformaRepository plataformaRepository;
    private final String API_KEY = "b63bc77ea7424d118a60bcc10f2b6ba6"; // Reemplaza con tu clave de RAWG

    public RawgService(VideojuegoRepository videojuegoRepository) {
        this.webClient = WebClient.create("https://api.rawg.io/api");
        this.videojuegoRepository = videojuegoRepository;
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

    public void cargarJuegosDesdeRAWG() {
        String url = "/games?key=" + API_KEY; //+ "&page_size=40";
        int maxPaginas = 3; // Limite de páginas para evitar demasiadas peticiones
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
                    Videojuego videojuego = new Videojuego(
                            game.getName(),
                            game.getBackgroundImage(),
                            game.getReleased()

                    );

                    videojuegoRepository.save(videojuego);
                    cargarPlataformasJuego(game,videojuego);
                    cargarGenerosJuego(game,videojuego);
                    videojuegoRepository.save(videojuego);
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
