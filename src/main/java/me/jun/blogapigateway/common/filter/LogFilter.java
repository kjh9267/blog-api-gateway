package me.jun.blogapigateway.common.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LogFilter extends AbstractGatewayFilterFactory<LogFilter.Config> {

    public LogFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info(String.format("%s%s", config.request, request.getURI()));

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(
                            () -> {
                                ServerHttpResponse response = exchange.getResponse();
                                log.info(String.format("%s :%s", request.getURI(), response.getStatusCode()));
                            }
                    ));
        };
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Config {
        private String request;
    }
}
