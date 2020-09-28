package run.halo.app.model.vo;

import lombok.Data;
import lombok.ToString;
import run.halo.app.model.dto.WebsiteDTO;

import java.util.List;

/**
 * Link team vo.
 *
 * @author ryanwang
 * @date 2019/3/22
 */
@Data
@ToString
public class WebsiteTeamVO {

    private String team;

    private List<WebsiteDTO> websites;
}
