package run.halo.app.core.freemarker.tag;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import run.halo.app.model.support.HaloConst;
import run.halo.app.service.LinkService;
import run.halo.app.service.WebsiteService;

import java.io.IOException;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Freemarker custom tag of link.
 *
 * @author ryanwang
 * @date 2019-03-22
 */
@Component
public class WebsiteTagDirective implements TemplateDirectiveModel {

    private final WebsiteService websiteService;

    public WebsiteTagDirective(Configuration configuration, WebsiteService websiteService) {
        this.websiteService = websiteService;
        configuration.setSharedVariable("websiteTag", this);
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        final DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

        if (params.containsKey(HaloConst.METHOD_KEY)) {
            String method = params.get(HaloConst.METHOD_KEY).toString();
            switch (method) {
                case "list":
                    env.setVariable("websites", builder.build().wrap(websiteService.listAll()));
                    break;
                case "listByRandom":
                    env.setVariable("websites", builder.build().wrap(websiteService.listAllByRandom()));
                    break;
                case "listTeams":
                    env.setVariable("teams", builder.build().wrap(websiteService.listTeamVos(Sort.by(DESC, "createTime"))));
                    break;
                case "listTeamsByRandom":
                    env.setVariable("teams", builder.build().wrap(websiteService.listTeamVosByRandom(Sort.by(DESC, "createTime"))));
                    break;
                case "count":
                    env.setVariable("count", builder.build().wrap(websiteService.count()));
                    break;
                default:
                    break;
            }
        }
        body.render(env.getOut());
    }
}
