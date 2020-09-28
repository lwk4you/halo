package run.halo.app.repository;

import org.springframework.data.jpa.repository.Query;
import run.halo.app.model.entity.Link;
import run.halo.app.model.entity.Website;
import run.halo.app.repository.base.BaseRepository;

import java.util.List;

/**
 * Link repository.
 *
 * @author johnniang
 */
public interface WebsiteRepository extends BaseRepository<Website, Integer> {

    /**
     * Find all link teams.
     *
     * @return a list of teams
     */
    @Query(value = "select distinct a.team from Website a")
    List<String> findAllTeams();
}
