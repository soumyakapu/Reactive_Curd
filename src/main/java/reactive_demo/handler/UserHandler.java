package reactive_demo.handler;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_demo.entity.User;
import reactive_demo.repo.UserRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;


@Service
//@RequiredArgsConstructor
public class UserHandler {
    @Autowired
    private  UserRepo repo;
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

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
    public Mono<ServerResponse> updateUserByFirstName(ServerRequest request){
        String name = request.pathVariable("name");
        Mono<User> userMono = request.bodyToMono(User.class);
        Mono<User> userMono1 = repo.findByFirstName(name);

      return  userMono.flatMap(user -> {
          if(user.getFirstName() != null){
              user.setFirstName(userMono.map(User::getFirstName).toString());

          }
          if(user.getLastName()!= null){
              user.setLastName(userMono.map(User::getLastName).toString());
          }
          if(user.getEmail() != null){
              user.setEmail(userMono.map(User::getEmail).toString());
          }
          if(user.getSalary() <0){
              try {
                  user.setSalary(userMono.map(User::getSalary).toFuture().get());
              } catch (InterruptedException e) {
                  throw new RuntimeException(e);
              } catch (ExecutionException e) {
                  throw new RuntimeException(e);
              }
          }
          return ServerResponse.ok().body( userMono.flatMap(repo::save),User.class);
        });

    }
    public Mono<ServerResponse> deleteUser(ServerRequest request){
        String firstName = request.pathVariable("firstName");
        Query query = new Query().addCriteria(Criteria.where("firstName").is(firstName));
        Mono<User> andRemove = mongoTemplate.findAndRemove(query, User.class);
        return ServerResponse.ok().body(andRemove,User.class);
    }
}
