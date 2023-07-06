package reactive_demo.router;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactive_demo.handler.UserHandler;

@Configuration
@RequiredArgsConstructor
public class userRouter {

    private final UserHandler handler;
    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/", handler::getAll)
                .POST("/",handler::createUser)
                .GET("/{name}",handler::getByName)
                .PUT("/update",handler::updateUser)
                .PUT("/updateByFirstName/{name}",handler::updateByFirstName)
                .build();
    }
}
