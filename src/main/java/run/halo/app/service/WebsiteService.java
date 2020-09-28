package run.halo.app.service;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import run.halo.app.model.dto.WebsiteDTO;
import run.halo.app.model.entity.Link;
import run.halo.app.model.entity.Website;
import run.halo.app.model.params.LinkParam;
import run.halo.app.model.params.WebsiteParam;
import run.halo.app.model.vo.LinkTeamVO;
import run.halo.app.model.vo.WebsiteTeamVO;
import run.halo.app.service.base.CrudService;

import java.util.List;

/**
 * Link service interface.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-14
 */
public interface WebsiteService extends CrudService<Website, Integer> {

    /**
     * List link dtos.
     *
     * @param sort sort
     * @return all links
     */
    @NonNull
    List<WebsiteDTO> listDtos(@NonNull Sort sort);

    /**
     * Lists link team vos.
     *
     * @param sort must not be null
     * @return a list of link team vo
     */
    @NonNull
    List<WebsiteTeamVO> listTeamVos(@NonNull Sort sort);

    /**
     * Lists link team vos by random
     *
     * @param sort
     * @return a list of link team vo by random
     */
    @NonNull
    List<WebsiteTeamVO> listTeamVosByRandom(@NonNull Sort sort);

    /**
     * Creates link by link param.
     *
     * @param websiteParam must not be null
     * @return create link
     */
    @NonNull
    Website createBy(@NonNull WebsiteParam websiteParam);

    /**
     * Exists by link name.
     *
     * @param name must not be blank
     * @return true if exists; false otherwise
     */
    boolean existByName(String name);

    /**
     * List all link teams.
     *
     * @return a list of teams.
     */
    List<String> listAllTeams();

    /**
     * List all link teams by random
     *
     * @return a list of teams by random
     */
    @NonNull
    List<Website> listAllByRandom();
}
