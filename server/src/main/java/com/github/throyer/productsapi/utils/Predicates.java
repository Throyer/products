package com.github.throyer.productsapi.utils;

import java.text.Normalizer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class Predicates {

    private static String REPLACES = "áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ";
    private static String MATCHES = "aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC";
 
    public static Predicate like(CriteriaBuilder builder, Expression<String> expression, String value) {
        var lower = Normalizer.normalize(value.toLowerCase().trim(), Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return builder.like(replace(builder, expression), "%" + lower + "%");
    }

    public static Expression<String> replace(
        CriteriaBuilder builder,
        Expression<String> path
    ) {
        return builder
            .function(
                "translate",
                String.class,
                builder.lower(path),
                builder.literal(REPLACES),
                builder.literal(MATCHES)
            );
    }
}
