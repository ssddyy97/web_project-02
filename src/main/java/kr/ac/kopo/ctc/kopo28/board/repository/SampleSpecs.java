package kr.ac.kopo.ctc.kopo28.board.repository;


import jakarta.persistence.criteria.Predicate;
import kr.ac.kopo.ctc.kopo28.board.domain.Sample;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SampleSpecs {
    public static Specification<Sample> search(Map<String, Object> filter) {
        return ( root,  query,  builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            filter.forEach((String key, Object value) -> {
                switch (key) {
                    case "name":
                        predicates.add(builder.equal(root.get(key).as(String.class), value));
                        break;
                }
            });
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
