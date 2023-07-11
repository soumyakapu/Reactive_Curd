package reactive_demo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class FluxExampleTest {
    @Test
    void flux(){
        Flux<String> flux = FluxExample.flux();
        StepVerifier.create(flux)
//                .expectNext("Hlo","All","Good Morning")
                .expectNext("Hlo")
                .expectNextCount(2)
                .verifyComplete();

    }
    @Test
    void mono(){
        Mono<String> mono = FluxExample.mono();
        StepVerifier.create(mono).expectNext("Good Morning").verifyComplete();
    }

    @Test
    void fluxToUpperCase() {
        var stringFlux = FluxExample.fluxToUpperCase();
        StepVerifier.create(stringFlux)
//                .expectNext("HLO","ALL","GOOD MORNING")
                .expectNext("G","O","O","D"," ","M","O","R","N","I","N","G")
                .verifyComplete();
    }
//    Reactive Streams are Immutable
    @Test
    void fluxImmutableToUpperCase() {
        Flux<String> stringFlux = FluxExample.fluxImmutableToUpperCase();
        StepVerifier.create(stringFlux).expectNext("HLO","ALL","GOOD MORNING")
                .verifyComplete();
    }

    @Test
    void fluxToUpperCase_Async() {
        Flux<String> stringFlux = FluxExample.fluxToUpperCase_Async();
        StepVerifier.create(stringFlux)
//                .expectNext("G","O","O","D"," ","M","O","R","N","I","N","G","E","V","E","N","I","N","G")
                .expectNextCount(19)
                .verifyComplete();
    }

    @Test
    void concatMapToUpperCase_Async() {
        Flux<String> stringFlux = FluxExample.concatMapToUpperCase_Async();
        StepVerifier.create(stringFlux)
                .expectNext("G","O","O","D"," ","M","O","R","N","I","N","G","E","V","E","N","I","N","G")
                .verifyComplete();
    }
}
