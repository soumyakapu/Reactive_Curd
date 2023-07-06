package reactive_demo.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_demo.entity.User;
import reactive_demo.repo.UserRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserHandler {

    private final UserRepo repo;

    public Mono<ServerResponse> getAll(ServerRequest request){
        Flux<User> all = repo.findAll();
        return ServerResponse.ok().body(all, User.class);
    }
    public Mono<ServerResponse> createUser(ServerRequest request){
       Mono<User> user = request.bodyToMono(User.class);
        Mono<User> userMono = user.flatMap(repo::save);
        return ServerResponse.ok().body(userMono,User.class);
    }
    public Mono<ServerResponse> getByName(ServerRequest request){
        String s = request.pathVariable("name");
        Mono<User> user =repo.findByFirstName(s);
        return ServerResponse.ok().body(user,User.class);
    }
    public Mono<ServerResponse> updateUser(ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().body( userMono.flatMap(repo::save),User.class);
    }
    public Mono<ServerResponse> updateByFirstName(ServerRequest request){
        String s = request.pathVariable("name");
        Mono<User> user = request.bodyToMono(User.class);
        Mono<User> byFirstName = repo.findByFirstName(s);
        if((user.map(User::getFirstName)) !=null){

        }


        Mono<User> userMono1 = byFirstName.flatMap(userMono -> repo.save(userMono));
        return ServerResponse.ok().body(userMono1,User.class);
    }
}
