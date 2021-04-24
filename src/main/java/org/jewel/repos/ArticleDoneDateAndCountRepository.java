package org.jewel.repos;

import org.jewel.model.ArticleDoneDateAndCount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RepositoryRestResource(collectionResourceRel = "articleDoneDateAndCount", itemResourceRel = "articleDoneDateAndCount", path = "articleDoneDateAndCount")
public interface ArticleDoneDateAndCountRepository extends PagingAndSortingRepository<ArticleDoneDateAndCount, Long> {
    ArticleDoneDateAndCount findArticleDoneDateAndCountByDoneDate(LocalDate date);
}
