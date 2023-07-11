package reactive_demo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxExample {
    public static Flux<String > flux(){

        return Flux.fromIterable(List.of("Hlo","All","Good Morning")).log();
    }
    public static Mono<String> mono(){
        return Mono.just("Good Morning").log();
    }
    public static Flux<String > fluxToUpperCase(){

        return Flux.fromIterable(List.of("Hlo","All","Good Morning"))
                .map(String::toUpperCase)
                .filter(s->s.length()>3)
                .flatMap(s->splitString(s))
                .log();
    }
    public static Flux<String> splitString(String name){
        String[] split = name.split("");
      return   Flux.fromArray(split);
    }
    public static Flux<String > fluxToUpperCase_Async(){

        return Flux.fromIterable(List.of("Hlo","All","Good Morning","Evening"))
                .map(String::toUpperCase)
                .filter(s->s.length()>3)
                .flatMap(s->splitStringWithDelay(s))
                .log();
    }
    public static Flux<String > concatMapToUpperCase_Async(){

        return Flux.fromIterable(List.of("Hlo","All","Good Morning","Evening"))
                .map(String::toUpperCase)
                .filter(s->s.length()>3)
                .concatMap(s->splitStringWithDelay(s))
                .log();
    }
    public static Flux<String> splitStringWithDelay(String name){
        String[] split = name.split("");
        int i = new Random().nextInt(1000);
        return   Flux.fromArray(split)
                .delayElements(Duration.ofMillis(i));
    }
    public static Flux<String > fluxImmutableToUpperCase(){

        Flux<String> stringImmutable = Flux.fromIterable(List.of("Hlo", "All", "Good Morning"));
        stringImmutable.map(String::toUpperCase)
                .log();
            return stringImmutable;
    }
    public static void main(String[] args) {

       flux().subscribe(s -> System.out.println(s));

            mono().subscribe(System.out::println);


    }
}
