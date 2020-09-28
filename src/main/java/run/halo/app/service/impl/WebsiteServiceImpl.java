package run.halo.app.service.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import run.halo.app.exception.AlreadyExistsException;
import run.halo.app.model.dto.LinkDTO;
import run.halo.app.model.dto.WebsiteDTO;
import run.halo.app.model.entity.Link;
import run.halo.app.model.entity.Website;
import run.halo.app.model.params.LinkParam;
import run.halo.app.model.params.WebsiteParam;
import run.halo.app.model.vo.LinkTeamVO;
import run.halo.app.model.vo.WebsiteTeamVO;
import run.halo.app.repository.LinkRepository;
import run.halo.app.repository.WebsiteRepository;
import run.halo.app.service.LinkService;
import run.halo.app.service.WebsiteService;
import run.halo.app.service.base.AbstractCrudService;
import run.halo.app.utils.ServiceUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * WebsiteService implementation class
 *
 * @author lwk
 * @date 2020-09-14
 */
@Service
public class WebsiteServiceImpl extends AbstractCrudService<Website, Integer> implements WebsiteService {

    private final WebsiteRepository websiteRepository;

    public WebsiteServiceImpl(WebsiteRepository websiteRepository) {
        super(websiteRepository);
        this.websiteRepository = websiteRepository;
    }

    @Override
    public @NotNull List<WebsiteDTO> listDtos(@NotNull Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");

        return convertTo(listAll(sort));
    }

    @Override
    public @NotNull List<WebsiteTeamVO> listTeamVos(@NotNull Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");

        // List all links
        List<WebsiteDTO> websites = listDtos(sort);

        // Get teams
        Set<String> teams = ServiceUtils.fetchProperty(websites, WebsiteDTO::getTeam);

        // Convert to team link list map (Key: team, value: link list)
        Map<String, List<WebsiteDTO>> teamLinkListMap = ServiceUtils.convertToListMap(teams, websites, WebsiteDTO::getTeam);

        List<WebsiteTeamVO> result = new LinkedList<>();

        // Wrap link team vo list
        teamLinkListMap.forEach((team, websiteList) -> {
            // Build link team vo
            WebsiteTeamVO websiteTeamVO = new WebsiteTeamVO();
            websiteTeamVO.setTeam(team);
            websiteTeamVO.setWebsites(websiteList);

            // Add it to result
            result.add(websiteTeamVO);
        });

        return result;
    }

    @Override
    public @NotNull List<WebsiteTeamVO> listTeamVosByRandom(@NotNull Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");
        List<WebsiteDTO> links = listDtos(sort);
        Set<String> teams = ServiceUtils.fetchProperty(links, WebsiteDTO::getTeam);
        Map<String, List<WebsiteDTO>> teamLinkListMap = ServiceUtils.convertToListMap(teams, links, WebsiteDTO::getTeam);
        List<WebsiteTeamVO> result = new LinkedList<>();
        teamLinkListMap.forEach((team, linkList) -> {
            WebsiteTeamVO WebsiteTeamVO = new WebsiteTeamVO();
            WebsiteTeamVO.setTeam(team);
            Collections.shuffle(linkList);
            WebsiteTeamVO.setWebsites(linkList);
            result.add(WebsiteTeamVO);
        });
        return result;
    }

    @Override
    public @NotNull Website createBy(@NotNull WebsiteParam websiteParam) {
        Assert.notNull(websiteParam, "Link param must not be null");

        // Check the name
        boolean exist = existByName(websiteParam.getName());

        if (exist) {
            throw new AlreadyExistsException("网站" + websiteParam.getName() + " 已存在").setErrorData(websiteParam.getName());
        }

        return create(websiteParam.convertTo());
    }

    @Override
    public boolean existByName(String name) {
        Assert.hasText(name, "Link name must not be blank");
        Website website = new Website();
        website.setName(name);

        return websiteRepository.exists(Example.of(website));
    }

    @Override
    public List<String> listAllTeams() {
        return websiteRepository.findAllTeams();
    }

    @Override
    public @NotNull List<Website> listAllByRandom() {
        List<Website> allWebsite = websiteRepository.findAll();
        Collections.shuffle(allWebsite);
        return allWebsite;
    }

    @NonNull
    private List<WebsiteDTO> convertTo(@Nullable List<Website> websites) {
        if (CollectionUtils.isEmpty(websites)) {
            return Collections.emptyList();
        }

        return websites.stream().map(website -> (WebsiteDTO) new WebsiteDTO().convertFrom(website))
                .collect(Collectors.toList());
    }
}
