package run.halo.app.model.dto;

import lombok.Data;
import run.halo.app.model.dto.base.OutputConverter;
import run.halo.app.model.entity.Website;

/**
 * Link output dto.
 *
 * @author ryanwang
 * @date 2019/3/21
 */
@Data
public class WebsiteDTO implements OutputConverter<WebsiteDTO, Website> {

    private Integer id;

    private String name;

    private String url;

    private String logo;

    private String description;

    private String team;

    private Integer priority;
}
