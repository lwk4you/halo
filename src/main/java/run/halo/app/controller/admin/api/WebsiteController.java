package run.halo.app.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import run.halo.app.model.dto.LinkDTO;
import run.halo.app.model.dto.WebsiteDTO;
import run.halo.app.model.entity.Link;
import run.halo.app.model.entity.Website;
import run.halo.app.model.params.LinkParam;
import run.halo.app.model.params.WebsiteParam;
import run.halo.app.service.LinkService;
import run.halo.app.service.WebsiteService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Website Controller
 *
 * @author lwk4you
 * @date 2020-09-25
 */
@RestController
@RequestMapping("/api/admin/websites")
public class WebsiteController {

    private final WebsiteService websiteService;

    public WebsiteController(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    @GetMapping
    @ApiOperation("Lists websites")
    public List<WebsiteDTO> listWebsites(@SortDefault(sort = "team", direction = DESC) Sort sort) {
        return websiteService.listDtos(sort.and(Sort.by(ASC, "priority")));
    }

    @GetMapping("{id:\\d+}")
    @ApiOperation("Gets website detail by id")
    public WebsiteDTO getBy(@PathVariable("id") Integer id) {
        return new WebsiteDTO().convertFrom(websiteService.getById(id));
    }

    @PostMapping
    @ApiOperation("Creates a website")
    public WebsiteDTO createBy(@RequestBody @Valid WebsiteParam websiteParam) {
        Website website = websiteService.createBy(websiteParam);
        return new WebsiteDTO().convertFrom(website);
    }

    @PutMapping("{id:\\d+}")
    @ApiOperation("Updates a website")
    public WebsiteDTO updateBy(@PathVariable("id") Integer id,
            @RequestBody @Valid WebsiteParam websiteParam) {
        Website website = websiteService.getById(id);
        websiteParam.update(website);
        return new WebsiteDTO().convertFrom(websiteService.update(website));
    }

    @DeleteMapping("{id:\\d+}")
    @ApiOperation("Delete website by id")
    public void deletePermanently(@PathVariable("id") Integer id) {
        websiteService.removeById(id);
    }

    @GetMapping("teams")
    @ApiOperation("Lists all website teams")
    public List<String> teams() {
        return websiteService.listAllTeams();
    }
}
