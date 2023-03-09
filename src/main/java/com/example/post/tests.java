package com.example.post;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

public class tests {
    private final R2dbcEntityTemplate test ;

    public tests(R2dbcEntityTemplate test) {
        this.test = test;
    }

    public static void main(String args[]){
    }




//    @Bean
//    public R2dbcCustomConversions test() {
//        ArrayList<Converter<?, ?>> converter = new ArrayList<>();
//    }
}
